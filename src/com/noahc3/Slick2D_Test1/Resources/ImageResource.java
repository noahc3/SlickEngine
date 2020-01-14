package com.noahc3.Slick2D_Test1.Resources;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageResource implements IResource {
    protected Identifier identifier;
    protected String path;
    protected Image image;

    public ImageResource(Identifier identifier, String path) throws SlickException {
        this.identifier = identifier;
        this.path = path;
        this.image = new Image(this.path);
    }

    public Image getImage() {
        return image;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }
}
