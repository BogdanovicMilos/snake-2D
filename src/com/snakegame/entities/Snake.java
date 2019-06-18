package com.snakegame.entities;

import com.snakegame.form.GameForm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake {

    private int snakeSize = 6;
    private int snakeHeadLocation = 270;
    private ArrayList<Point> snakeBody;

    private Color randColor;

    public Snake(){

        snakeBody = new ArrayList<>();
        snakeBody.add(new Point(snakeHeadLocation, snakeHeadLocation));

        for (int i = 0; i < snakeSize; i++){
            snakeBody.add(new Point(snakeHeadLocation, snakeBody.get(i).y - 10));

        }
    }

    public boolean isDead(){
        Point head = snakeBody.get(0);
        if (head.getLocation().x + 6 >= GameForm.WIDTH || head.getLocation().x < 0)
            return true;
        if (head.getLocation().y + 10 >= GameForm.HEIGHT || head.getLocation().y < 0)
            return true;
        for (int i = 1; i < snakeBody.size(); i++){
            if (head.x == snakeBody.get(i).x && head.y == snakeBody.get(i).y){
                return true;
            }
        }
        return false;
    }

    public boolean isFoodEaten(Food f){
        Point head = snakeBody.get(0);
        if (head.getLocation().x == f.getFoodLocation().getLocation().x){
            if (head.getLocation().y == f.getFoodLocation().getLocation().y){
                return true;
            }
        }
        return false;
    }

    public void growSnake(){
        int size = snakeBody.size() - 1;
        snakeBody.add(new Point(snakeBody.get(size).getLocation().x, snakeBody.get(size).getLocation().y));
    }

    public void generateColor(){
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        randColor = new Color(r, g, b);
    }

    public void setSnakeHead(int x, int y, int oldX, int oldY){
        try {
            snakeBody.get(0).setLocation(x, y);
            for (int i = 1; i < snakeBody.size(); i++){
                int tempX = snakeBody.get(i).getLocation().x;
                int tempY = snakeBody.get(i).getLocation().y;
                snakeBody.get(i).setLocation(oldX, oldY);
                oldX = tempX;
                oldY = tempY;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g){

        for (int i = 0; i < snakeBody.size(); i++){
            g.setColor(randColor);
            g.drawRect(snakeBody.get(i).getLocation().x, snakeBody.get(i).getLocation().y, snakeSize, snakeSize);
            g.setColor(randColor);
            g.fillRect(snakeBody.get(i).getLocation().x, snakeBody.get(i).getLocation().y, snakeSize, snakeSize);

        }
    }
}
