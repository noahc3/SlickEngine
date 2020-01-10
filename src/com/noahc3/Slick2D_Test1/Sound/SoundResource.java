package com.noahc3.Slick2D_Test1.Sound;

import com.noahc3.Slick2D_Test1.Event.SceneChangedListener;
import com.noahc3.Slick2D_Test1.Resources.IResource;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import javax.swing.text.Position;

public class SoundResource implements IResource {
    protected Identifier identifier;
    protected SoundCategory category;
    protected String path;
    protected ScenePoint pos;

    public SoundResource(Identifier identifier, SoundCategory category) {
        this.identifier = identifier;
        this.path = "assets/" + this.getIdentifier().name.replace('.', '/') + ".ogg";
        this.category = category;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public GlobalSound playEverywhere(float pitch, float volume)  {
        return new GlobalSound(this, pitch, volume);
    }

    public SceneSound playInScene(float pitch, float volume, Identifier scene, boolean persistent) {
        return new SceneSound(this, pitch, volume, scene, persistent);
    }

    public PositionalSceneSound playPositional(float pitch, float volume, ScenePoint pos, boolean persistent) {
        return new PositionalSceneSound(this, pitch, volume, pos, persistent);
    }
}
