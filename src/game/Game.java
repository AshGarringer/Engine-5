package game;

import engine.framework.Engine;
import engine.graphics.ImageAnimation;
import engine.graphics.Textures;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
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
    float zoom;
    float scroll;
    Random random;

    public Game(){
        this.start("Fever Dream", 1200, 675, false);
        ball1 = new Ball();
        ball2 = new Ball();
    }
    
    @Override
    public void tick() {
        ball1.tick(ball2.x,ball2.y);
        ball2.tick(ball1.x,ball1.y);
    }

    @Override
    public void render(Graphics g) {
        ball1.render(g);
        ball2.render(g);
    }
}
class Ball{

    BufferedImage[] animation;
    int frame;
    double rotation;
    int rotationIntent;
    float rotationMomentum;
    float x;
    float y;
    float xMomentum;
    float yMomentum;
    boolean moving;
    double direction;
    int timeSinceRotation;
    int timeSinceMovement;
    int timeMoving;
    Random random;

    public Ball(){
        random = new Random();

        animation = Textures.loadAnimation("complexBall/frame", 52);
    }

    public void tick(float otherx, float othery){

        frame ++;
        if(frame >= animation.length)frame = 0;

        double dist = Math.sqrt(Math.pow(otherx - x,2) + Math.pow(othery - y,2));
        double angle = Math.atan2(otherx - x,othery - y);

        if(moving) {
            timeMoving++;
            if(random.nextInt(200) < timeMoving){
                moving = false;
                timeMoving = 0;
            }
        }
        else {
            timeSinceMovement++;

            if(random.nextInt(600) < timeSinceMovement + 20){
                moving = true;
                timeSinceMovement = 0;
                direction = getRotation(dist,angle);
            }
        }

        timeSinceRotation ++;
        if(random.nextInt(100) < timeSinceRotation){
            rotationIntent = random.nextInt(3)-1;
        }

        rotationMomentum += (rotationIntent * 5 - rotationIntent)*0.01f;
        if(moving) {
            xMomentum += (float) (Math.cos(direction) * 10 - xMomentum) * 0.05f;
            yMomentum += (float) (Math.sin(direction) * 10 - xMomentum) * 0.05f;
        }
        else{
            xMomentum *= 0.08f;
            yMomentum *= 0.08f;
        }

        rotation = (rotationMomentum + rotation) % Math.PI;
        x += xMomentum;
        y += yMomentum;
    }

    public void render(Graphics g){
        g.drawImage(animation[frame],Math.round(x),Math.round(y),Game.PLAYER_SIZE,Game.PLAYER_SIZE, null);
    }

    public double getRotation(double dist, double angle){
        if(dist > 400){
            return angle + random.nextDouble(Math.PI) - Math.PI/2;
        }
        else{
            return random.nextDouble(Math.PI*2);
        }
    }

}
