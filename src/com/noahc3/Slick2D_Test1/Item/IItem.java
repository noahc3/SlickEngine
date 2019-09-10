package com.noahc3.Slick2D_Test1.Item;

import com.noahc3.Slick2D_Test1.Entity.EntityPlayer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public interface IItem {
    void drawOnScreen(Graphics g, int x, int y, int width, int height);
    boolean canEquip(EntityPlayer player);
    String getDisplayName();
    void update(GameContainer gc, int delta);
    ItemDefinition getItemDefinition();
}
