package com.noahc3.Slick2D_Test1.Core;

import com.noahc3.Slick2D_Test1.GUI.FontResource;
import com.noahc3.Slick2D_Test1.Resources.IResource;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Resources.ImageResource;
import com.noahc3.Slick2D_Test1.Sound.SoundResource;
import com.noahc3.Slick2D_Test1.World.Scene;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

//<T extends interface> java being java
public class Registry<T extends IResource> {
    public static final Registry<Scene> SCENES = new Registry<>();
    public static final Registry<SoundResource> SOUNDS = new Registry<>();
    public static final Registry<FontResource> FONTS = new Registry<>();
    public static final Registry<ImageResource> IMAGES = new Registry<>();


    //i am conflicted if this should map strings or identifiers.
    //strings are better since they can use the hashmap's get efficiency correctly...
    //but resources are identified by their identifier.
    private HashMap<String, T> registry = new HashMap<>();

    public void register(T item) {
        registry.put(item.getIdentifier().toString(), item);
    }

    public boolean tryRegister(T item) {
        return registry.putIfAbsent(item.getIdentifier().toString(), item) == null;
    }

    public T get(Identifier identifier) {
        return registry.getOrDefault(identifier.toString(), null);
    }
}
