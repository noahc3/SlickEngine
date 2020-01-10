package com.noahc3.Slick2D_Test1.Event;

import com.noahc3.Slick2D_Test1.World.Scene;

import java.util.ArrayList;

public class Events {
    public static final ArrayList<SceneChangedListener> SceneChangedListeners = new ArrayList<>();

    public static void SceneChanged(Scene scene) {
        for (SceneChangedListener k : SceneChangedListeners) {
            k.SceneChanged(scene);
        }
    }
}
