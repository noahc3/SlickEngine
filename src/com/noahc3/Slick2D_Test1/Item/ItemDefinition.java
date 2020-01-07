package com.noahc3.Slick2D_Test1.Item;

import com.noahc3.Slick2D_Test1.Resources.Identifier;

public class ItemDefinition {

    String displayName;
    Identifier identifier;

    public ItemDefinition(Identifier identifier) {
        this.identifier = identifier;
    }

    public ItemDefinition(Identifier identifier, String displayName) {
        this.identifier = identifier;
        this.displayName = displayName;
    }

    public boolean fits(ItemDefinition in) {
        if (identifier != null) {
            if (!identifier.equals(in.identifier)) return false;
        }

        if (displayName != null) {
            if (!displayName.equals(in.displayName)) return false;
        }

        return true;
    }
}
