package com.noahc3.Slick2D_Test1.GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public interface IGUIElement {
    void draw(GameContainer gc, Graphics g);
    boolean shouldUpdate();

    int shouldPersist(); //0: yes | 1: draw/update first | 2: clear immediately (likely been updated)


    void update(GameContainer gc, int delta);
}
