package com.noahc3.Slick2D_Test1.GUI;

import com.noahc3.Slick2D_Test1.Resources.IResource;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.FontUtils;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FontResource implements IResource {
    private Identifier identifier;
    private String path;
    private org.newdawn.slick.Font font;

    public FontResource(Identifier identifier, int fontType, String path, int style, float size, boolean antiAlias) {
        this.identifier = identifier;
        this.path = path;

        try {
            Font awtFont = Font.createFont(fontType, ResourceLoader.getResourceAsStream(this.path));
            awtFont = awtFont.deriveFont(style, size);
            this.font = new TrueTypeFont(awtFont, antiAlias);
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
        }

    }

    public org.newdawn.slick.Font getFont() {
        return font;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }


}
