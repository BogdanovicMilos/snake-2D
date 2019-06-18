package com.snakegame.utilities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private static Keyboard instance;
    private boolean keys[];

    public Keyboard(){
        keys = new boolean[256];
    }

    public static Keyboard getInstance(){
        if (instance == null){
            instance = new Keyboard();
        }
        return instance;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length){
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length){
            keys[e.getKeyCode()] = false;
        }
    }

    public boolean isDown(int key){
        if (key >= 0 && key < keys.length){
            return keys[key];
        }
        return false;
    }
}
