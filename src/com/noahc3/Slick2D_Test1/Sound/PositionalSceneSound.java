package com.noahc3.Slick2D_Test1.Sound;

import com.noahc3.Slick2D_Test1.Event.Events;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import org.newdawn.slick.geom.Point;

public class PositionalSceneSound extends SceneSound {
    protected Point position;
    public PositionalSceneSound(SoundResource res, float pitch, float volume, ScenePoint scenePoint, boolean persistent) {
        super(res, pitch, volume, scenePoint.scene, persistent);
        this.position = scenePoint.pos;
        play(pitch, volume);
    }

    @Override
    public boolean play(float pitch, float volume) {
        if (position == null) return false; //called by superconstructor, skip.
        else if (Game.player.getScene() != null && Game.player.getScene().equals(this.scene)) {
            sound.playAt(pitch, volume * res.category.getVolume(), position.getX(), position.getY(), 0);
            return true;
        } else {
            stop();
            if (!persistent) Events.SceneChangedListeners.remove(this);
            return false;
        }
    }
}
