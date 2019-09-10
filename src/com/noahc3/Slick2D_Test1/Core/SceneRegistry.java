package com.noahc3.Slick2D_Test1.Core;

import com.noahc3.Slick2D_Test1.World.Scene;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class SceneRegistry {

    static HashMap<String, Scene> SceneRegistry = new HashMap<>();

    public static void RegisterScene(Scene scene) {
        SceneRegistry.put(scene.getRegistryName(), scene);
    }

    public static boolean TryRegisterScene(Scene scene) {
        if (SceneRegistry.putIfAbsent(scene.getRegistryName(), scene) == null) {
            return true;
        } else return false;
    }

    public static Scene GetScene(String sceneRegistryName) {
        if (SceneRegistry.containsKey(sceneRegistryName)) return SceneRegistry.get(sceneRegistryName);
        else return null;
    }


}
