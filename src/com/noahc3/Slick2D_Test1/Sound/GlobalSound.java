package com.noahc3.Slick2D_Test1.Sound;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class GlobalSound implements ISound {
    protected SoundResource res;
    protected Sound sound;
    protected float pitch;
    protected float volume;

    public GlobalSound(SoundResource res, float pitch, float volume) {
        this.res = res;
        this.pitch = pitch;
        this.volume = volume;
        try {
            this.sound = new Sound(res.path);
        } catch (SlickException e) {
            System.out.println(e.getMessage());
        }
        play(this.pitch, this.volume);
    }

    @Override
    public boolean play(float pitch, float volume) {
        sound.play(pitch, volume * res.category.getVolume());
        return true;
    }

    @Override
    public boolean stop() {
        sound.stop();
        return true;
    }
}
