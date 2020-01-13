package com.noahc3.Slick2D_Test1.Item;

import com.noahc3.Slick2D_Test1.Config.ConfigControls;
import com.noahc3.Slick2D_Test1.Entity.EntityPlayer;
import com.noahc3.Slick2D_Test1.Entity.IInteractable;
import com.noahc3.Slick2D_Test1.Entity.InteractionType;
import com.noahc3.Slick2D_Test1.GUI.GUITextDialogue;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;

public class BasicItem implements IItem, IInteractable {

    Image texture;
    String displayName;
    Identifier identifier;
    Identifier pickupSound;

    public BasicItem(String displayName, Image texture, Identifier pickupSound) {
        this.identifier = new Identifier("itemDebug");
        this.displayName = displayName;
        this.texture = texture;
        this.pickupSound = pickupSound;
    }

    @Override
    public void drawOnScreen(Graphics g, int x, int y, int width, int height) {
            texture.draw(x, y, width, height);
    }

    @Override
    public boolean canEquip(EntityPlayer player) {
        return true;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void update(GameContainer gc, int delta) {

    }

    @Override
    public String interactionDisplayName() {
        return "Use " + displayName;
    }

    @Override
    public boolean canInteract(EntityPlayer e) {
        return true;
    }

    @Override
    public boolean needsInteraction(EntityPlayer e) {
        return true;
    }

    @Override
    public InteractionType interactionType() {
        return InteractionType.INPUT_PRIMARY;
    }

    @Override
    public void interact(GameContainer gc, EntityPlayer e) {
        Game.GUIRenderQueue.add(new GUITextDialogue("This is a fantastic debug message! Testing wordwrapping maybe or something heh heh heh heh heh. Does it work? I dont know. Testing wordwrapping maybe or something.", 50, Game.dialogueFont));
    }

    @Override
    public Shape interactionArea() {
        return null;
    }

    @Override
    public int getInteractionKey() {
        return ConfigControls.interact_Primary;
    }

    @Override
    public ItemDefinition getItemDefinition() {
        return new ItemDefinition(identifier, displayName);
    }

    @Override
    public Identifier getPickupSound() {
        return pickupSound;
    }
}
