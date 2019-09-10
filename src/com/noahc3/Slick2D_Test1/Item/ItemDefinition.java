package com.noahc3.Slick2D_Test1.Item;

public class ItemDefinition {

    String displayName;
    String registryName;

    public ItemDefinition(String registryName) {
        this.registryName = registryName;
    }

    public ItemDefinition(String registryName, String displayName) {
        this.registryName = registryName;
        this.displayName = displayName;
    }

    public boolean fits(ItemDefinition in) {
        if (registryName != null) {
            if (!registryName.equals(in.registryName)) return false;
        }

        if (displayName != null) {
            if (!displayName.equals(in.displayName)) return false;
        }

        return true;
    }
}
