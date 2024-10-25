package game;

import engine.framework.Engine;
import engine.graphics.ImageAnimation;
import engine.graphics.Textures;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author cookibot
 */
public class Game extends Engine{

    public static int TILE_SIZE = 120;
    public static int PLAYER_SIZE = 200;
    public static Point SCREEN = new Point(1600,900);
    BufferedImage image;

    Ball ball1;
    Ball ball2;
    float zoom = 10;
    float camerax = 0;
    float cameray = 0;
    Random random;
    BufferedImage tile1;
    BufferedImage tile2;
    ArrayList<Point> switching;

    public Game(){
        random = new Random();
        ball1 = new Ball(random);
        ball2 = new Ball(random);
        tile1 = Textures.loadPng("tiles/tile1");
        tile2 = Textures.loadPng("tiles/tile2");
        switching = new ArrayList<>();
        image = new BufferedImage(1,1,1);
        this.start("Fever Dream", 1200, 675, false);
    }
    
    @Override
    public void tick() {
        ball1.tick(ball2.x,ball2.y);
        ball2.tick(ball1.x,ball1.y);
    }

    @Override
    public void render(Graphics g) {

        if(image.getWidth() != window.getWidth() || image.getHeight() != window.getHeight())
            image = new BufferedImage(window.getWidth(),window.getHeight(),1);
        Graphics2D g2d = (Graphics2D)image.getGraphics();

        camerax += ((ball1.x + ball2.x)/2f-camerax)*0.05f;
        cameray += ((ball1.y + ball2.y)/2f-cameray)*0.05f;

        Rectangle r = new Rectangle(Math.round(camerax - window.getWidth()/2*zoom)-TILE_SIZE,
                Math.round(cameray - window.getHeight()/2*zoom)-TILE_SIZE,window.getWidth()+TILE_SIZE*2, window.getHeight()+TILE_SIZE*2);

        if(random.nextInt(40) == 0);

        zoom += (Math.max(Math.max((Math.max(ball1.x,ball2.x) - Math.min(ball1.x,ball2.x) + PLAYER_SIZE)/window.getWidth(),
                            (Math.max(ball1.y,ball2.y) - Math.min(ball1.y,ball2.y) + PLAYER_SIZE)/window.getHeight()),
                            PLAYER_SIZE*4f/window.getWidth()) - zoom)*0.05f;

        int numy = Math.round(r.height*zoom)/TILE_SIZE;
        int numx = Math.round(r.width*zoom)/TILE_SIZE;

        Point start = new Point(Math.abs((Math.round(camerax - (window.getWidth()/2)*zoom)-TILE_SIZE)%TILE_SIZE),
                Math.abs(Math.round(cameray - (window.getHeight()/2)*zoom)-TILE_SIZE)%TILE_SIZE);

        g2d.scale(1/zoom,1/zoom);
        g2d.translate(window.getWidth()/2*zoom-camerax,window.getHeight()/2*zoom-cameray);

        for(int x = 0; x < numx; x ++){
            for(int y = 0; y < numy; y ++){
                g2d.drawImage(tile1,r.x + start.x + TILE_SIZE*x,r.y + start.y + TILE_SIZE*y,TILE_SIZE,TILE_SIZE,null);
            }
        }

        ball1.render(g2d);
        ball2.render(g2d);
        g2d.dispose();
    }
}
class Ball{

    BufferedImage[] animation;
    int frame= 0;
    double rotation= 0;
    int rotationIntent= 0;
    float rotationMomentum= 0;
    float x = 0;
    float y = 0;
    float xMomentum= 0;
    float yMomentum= 0;
    boolean moving = false;
    double direction = 0;
    int timeSinceRotation = 0;
    int timeSinceMovement = 0;
    int timeMoving = 0;
    Random random;

    public Ball(Random random){
        this.random = random;

        animation = Textures.loadAnimation("complexBall/frame", 52);
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
        AffineTransform old = g.getTransform();
        g.translate(Math.round(x),Math.round(y));
        g.rotate(rotation);
        g.drawImage(animation[frame/3],-Game.PLAYER_SIZE/2,-Game.PLAYER_SIZE/2,Game.PLAYER_SIZE,Game.PLAYER_SIZE, null);
        g.setTransform(old);
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
