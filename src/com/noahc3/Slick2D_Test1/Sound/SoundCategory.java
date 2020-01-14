package com.noahc3.Slick2D_Test1.Sound;

import java.lang.invoke.VolatileCallSite;

public enum SoundCategory {

    MUSIC("music", 1.0f),
    EFFECTS("effects", 1.0f);

    private final String name;
    private float volume;

    SoundCategory(String name, float initialVolume) {
        this.name = name;
        this.volume = initialVolume;
    }

    public String getName() { return name; }
    public float getVolume() { return volume; }
    public void setVolume(float volume) { this.volume = volume; }

}
