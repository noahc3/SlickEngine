package com.noahc3.Slick2D_Test1.Core;

import com.noahc3.Slick2D_Test1.Resources.IResource;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.World.Scene;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

//<T extends interface> java being java
public class Registry<T extends IResource> {
    private HashMap<Identifier, T> registry = new HashMap<>();

    public void register(T item) {
        registry.put(item.getIdentifier(), item);
    }

    public boolean tryRegister(T item) {
        return registry.putIfAbsent(item.getIdentifier(), item) == null;
    }

    public T get(Identifier identifier) {
        for(Map.Entry<Identifier, T> k : registry.entrySet()) {
            System.out.println(k.getKey());
            if (k.getKey().equals(identifier)) return k.getValue();
        }
        return null;
    }

    public static final Registry<Scene> SCENES = new Registry<>();
}
