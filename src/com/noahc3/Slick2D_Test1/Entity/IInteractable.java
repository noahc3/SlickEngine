package com.noahc3.Slick2D_Test1.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;

public interface IInteractable {

    String interactionDisplayName();

    boolean canInteract(EntityPlayer e);

    boolean needsInteraction(EntityPlayer e);

    InteractionType interactionType();

    void interact(GameContainer gc, EntityPlayer e);

    Shape interactionArea();

    int getInteractionKey();

}
