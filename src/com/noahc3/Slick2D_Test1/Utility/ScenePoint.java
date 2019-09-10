package com.noahc3.Slick2D_Test1.Utility;

import org.newdawn.slick.geom.*;

public class ScenePoint {

    public Point pos;
    public String scene;

    public ScenePoint(Point position, String scene) {
        this.pos = position;
        this.scene = scene;
    }

    public ScenePoint(int x, int y, String scene) {
        this.pos = new Point(x, y);
        this.scene = scene;
    }
}
