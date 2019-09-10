package com.noahc3.Slick2D_Test1.World;

import com.noahc3.Slick2D_Test1.Entity.EntityItem;
import com.noahc3.Slick2D_Test1.Entity.WarpPad;
import com.noahc3.Slick2D_Test1.Item.BasicItem;
import com.noahc3.Slick2D_Test1.Resources.Tilemaps;
import com.noahc3.Slick2D_Test1.Resources.Tilesets;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class SceneTest extends Scene {

    public SceneTest(String registryName, String displayName) {
        super(registryName, displayName);

        try {
            this.tileMap = new TiledMap(Tilemaps.TEST_1, Tilesets.tilesets);

            entities.add(new WarpPad("WARPPAD", "Warp Pad", new Point2D(544, 139), new ScenePoint(198, 322, "sceneHouseGeneric1"), true, "Enter", true));
            entities.add(new EntityItem("test", "Item", new Point2D(500, 139), new BasicItem("test", new Image("/assets/textures/debug.png"))));
        } catch (SlickException ex) {
            System.err.println(ex.getMessage());
        }

        generateCollisions(16, 0, 1432);
        //SceneRegistry.RegisterScene(this);
    }
}
