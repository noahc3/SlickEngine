package com.noahc3.Slick2D_Test1.Utility;

import com.noahc3.Slick2D_Test1.Resources.Identifier;
import org.newdawn.slick.geom.*;

public class ScenePoint {

    public Point pos;
    public Identifier scene;

    public ScenePoint(Point position, Identifier scene) {
        this.pos = position;
        this.scene = scene;
    }

    public ScenePoint(int x, int y, Identifier scene) {
        this.pos = new Point(x, y);
        this.scene = scene;
    }
}
