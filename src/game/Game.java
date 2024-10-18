package game;

import engine.framework.Engine;

import java.awt.*;
import java.util.Random;

/**
 *
 * @author cookibot
 */
public class Game extends Engine{

    public static int TILE_SIZE = 120;
    public static int PLAYER_SIZE = 200;
    public static Point SCREEN = new Point(1600,900);

    float zoom;
    float scroll;
    Random random;

    public Game(){
        this.start("Fever Dream", 1200, 675, false);
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {

    }


    
}
class Ball{

    float rotation;
    float desiredRotation;
    float x;
    float y;
    float xMomentum;
    float yMomentum;
    float direction;
    boolean moving;
    int timeSinceRotation;
    int timeSinceMovement;
    int timeMoving;
    Random random;

    public Ball(){
        random = new Random();
    }

    public void tick(float otherx, float othery){

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



            }
        }


        timeSinceRotation ++;
    }


}
