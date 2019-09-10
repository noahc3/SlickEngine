package com.noahc3.Slick2D_Test1.Utility;

import com.noahc3.Slick2D_Test1.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

public class TileUtils {

    public static Point2D AbsoluteToTile(int x, int y) {
        return new Point2D((x - (x % 16)) / 16,(y - (y % 16)) / 16);
    }

    public static Point2D WorldToScreen(GameContainer gc, int x, int y) {
        return new Point2D((int)(x - Game.player.getPosition().getX() + ((gc.getWidth() / 2) / Game.scale)), (int)(y - Game.player.getPosition().getY() + ((gc.getHeight() / 2) / Game.scale)));
    }

    public static Point2D ScreenToWorld(GameContainer gc, int x, int y) {
        return new Point2D((int) ((x / Game.scale) + Game.player.getPosition().getX() - (gc.getWidth() / (2 * Game.scale))), (int) ((y / Game.scale) + Game.player.getPosition().getY() - (gc.getHeight() / (2 * Game.scale))));
    }

    public static Rectangle GetScreenBoundingBox(GameContainer gc) {
        return new Rectangle(-(gc.getWidth() / 2) + Game.player.getPosition().getX(), -(gc.getHeight() / 2) + Game.player.getPosition().getY(), gc.getWidth(), gc.getHeight());

    }

    public static Polygon GetTriangle(int x, int y, int width, int height, int corner) {

        Polygon triangle = new Polygon();

        if (corner == 0) {
            triangle.addPoint(width, height);
            triangle.addPoint(0, height);
            triangle.addPoint(width, 0);
        } else if (corner == 1) {
            triangle.addPoint(width, height);
            triangle.addPoint(0, height);
            triangle.addPoint(0, 0);
        } else if (corner == 2) {
            triangle.addPoint(0, 0);
            triangle.addPoint(0, height);
            triangle.addPoint(width, 0);
        } else if (corner == 4) {
            triangle.addPoint(width, height);
            triangle.addPoint(0, 0);
            triangle.addPoint(width, 0);
        }

        triangle.setX(x);
        triangle.setY(y);

        return triangle;
    }


}
