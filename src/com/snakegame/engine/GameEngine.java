package com.snakegame.engine;

//import com.snakegame.entities.BufferedImageLoader;
//import com.snakegame.entities.CoinSpriteSheet;
import com.snakegame.entities.Food;
import com.snakegame.entities.Snake;
import com.snakegame.form.GameForm;
import com.snakegame.utilities.Direction;
import com.snakegame.utilities.Keyboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class GameEngine extends JPanel implements ActionListener {

    private Food food;
    private Snake snake;
    private Keyboard keyboard;
    private Timer timer;
    private int delay = 80;
    private Direction direction;

//    private BufferedImageLoader bufferedImageLoader;
//    private CoinSpriteSheet coinSpriteSheet;
//    private BufferedImage sprite;
//    private BufferedImage spriteSheet;

    private int posX;
    private int posY;
    private int oldX;
    private int oldY;

    private boolean isGameOver = false;
    private boolean isStarted = true;

    private boolean restrictLeftRight = false;
    private boolean restrictUpDown = false;

    private int score = 0;
    private int highscore = 0;

    private BufferedImage gameover = null;


    public GameEngine(){
//        bufferedImageLoader = new BufferedImageLoader();
//        spriteSheet = bufferedImageLoader.loadImage();
//        coinSpriteSheet = new CoinSpriteSheet(spriteSheet);
//        sprite = coinSpriteSheet.getSpriteSheet(0,0,25,25);

        food = new Food(0, 0);
        snake = new Snake();
        keyboard = Keyboard.getInstance();
        this.addKeyListener(keyboard);
        timer = new Timer(delay, this);
        timer.start();
        init();

    }

    private void init(){
        snake = new Snake();
        snake.generateColor();
        direction = Direction.DOWN;
        posX = oldX = 270;
        posY = oldY = 270;
        score = 0;
        produceFood();
    }

    public void paint(Graphics g){

        super.paint(g);

        if (isStarted){
            g.setColor(Color.BLUE);
            g.drawString("Press \"ENTER\" to start the game", GameForm.WIDTH / 2 - 100, GameForm.HEIGHT / 2);
            if (keyboard.isDown(KeyEvent.VK_ENTER)){
                isStarted = false;
                init();
            }
        } else if (isGameOver){
            highscore = getHighScore();
            if (score == 0){
                updateHighScore();
            } else if (score > highscore){
                updateHighScore();
            }

            g.setColor(Color.GRAY);
            try {
                gameover = ImageIO.read(new File("resources/gameover.png"));
                g.drawImage(gameover, GameForm.HEIGHT / 4, GameForm.WIDTH / 4, this);
                g.drawString("Press \"R\" to restart the game", GameForm.HEIGHT / 2 - 90, GameForm.WIDTH / 2 + 125);
            } catch (Exception e){
                e.printStackTrace();
            }

        } else {
            snake.draw(g);
            food.draw(g);
//            g.drawImage(sprite, 100,100,null);
            g.setColor(Color.BLUE);
            g.drawString("Score: " + score, 10, GameForm.HEIGHT - 40);
            g.drawString("High Score: " + getHighScore(), GameForm.WIDTH - 100, GameForm.HEIGHT - 40);
        }
    }

    private int getHighScore(){
        FileReader fileReader;
        BufferedReader bufferedReader = null;
        String line;

        try {
            fileReader = new FileReader("/Users/milosbogdanovic/IdeaProjects/Snake2D/highscore.txt");
            bufferedReader = new BufferedReader(fileReader);
            if ((line = bufferedReader.readLine()) != null) {
                String val = line.split(":")[1];
                return Integer.parseInt(val);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    private void updateHighScore(){

        FileWriter fileWriter;
        BufferedWriter bufferedWriter = null;

        if (score == 0 && highscore == 0)
            return;

        if (score > highscore) {

            String name = JOptionPane.showInputDialog("New Highscore. What is your name?");
            String newHighScore = name + ":" + score;

            File scoreFile = new File("/Users/milosbogdanovic/IdeaProjects/Snake2D/highscore.txt");

            if (!scoreFile.exists()) {
                try {
                    scoreFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                fileWriter = new FileWriter(scoreFile);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(newHighScore + "");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            repaint();
        }
    }

    private void checkSnakeMovement(){
        if (restrictLeftRight){
            if (keyboard.isDown(KeyEvent.VK_UP)){
                direction = Direction.UP;
            } else if (keyboard.isDown(KeyEvent.VK_DOWN)){
                direction = Direction.DOWN;
            }
        } else if (restrictUpDown) {
            if (keyboard.isDown(KeyEvent.VK_LEFT)) {
                direction = Direction.LEFT;
            } else if (keyboard.isDown(KeyEvent.VK_RIGHT)) {
                direction = Direction.RIGHT;
            }
        }
    }

    private void produceFood(){
        int locX = generateRandom(GameForm.WIDTH / 20 - 5, 1);
        int locY = generateRandom(GameForm.HEIGHT / 20 - 5, 1);

        food.setFoodLocation(locX,locY);
    }

    private int generateRandom(int high, int low){

        return (int) (Math.floor(Math.random() * (1 + high - low)) + low) * 20;
    }

    private void restrictSnakeMovement(){
        if (direction == Direction.LEFT || direction == Direction.RIGHT){
            restrictLeftRight = true;
            restrictUpDown = false;
        }
        if (direction == Direction.UP || direction == Direction.DOWN){
            restrictLeftRight = false;
            restrictUpDown = true;
        }
    }

    private void moveSnake(){
        switch (direction){
            case UP: posY -= 10;break;
            case DOWN: posY += 10;break;
            case LEFT: posX -= 10;break;
            case RIGHT: posX += 10;break;
        }
    }

    public void update(){
        if (!isGameOver){
            repaint();
            checkSnakeMovement();
            restrictSnakeMovement();
            moveSnake();
            snake.setSnakeHead(posX, posY, oldX, oldY);
            oldX = posX;
            oldY = posY;

            if (snake.isFoodEaten(food)){
                snake.generateColor();
                produceFood();
                snake.growSnake();
                score++;
                timer.setDelay(delay--);
            }
            if (snake.isDead()){
                isGameOver = true;
            }
        }

        if (isGameOver && keyboard.isDown(KeyEvent.VK_R)){
            isGameOver = false;
            init();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }
}
