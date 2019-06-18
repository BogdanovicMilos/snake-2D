package com.snakegame.entities;

import java.awt.*;

public class Food {

    private int foodSize = 6;
    private Point foodLocation;

    public Food(int x, int y){
        this.foodLocation = new Point(x, y);
    }

    public void setFoodLocation(int x, int y){
        this.foodLocation.setLocation(x,y);
    }

    public Point getFoodLocation(){
        return this.foodLocation;
    }

    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.drawOval(foodLocation.x, foodLocation.y, foodSize, foodSize);
        g.setColor(Color.BLUE);
        g.fillOval(foodLocation.x, foodLocation.y, foodSize, foodSize);
    }
}
