package com.noahc3.Slick2D_Test1.Entity;

import com.noahc3.Slick2D_Test1.Core.SceneRegistry;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Item.IItem;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.TileUtils;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class EntityItem extends EntityGeneric {

    IItem item;

    public EntityItem(String registryName, String displayName, Point2D pos, IItem item) {
        super(registryName, displayName);

        this.posX = pos.x;
        this.posY = pos.y;
        this.item = item;

        this.scene = "sceneTest";
    }


    @Override
    public Shape getBoundingBox() {
        return new Rectangle(getPosition().getX(), getPosition().getY(), 24, 24);
    }

    @Override
    public boolean setDisplayName(String displayName) {
        return false;
    }

    @Override
    public void drawInWorld(GameContainer gc, Graphics g, int x, int y) {
        Point2D drawPos = TileUtils.WorldToScreen(gc, (int) getPosition().getX(), (int) getPosition().getY());
        item.drawOnScreen(g, drawPos.x, drawPos.y, 24, 24);
    }

    @Override
    public void drawInWorld(GameContainer gc, Graphics g, Point point) {
        drawInWorld(gc, g, (int) point.getX(), (int) point.getY());
    }

    @Override
    public void drawOnScreen(GameContainer gc, Graphics g, int x, int y) {
        super.drawOnScreen(gc, g, x, y);
    }

    @Override
    public void update(GameContainer gc, String scene, int delta) {
        System.out.println("YO");
        if (getBoundingBox().intersects(Game.player.getBoundingBox())) {
            if (Game.player.inventory.TryInsert(item) == null) SceneRegistry.GetScene(Game.player.getScene()).removeEntity(this);
        }
    }
}
