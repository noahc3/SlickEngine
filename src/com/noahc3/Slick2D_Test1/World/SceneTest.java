package com.noahc3.Slick2D_Test1.World;

import com.noahc3.Slick2D_Test1.Entity.EntityItem;
import com.noahc3.Slick2D_Test1.Entity.EntitySlime;
import com.noahc3.Slick2D_Test1.Entity.WarpPad;
import com.noahc3.Slick2D_Test1.Item.BasicItem;
import com.noahc3.Slick2D_Test1.Item.ItemSword;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Resources.Tilemaps;
import com.noahc3.Slick2D_Test1.Resources.Tilesets;
import com.noahc3.Slick2D_Test1.Sound.SoundPlayer;
import com.noahc3.Slick2D_Test1.Utility.NumberUtilities;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TiledMap;

public class SceneTest extends Scene {

    public SceneTest(Identifier identifier, String displayName) {
        super(identifier, displayName);

        try {
            this.tileMap = new TiledMap(Tilemaps.TEST_1, Tilesets.tilesets);
            entities.add(new WarpPad(new Point2D(544, 139), new ScenePoint(198, 322, new Identifier("sceneHouseGeneric1")), true, "Enter", new Identifier("sound.effect.door"), true));
            entities.add(new EntityItem(new ScenePoint(new Point(500, 139), this.getIdentifier()), new ItemSword("Sword")));

            for (int i = 0; i < 20; i++) {
                entities.add(new EntitySlime("slime", new ScenePoint(NumberUtilities.randomPointOffset(new Point(858, 206), 10, 50, true), this.getIdentifier())));
            }

            SoundPlayer.playSceneSound(new Identifier("sound.music.sceneTest"), 1.0f, 100.0f, getIdentifier(), true);
            SoundPlayer.playPositionalSceneSound(new Identifier("sound.effect.fountain"), 1.0f, 100.0f, new ScenePoint(968, 364, getIdentifier()), true);
        } catch (SlickException ex) {
            System.err.println(ex.getMessage());
        }

        generateCollisions(16, 0, 1432);
        //SceneRegistry.RegisterScene(this);
    }
}
