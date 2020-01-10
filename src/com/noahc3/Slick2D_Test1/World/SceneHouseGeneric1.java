package com.noahc3.Slick2D_Test1.World;

import com.noahc3.Slick2D_Test1.Entity.WarpPad;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Resources.Tilemaps;
import com.noahc3.Slick2D_Test1.Resources.Tilesets;
import com.noahc3.Slick2D_Test1.Sound.SoundPlayer;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class SceneHouseGeneric1 extends Scene{

    public SceneHouseGeneric1(Identifier identifier, String displayName) {
        super(identifier, displayName);

        try {
            this.tileMap = new TiledMap(Tilemaps.HOUSE_GENERIC_1, Tilesets.tilesets);
            System.out.println("id: " + getIdentifier());
            SoundPlayer.playSceneSound(new Identifier("sound.music.sceneHouseGeneric1"), 1.0f, 1.0f, getIdentifier(), true);
        } catch (SlickException ex) {
            System.err.println(ex.getMessage());
        }

        generateCollisions(16, 0, 1432);

        entities.add(new WarpPad(new Point2D(192, 335), new ScenePoint(554, 175, new Identifier("sceneTest")), false, "", true));

        //SceneRegistry.RegisterScene(this);
    }
}
