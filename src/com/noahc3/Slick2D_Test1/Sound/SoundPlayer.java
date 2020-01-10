package com.noahc3.Slick2D_Test1.Sound;

import com.noahc3.Slick2D_Test1.Core.Registry;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import org.newdawn.slick.SlickException;

public class SoundPlayer {
    public static GlobalSound playEverywhere(Identifier sound, float pitch, float volume) {
        SoundResource res = Registry.SOUNDS.get(sound);
        if (res == null) return null;
        GlobalSound s = res.playEverywhere(pitch, volume);
        return s;
    }

    public static SceneSound playSceneSound(Identifier sound, float pitch, float volume, Identifier scene, boolean persistent) {
        SoundResource res = Registry.SOUNDS.get(sound);
        if (res == null) return null;
        SceneSound s = res.playInScene(pitch, volume, scene, persistent);
        return s;
    }

    public static PositionalSceneSound playPositionalSceneSound(Identifier sound, float pitch, float volume, ScenePoint scenePoint, boolean persistent) {
        SoundResource res = Registry.SOUNDS.get(sound);
        if (res == null) return null;
        PositionalSceneSound s = res.playPositional(pitch, volume, scenePoint, persistent);
        return s;
    }
}
