package com.noahc3.Slick2D_Test1;

import javafx.stage.Screen;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Game Starting...");

        try {
            AppGameContainer appgc = new AppGameContainer(new Game("Test"));
            appgc.setDisplayMode(1200, 800, false);
            //appgc.setDisplayMode(appgc.getScreenWidth(),appgc.getScreenHeight(), true);
            //appgc.setDisplayMode(1366,768, false);
            appgc.start();
        } catch (SlickException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
