package com.noahc3.Slick2D_Test1.Entity;

import com.noahc3.Slick2D_Test1.Config.ConfigControls;
import com.noahc3.Slick2D_Test1.Container.Container;
import com.noahc3.Slick2D_Test1.Container.ItemSlot;
import com.noahc3.Slick2D_Test1.Core.Registry;
import com.noahc3.Slick2D_Test1.GUI.*;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Item.BasicItem;
import com.noahc3.Slick2D_Test1.Item.IItem;
import com.noahc3.Slick2D_Test1.Resources.Entities;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Sound.SoundPlayer;
import com.noahc3.Slick2D_Test1.Utility.NumberUtilities;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class EntityPlayer extends EntityGeneric implements IHealth {

    long lastUpdate = 0;

    boolean isAnimating = false;

    int xMovementCycleLeft = 0;
    int yMovementCycleLeft = 0;

    int step = 1;
    int speed = 2;

    int direction = 3;

    public int width = 13;
    public int height = 23;

    public IItem mouseItem = null;

    boolean init = true; //TODO: Implement initialization for all entities properly

    float health = 12.0f;
    float knockback = 30.0f;

    //ItemSlot equipedItem = new ItemSlot();

    public Container inventory = new Container(15);

    SpriteSheet ss_walkUp;
    SpriteSheet ss_walkDown;
    SpriteSheet ss_walkLeft;
    SpriteSheet ss_walkRight;

    Animation anim_walkUp;
    Animation anim_walkDown;
    Animation anim_walkLeft;
    Animation anim_walkRight;

    public IInteractable interation;

    boolean holdingPrimaryInteraction = false;
    boolean holdingSecondaryInteraction = false;

    public EntityPlayer(GameContainer gc, String displayName) {
        super(new Identifier("entity.player"), displayName);

        configureAnimations();

        posX = 0;
        posY = 0;
        IItem item = null;
        item = new BasicItem("Test Item", new Identifier("texture.debug"), new Identifier("sound.effect.pickup"));
        inventory.TryInsert(item);
    }

    @Override
    public EntityCategory getEntityCategory() {
        return EntityCategory.PLAYER;
    }

    private void configureAnimations() {
        try {
            ss_walkUp = new SpriteSheet(Entities.player_walkUp, 16, 23);
            anim_walkUp = new Animation(ss_walkUp, 200);
            anim_walkUp.setAutoUpdate(false);
            anim_walkUp.setLooping(true);

            ss_walkDown = new SpriteSheet(Entities.player_walkDown, 16, 23);
            anim_walkDown = new Animation(ss_walkDown, 200);
            anim_walkDown.setAutoUpdate(false);
            anim_walkDown.setLooping(true);

            ss_walkLeft = new SpriteSheet(Entities.player_walkLeft, 16, 23);
            anim_walkLeft = new Animation(ss_walkLeft, 200);
            anim_walkLeft.setAutoUpdate(false);
            anim_walkLeft.setLooping(true);

            ss_walkRight = new SpriteSheet(Entities.player_walkRight, 16, 23);
            anim_walkRight = new Animation(ss_walkRight, 200);
            anim_walkRight.setAutoUpdate(false);
            anim_walkRight.setLooping(true);

        } catch (SlickException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public boolean changeScene(ScenePoint scenePoint) {
        Game.sceneChanging = true;
        Game.sceneChangeStorage = scenePoint;

        return true;
    }

    @Override
    public Shape getBoundingBox() {
        return new Rectangle(this.getPosition().getX() - (width / 2), this.getPosition().getY() - (height / 2), width, height);
    }

    @Override
    public boolean getPersistence(Identifier scene) {
        return true;
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
        SpriteSheet ss;

        if (direction == 0) anim = anim_walkRight;
        else if (direction == 3) anim = anim_walkUp;
        else if (direction == 2) anim = anim_walkLeft;
        else anim = anim_walkDown;

        if (isAnimating) {
            anim.draw((gc.getWidth() / (2 * Game.scale)) - (anim.getWidth() / 2), (gc.getHeight() / (2 * Game.scale)) - (anim.getHeight() / 2));
        } else {
            anim.setCurrentFrame(0);
            anim.draw((gc.getWidth() / (2 * Game.scale)) - (anim.getWidth() / 2), (gc.getHeight() / (2 * Game.scale)) - (anim.getHeight() / 2));
        }

        for(int i = 0; i < 3; i++) {
            //HACK
            Identifier overlay;
            switch(i) {
                default: case 0: overlay = new Identifier("texture.gui.slot.overlay_j"); break;
                case 1: overlay = new Identifier("texture.gui.slot.overlay_k"); break;
                case 2: overlay = new Identifier("texture.gui.slot.overlay_l"); break;
            }
            Game.GUIRenderQueue.add(new GUISlot("hotbar " + i, inventory.GetSlot(i), new Point2D(gc.getWidth() - ((3 - i) * 69), 5), new Identifier("texture.gui.slot.default"), new Identifier("texture.gui.slot.highlighted"), overlay, false));
        }

        if (this.interation != null) {
            Game.GUIRenderQueue.add(new GUIButtonPrompt(this.interation.getInteractionKey(), this.interation.interactionDisplayName(), Game.bigFont, 8));
        }

    }

    @Override
    public void update(GameContainer gc, Identifier scene, int delta) {


        if (!Game.sceneChanging) {

            AL10.alListener3f(AL10.AL_POSITION, posX, posY, 0);

            Animation anim;

            if (direction == 0) anim = anim_walkRight;
            else if (direction == 3) anim = anim_walkUp;
            else if (direction == 2) anim = anim_walkLeft;
            else anim = anim_walkDown;

            anim.update(delta);

            if (isAnimating && xMovementCycleLeft == 0 && yMovementCycleLeft == 0) isAnimating = false;

            int oldX = posX;
            int oldY = posY;

            if (gc.getInput().isKeyDown(ConfigControls.movement_Up)) {
                if (yMovementCycleLeft >= 0) {
                    direction = 3;
                }
                if (yMovementCycleLeft == 0) {
                    yMovementCycleLeft = step;
                    isAnimating = true;
                }
            }
            if (gc.getInput().isKeyDown(ConfigControls.movement_Down)) {
                if (yMovementCycleLeft <= 0) {
                    direction = 1;
                }
                if (yMovementCycleLeft == 0) {
                    yMovementCycleLeft = -step;
                    isAnimating = true;
                }

            }
            if (gc.getInput().isKeyDown(ConfigControls.movement_Left)) {
                if (xMovementCycleLeft <= 0) {
                    direction = 2;
                }
                if (xMovementCycleLeft == 0) {
                    xMovementCycleLeft = -step;
                    isAnimating = true;
                }

            }
            if (gc.getInput().isKeyDown(ConfigControls.movement_Right)) {
                if (xMovementCycleLeft >= 0) {
                    direction = 0;
                }
                if (xMovementCycleLeft == 0) {
                    xMovementCycleLeft = step;
                    isAnimating = true;
                }

            }

            if (yMovementCycleLeft > 0) {
                yMovementCycleLeft = Math.max(yMovementCycleLeft - speed, 0);
                posY -= speed;
                //direction = 1;
            } else if (yMovementCycleLeft < 0) {
                yMovementCycleLeft = Math.min(yMovementCycleLeft + speed, 0);
                posY += speed;
                //direction = 2;
            }

            Shape boundingBox = getBoundingBox();

            for (Shape n : Registry.SCENES.get(getScene()).getColliders()) {
                if (n.intersects(boundingBox)) {
                    posY = oldY;
                    yMovementCycleLeft = 0;
                    //isAnimating = false;
                }
            }

            if (xMovementCycleLeft > 0) {
                xMovementCycleLeft = Math.max(xMovementCycleLeft - speed, 0);
                posX += speed;
                //direction = 3;
            } else if (xMovementCycleLeft < 0) {
                xMovementCycleLeft = Math.min(xMovementCycleLeft + speed, 0);
                posX -= speed;
                //direction = 4;
            }

            boundingBox = getBoundingBox();

            for (Shape n : Registry.SCENES.get(getScene()).getColliders()) {
                if (n.intersects(boundingBox)) {
                    posX = oldX;
                    xMovementCycleLeft = 0;
                    //isAnimating = false;
                }
            }

            for(IItem k : inventory.GetItems()) if (k != null) k.update(gc, delta);

            this.interation = null;
            boolean canInteract = false;

            for(IEntity k : Registry.SCENES.get(getScene()).getEntities()) {
                if (k instanceof IInteractable) {
                    if (((IInteractable) k).interactionArea() != null) {
                        if (getBoundingBox().intersects(((IInteractable) k).interactionArea())) {
                            if (((IInteractable) k).needsInteraction(this) && ((IInteractable) k).canInteract(this)) {
                                this.interation = (IInteractable) k;
                                canInteract = true;
                            }
                        }
                    }
                }
            }

            //if an item interaction occurs, set this.interaction to null to prevent undefined behaviour
            //when trying to interact with an item and a world entity at the same time.
            if (gc.getInput().isKeyDown(ConfigControls.item_A) && !this.holdingPrimaryInteraction) {
                if(tryItemInteraction(gc, inventory.GetSlot(0).GetItem())) this.interation = null;
            } else if (gc.getInput().isKeyDown(ConfigControls.item_B) && !this.holdingPrimaryInteraction) {
                if(tryItemInteraction(gc, inventory.GetSlot(1).GetItem())) this.interation = null;
            } else if (gc.getInput().isKeyDown(ConfigControls.item_C) && !this.holdingPrimaryInteraction) {
                if(tryItemInteraction(gc, inventory.GetSlot(2).GetItem())) this.interation = null;
            }

            if (this.interation != null) {
                InteractionType type = interation.interactionType();

                if (type == InteractionType.INPUT_PRIMARY) {
                    if (gc.getInput().isKeyDown(ConfigControls.interact_Primary) && !this.holdingPrimaryInteraction) {
                        this.interation.interact(gc, this);
                    }
                } else if (type == InteractionType.INPUT_SECONDARY) {
                    if (gc.getInput().isKeyDown(ConfigControls.interact_Secondary) && !this.holdingSecondaryInteraction) {
                        this.interation.interact(gc, this);
                    }
                }
            }

            if (gc.getInput().isKeyDown(ConfigControls.interact_Primary)) holdingPrimaryInteraction = true;
            else holdingPrimaryInteraction = false;
            if (gc.getInput().isKeyDown(ConfigControls.interact_Secondary)) holdingSecondaryInteraction = true;
            else holdingSecondaryInteraction = false;

        } else {

        }
    }

    private boolean tryItemInteraction(GameContainer gc, IItem item) {
        if (item != null) {
            if (item instanceof IInteractable) {
                if(((IInteractable) item).needsInteraction(this) && ((IInteractable) item).canInteract(this)) {
                    ((IInteractable) item).interact(gc, this);
                    return true;
                }
            }
        }
        return false;
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
            if (((IEntity) sender).getEntityCategory().equals(EntityCategory.HOSTILE)) {
                float[] entityCenter = ((IEntity) sender).getBoundingBox().getCenter();
                Point knockbackTarget = NumberUtilities.pointAwayFromPoint(this.getPosition(), new Point(entityCenter[0], entityCenter[1]), knockback, true, true);
                Point pos = getPosition();
                xMovementCycleLeft += (int)(knockbackTarget.getX() - pos.getX());
                yMovementCycleLeft -= (int)(knockbackTarget.getY() - pos.getY());
            }
        }

        SoundPlayer.playEverywhere(new Identifier("sound.effect.hurt"), 1.0f, 100.0f);

        if (health <= 0) {
            Game.GUIRenderQueue.add(new GUITextDialogue("You died.", 50, Game.dialogueFont));
        }

        return oldHealth - health;
    }
}
