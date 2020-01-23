package com.noahc3.Slick2D_Test1.Entity;

import com.noahc3.Slick2D_Test1.Config.ConfigControls;
import com.noahc3.Slick2D_Test1.Config.ConfigDebug;
import com.noahc3.Slick2D_Test1.Core.Registry;
import com.noahc3.Slick2D_Test1.GUI.GUIButtonPrompt;
import com.noahc3.Slick2D_Test1.GUI.GUISlot;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Item.IItem;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Sound.SoundPlayer;
import com.noahc3.Slick2D_Test1.Utility.NumberUtilities;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import com.noahc3.Slick2D_Test1.Utility.TileUtils;
import com.sun.corba.se.spi.ior.IdentifiableFactory;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.util.Random;

public class EntitySlime extends EntityGeneric implements IHealth {

    protected SpriteSheet ss_idle;
    protected Animation anim_idle;

    protected SpriteSheet ss_jump;
    protected Animation anim_jump;

    protected float minDefaultIdleCycle = 700;
    protected float maxDefaultIdleCycle = 1300;
    protected float defaultJumpCycle = 600;
    protected float playerAggroRange = 200;
    protected float maxJumpDistance = 30;

    protected float idleCycle;
    protected float jumpCycle;
    protected boolean isJumping;
    protected Point jumpStart;
    protected Point jumpTarget;

    protected float damage = 2.0f;
    protected float damageCooldown = 0; //millis
    protected float defaultDamageCooldown = 1000; //millis

    protected float health = 10.0f;
    protected float knockback = 30.0f;

    protected float attackCooldown;

    protected Random rng;

    public EntitySlime(String displayName, ScenePoint scenePoint) {
        super(new Identifier("entity.slime"), displayName);
        configureAnimations();
        posX = (int) scenePoint.pos.getX();
        posY = (int) scenePoint.pos.getY();
        this.scene = scenePoint.scene;
        this.rng = new Random();
        setIdleCycle();

    }

    private void setIdleCycle() {
        this.idleCycle = rng.nextFloat() * (maxDefaultIdleCycle - minDefaultIdleCycle) + minDefaultIdleCycle;
    }

    private void configureAnimations() {
        ss_idle = new SpriteSheet(Registry.IMAGES.get(new Identifier("texture.entity.slime.idle")).getImage(), 14, 16);
        anim_idle = new Animation(ss_idle, 150);
        anim_idle.setAutoUpdate(false);
        anim_idle.setLooping(true);


        ss_jump = new SpriteSheet(Registry.IMAGES.get(new Identifier("texture.entity.slime.jump")).getImage(), 14, 16);
        anim_jump = new Animation(ss_jump, Math.round(defaultJumpCycle / ss_jump.getHorizontalCount()));
        anim_jump.setAutoUpdate(false);
        anim_jump.setLooping(true);
    }

    @Override
    public EntityCategory getEntityCategory() {
        return EntityCategory.HOSTILE;
    }

    @Override
    public Shape getBoundingBox() {
        Image currentFrame = anim_idle.getCurrentFrame();
        return new Rectangle(this.getPosition().getX() - (currentFrame.getWidth() / 2), this.getPosition().getY() - (currentFrame.getHeight() / 2), currentFrame.getWidth(), currentFrame.getHeight());
    }

    @Override
    public boolean getPersistence(Identifier scene) {
        return false;
    }

    @Override
    public boolean getDrawable() {
        return true;
    }

    @Override
    public void drawInWorld(GameContainer gc, Graphics g, int x, int y) {
        drawInWorld(gc, g, new Point(x, y));
    }

    @Override
    public void drawInWorld(GameContainer gc, Graphics g, Point point) {
        Animation anim;
        if (isJumping) anim = anim_jump;
        else anim = anim_idle;

        Point2D drawPos = TileUtils.WorldToScreen(gc, (int) getPosition().getX(), (int) getPosition().getY());
        anim.draw(drawPos.x - (anim.getWidth() / 2), drawPos.y - (anim.getHeight() / 2));

        if (ConfigDebug.DRAW_ENTITY_PATHS) {
            if (jumpTarget != null) {
                Point start = TileUtils.WorldToScreen(gc, this.getPosition());
                Point target = TileUtils.WorldToScreen(gc, this.jumpTarget);
                Color color = g.getColor();
                g.setColor(Color.red);
                g.drawLine(start.getX(), start.getY(), target.getX(), target.getY());
                g.setColor(color);
            }
        }



    }

    @Override
    public void update(GameContainer gc, Identifier scene, int delta) {
        if (!Game.sceneChanging) {
            Animation anim;
            if (isJumping) anim = anim_jump;
            else anim = anim_idle;

            anim.update(delta);

            if (!isJumping) {
                idleCycle -= delta;
                //if idleCycle <= 0, switch to jumping and set the jump target
                //the target will be the player if within the aggro range, or otherwise
                //a random point with a random offset from the slime
                if (idleCycle <= 0) {
                    setIdleCycle();
                    isJumping = true;
                    jumpStart = this.getPosition();
                    if (Math.abs(NumberUtilities.distance(this.getPosition(), Game.player.getPosition())) <= playerAggroRange) {
                        jumpTarget = NumberUtilities.pointTowardsPoint(this.getPosition(), Game.player.getPosition(), maxJumpDistance, true);
                    } else {
                        jumpTarget = NumberUtilities.randomPointOffset(this.getPosition(), maxJumpDistance / 2, maxJumpDistance, true);
                    }
                    SoundPlayer.playPositionalSceneSound(new Identifier("sound.effect.slime"), rng.nextFloat() + 0.5f, 100.0f, this.getScenePosition(), false);
                }
            } else {
                jumpCycle -= delta;
                if (jumpCycle <= 0) {
                    jumpCycle = defaultJumpCycle;
                    isJumping = false;
                    jumpTarget = null;
                    jumpStart = null;
                } else {
                    int oldX = posX;
                    int oldY = posY;

                    posY = (int)((jumpTarget.getY() - jumpStart.getY()) * ((defaultJumpCycle - jumpCycle) / defaultJumpCycle) + jumpStart.getY());
                    Shape boundingBox = getBoundingBox();
                    for (Shape n : Registry.SCENES.get(getScene()).getColliders()) {
                        if (n.intersects(boundingBox)) {
                            posY = oldY;
                        }
                    }

                    posX = (int)((jumpTarget.getX() - jumpStart.getX()) * ((defaultJumpCycle - jumpCycle) / defaultJumpCycle) + jumpStart.getX());
                    boundingBox = getBoundingBox();
                    for (Shape n : Registry.SCENES.get(getScene()).getColliders()) {
                        if (n.intersects(boundingBox)) {
                            posX = oldX;
                        }
                    }
                }
            }

            if (damageCooldown > 0) {
                damageCooldown -= delta;
            } else {
                for(IEntity k : Registry.SCENES.get(getScene()).getEntities()) {
                    if (k instanceof IHealth) {
                        if (k.getEntityCategory().equals(EntityCategory.PLAYER)) {
                            if (k.getBoundingBox().intersects(this.getBoundingBox())) {
                                ((IHealth) k).damage(this, damage);
                                damageCooldown = defaultDamageCooldown;
                            }
                        }
                    }
                }
            }

        } else {

        }
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float damage(Object sender, float rawDamage) {
        float oldHealth = health;
        health = NumberUtilities.clamp(health - rawDamage, 0, health);

        if (sender instanceof IEntity) {
            float[] entityCenter = ((IEntity) sender).getBoundingBox().getCenter();
            Point knockbackTarget = NumberUtilities.pointAwayFromPoint(this.getPosition(), new Point(entityCenter[0], entityCenter[1]), knockback, true, true);
            setIdleCycle();
            jumpCycle = defaultJumpCycle;
            isJumping = true;
            jumpStart = this.getPosition();
            jumpTarget = knockbackTarget;
            SoundPlayer.playPositionalSceneSound(new Identifier("sound.effect.slime"), rng.nextFloat() * 0.2f + 0.3f, 300.0f, this.getScenePosition(), false);
        }

        if (health <= 0) {
            Registry.SCENES.get(getScene()).removeEntity(this);
        }

        return oldHealth - health;
    }
}

