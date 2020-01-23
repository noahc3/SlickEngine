package com.noahc3.Slick2D_Test1.Entity;

public enum EntityCategory {
    HOSTILE("Hostile"),
    PEACEFUL("Peaceful"),
    PASSIVE("Passive"),
    PLAYER("Player"),
    INANIMATE("Inanimate");

    private final String name;

    EntityCategory(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
