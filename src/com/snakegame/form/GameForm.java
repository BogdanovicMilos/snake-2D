package com.snakegame.form;

import com.snakegame.engine.GameEngine;
import com.snakegame.utilities.Keyboard;

import javax.swing.*;

public class GameForm {

    public static int WIDTH = 600;
    public static int HEIGHT = 600;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Snake Game");
        GameEngine gameEngine = new GameEngine();
        Keyboard keys = Keyboard.getInstance();
        frame.addKeyListener(keys);

        frame.add(gameEngine);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

    }
}
