package com.noahc3.Slick2D_Test1.Sound;

import com.noahc3.Slick2D_Test1.Entity.EntityPlayer;
import com.noahc3.Slick2D_Test1.Event.Events;
import com.noahc3.Slick2D_Test1.Event.SceneChangedListener;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.World.Scene;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SceneSound implements ISound, SceneChangedListener {
    protected SoundResource res;
    protected Sound sound;
    protected float pitch;
    protected float volume;
    protected Identifier scene;
    protected boolean persistent;

    // A SceneSound will only start if the player is in the specified scene, and stop when the scene changes.
    // If flagged persistent, this sound will remain subscribed to SceneChanged after a scene change and begin
    // playing again if the specified scene is reloaded. This persistent flag is useful for sounds that
    // need to always play when a scene is loaded (eg. music, static sound effects), as this way
    // you don't need to recreate the sound object every time the scene is loaded.
    public SceneSound(SoundResource res, float pitch, float volume, Identifier scene, boolean persistent) {
        Events.SceneChangedListeners.add(this);
        this.res = res;
        this.pitch = pitch;
        this.volume = volume;
        this.scene = scene;
        this.persistent = persistent;
        try {
            this.sound = new Sound(res.path);
        } catch (SlickException e) {
            System.out.println(e.getMessage());
        }
        play(this.pitch, this.volume);
    }

    @Override
    public boolean play(float pitch, float volume) {
        if (Game.player.getScene() != null && Game.player.getScene().equals(this.scene)) {
            sound.play(pitch, volume * res.category.getVolume());
            return true;
        } else {
            stop();
            if (!persistent) Events.SceneChangedListeners.remove(this);
            return false;
        }
    }

    @Override
    public boolean stop() {
        sound.stop();
        return true;
    }

    @Override
    public void SceneChanged(Scene scene) {
        play(pitch, volume);
    }
}
