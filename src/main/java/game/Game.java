package game;

import engine.framework.Engine;
import engine.graphics.OptimizedImage;
import engine.graphics.Textures;
import engine.input.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author cookibot
 */
public class Game extends Engine{

    public static int TILE_SIZE = 120;
    public static int PLAYER_SIZE = 200;
    public static Point SCREEN = new Point(1600,900);

    Ball ball1;
    Ball ball2;
    float zoom = 10;
    float camerax = 0;
    float cameray = 0;
    Random random;
    OptimizedImage tile1;
    Keyboard keyboard;

    public Game(){
        random = new Random();
        ball1 = new Ball(random);
        ball2 = new Ball(random);
        tile1 = new OptimizedImage(Textures.loadPng("tiles/tile1"));
        keyboard = new Keyboard();
        this.addKeyListener(keyboard);
        this.start("Fever Dream", 1200, 675, false);
    }
    
    @Override
    public void tick() {
        ball1.tick(ball2.x,ball2.y);
        ball2.tick(ball1.x,ball1.y);
    }

    @Override
    public void render(Graphics2D g) {

        camerax += ((ball1.x + ball2.x)/2f-camerax)*0.05f;
        cameray += ((ball1.y + ball2.y)/2f-cameray)*0.05f;

        if(random.nextInt(40) == 0);
        
        switch(keyboard.clearPressed()){
            case KeyEvent.VK_W:
                ball1.y -= 50;
                break;
            case KeyEvent.VK_A:
                ball1.x -= 50;
                break;
            case KeyEvent.VK_S:
                ball1.y += 50;
                break;
            case KeyEvent.VK_D:
                ball1.x += 50;
                break;
        }

        // Multipling by zoom converts the window rectangle to the game rectangle
        // Dividing by zoom converts the game rectangle to the window rectangle
        zoom += (Math.max(Math.max((Math.max(ball1.x,ball2.x) - Math.min(ball1.x,ball2.x) + PLAYER_SIZE)/window.getWidth(),
                            (Math.max(ball1.y,ball2.y) - Math.min(ball1.y,ball2.y) + PLAYER_SIZE)/window.getHeight()),
                            PLAYER_SIZE*4f/window.getWidth()) - zoom)*0.05f;

        //game rectangle context
        
        float scWidth = window.getWidth()*zoom;
        float scHeight = window.getHeight()*zoom;
        
        float startX = camerax - scWidth/2 - ((camerax - scWidth/2)%TILE_SIZE + TILE_SIZE)%TILE_SIZE;
        float startY = cameray - scHeight/2 - ((cameray - scHeight/2)%TILE_SIZE + TILE_SIZE)%TILE_SIZE;
        
        int numX = (int)(scWidth/TILE_SIZE) + 2;
        int numY = (int)(scHeight/TILE_SIZE) + 2;
        
        //game rectangle context

        g.scale(1/zoom,1/zoom);
        g.translate(scWidth/2-camerax,scHeight/2-cameray);

        for(int x = 0; x < numX; x ++){
            for(int y = 0; y < numY; y ++){
                tile1.drawImage((int)(startX + x*TILE_SIZE), (int)(startY + y*TILE_SIZE), TILE_SIZE+1, TILE_SIZE+1, g);
            }
        }
        
        ball1.render(g);
        ball2.render(g);
    }
}
class Ball{

    OptimizedImage[] animation;
    int frame = 0;
    double rotation = 0;
    int rotationIntent = 0;
    float rotationMomentum = 0;
    float x = 0;
    float y = 0;
    float xMomentum = 0;
    float yMomentum = 0;
    boolean moving = false;
    double direction = 0;
    int timeSinceRotation = 0;
    int timeSinceMovement = 0;
    int timeMoving = 0;
    Random random;

    public Ball(Random random){
        this.random = random;

        animation = Textures.loadOptimizedAnimation("complexBall/frame", 52);
    }

    public void tick(float otherx, float othery){

        frame ++;
        if(frame >= animation.length*3)frame = 0;

        double dist = Math.sqrt(Math.pow(otherx - x,2) + Math.pow(othery - y,2));
        double angle = Math.atan2(othery - y,otherx - x);

        if(moving) {
            timeMoving++;
            if(random.nextInt(100)*40 < timeMoving){
                moving = false;
                timeMoving = 0;
            }
        }
        else {
            timeSinceMovement++;

            if(random.nextInt(40)*30 < timeSinceMovement){
                moving = true;
                timeSinceMovement = 0;
                direction = getRotation(dist,angle);
            }
        }

        timeSinceRotation ++;
        if(random.nextInt(100) < timeSinceRotation){
            rotationIntent = random.nextInt(3)-1;
            if(random.nextInt(2) == 0)rotationIntent = 0;
        }

        rotationMomentum += (rotationIntent * 2 - rotationMomentum)*0.01f;
        if(moving) {
            xMomentum += (float) (Math.cos(direction) * 3 - xMomentum) * 0.05f;
            yMomentum += (float) (Math.sin(direction) * 3 - yMomentum) * 0.05f;
        }
        else{
            xMomentum *= 0.95f;
            yMomentum *= 0.95f;
        }

        rotation = (rotationMomentum + rotation) % (Math.PI*2f);
        x += xMomentum;
        y += yMomentum;
    }

    public void render(Graphics2D g){
        animation[frame/3].drawRotated(Math.round(x), Math.round(y), Game.PLAYER_SIZE, Game.PLAYER_SIZE, rotation, g);
    }

    public double getRotation(double dist, double angle){
        if(dist > 500){
            return angle + random.nextDouble(Math.PI) - Math.PI/2;
        }
        else if (dist < Game.PLAYER_SIZE){
            return angle + random.nextDouble(Math.PI) + Math.PI/2;
        }
        else{
            return random.nextDouble(Math.PI*2);
        }
    }

}
