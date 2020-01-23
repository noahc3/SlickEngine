package com.noahc3.Slick2D_Test1.Entity;

import com.noahc3.Slick2D_Test1.Config.ConfigDebug;
import com.noahc3.Slick2D_Test1.Core.Registry;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Resources.ImageResource;
import com.noahc3.Slick2D_Test1.Utility.NumberUtilities;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.TileUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.List;

public class EntitySword extends EntityGeneric {
    protected Image sprite;
    protected float damage = 2.0f;
    protected float defaultLifeTime = 250.0f;
    protected float lifeTime = 0.0f;
    protected List<IHealth> damagedEntities;

    public EntitySword(String displayName) {
        super(new Identifier("entity.sword"), displayName);
        this.sprite = Registry.IMAGES.get(new Identifier("texture.entity.sword")).getImage().getScaledCopy(0.5f);
        this.lifeTime = defaultLifeTime;
        this.setScene(Game.player.getScene());
        this.damagedEntities = new ArrayList<IHealth>();
    }

    @Override
    public void drawInWorld(GameContainer gc, Graphics g, int x, int y) {
        drawInWorld(gc, g, new Point(x, y));
    }

    @Override
    public void drawInWorld(GameContainer gc, Graphics g, Point point) {
        if (lifeTime > 0) {
            Point2D drawPos = TileUtils.WorldToScreen(gc, (int) getPosition().getX() - (sprite.getWidth()/2), (int) getPosition().getY() - (sprite.getHeight()/2));
            sprite.setRotation((float) (Game.player.direction * 90 + 90) + (NumberUtilities.clamp((lifeTime / defaultLifeTime - 0.7f), -0.5f, 0.5f) * 100));
            sprite.draw(drawPos.x, drawPos.y);
        }
    }

    @Override
    public void update(GameContainer gc, Identifier scene, int delta) {
        Point positionOffset = NumberUtilities.pointTowardsAngle(Game.player.getPosition(), (float) (Game.player.direction * Math.PI / 2.0f), 3.0f, true);
        this.setPosition(positionOffset);

        lifeTime = NumberUtilities.clamp(lifeTime - delta, 0, defaultLifeTime);

        if (lifeTime > 0) {
            List<IEntity> entities = new ArrayList<IEntity>();
            entities.addAll(Registry.SCENES.get(getScene()).getEntities());
            for(IEntity k : entities) {
                if (k instanceof IHealth) {
                    if (!damagedEntities.contains(k)) {
                        if (k.getBoundingBox().intersects(this.getBoundingBox())) {
                            ((IHealth) k).damage(this, damage);
                            this.damagedEntities.add((IHealth)k);
                        }
                    }
                }
            }
        }else {
            Registry.SCENES.get(getScene()).removeEntity(this);
        }
    }

    @Override
    public boolean getDrawable() {
        return true;
    }

    @Override
    public Shape getBoundingBox() {
        Rectangle rec = new Rectangle(this.getPosition().getX() - (sprite.getWidth() / 16.0f), this.getPosition().getY() - (sprite.getHeight() / 16.0f), sprite.getWidth() / 8.0f, sprite.getHeight() / 8.0f);
        float angle = (float) (Game.player.direction * Math.PI/2.0f) + (NumberUtilities.clamp((lifeTime / defaultLifeTime - 0.7f), -0.5f, 0.5f) * (5.0f * (float)Math.PI / 9.0f));
        System.out.println(angle);
        Point centerOffset = NumberUtilities.pointTowardsAngle(new Point(Game.player.getPosition().getX(), Game.player.getPosition().getY()), angle, 25.0f, true);
        rec.setCenterX(centerOffset.getX());
        rec.setCenterY(centerOffset.getY());
        return rec;
    }

    public void cancelSword() {
        lifeTime = 0.0f;
    }
}
