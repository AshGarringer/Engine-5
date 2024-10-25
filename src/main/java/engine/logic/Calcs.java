package engine.logic;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author cookibot
 */
public class Calcs {
    
    public static int TITLE_BAR_HEIGHT = 37;
    
    public static Rectangle getRectScaledtoCanvas(Rectangle scale_rect,Rectangle canvas){
        float scale = (float)canvas.height/scale_rect.height;
        return new Rectangle(-(Math.round(scale*scale_rect.width)-canvas.width)/2,0,(int)(scale_rect.width*scale),canvas.height);
    }
    
    public static Rectangle getRectScaledtoCanvas(int width, int height,Rectangle canvas){
        return getRectScaledtoCanvas(new Rectangle(0,0,width,height),canvas);
    }
    
    
    public static String fillInt(int i){
        if(i < 10)return "000"+i;
        if(i < 100)return "00"+i;
        if(i < 1000)return "0"+i;
        return ""+i;
    }
    
    public static Point rotatePoint(Point rotate, Point rotate_point, double degrees){
        return rotatePoint(rotate.x,rotate.y,rotate_point.x,rotate_point.y,degrees);
    }
    
    public static Point rotatePoint(Point rotate, int rotate_x, int rotate_y, double degrees){
        return rotatePoint(rotate.x,rotate.y,rotate_x,rotate_y,degrees);
    }
    
    public static Point rotatePoint(int x, int y, Point rotate_point, double degrees){
        return rotatePoint(x,y,rotate_point.x,rotate_point.y,degrees);
    }
    
    public static Point rotatePoint(int x, int y, int rotate_x, int rotate_y, double degrees){
        
        degrees = Math.toRadians(degrees);
        
        double current_angle = Math.atan2(y - rotate_y, x - rotate_x);
        
        double radius = Math.sqrt((x - rotate_x)*(x - rotate_x) + (y - rotate_y)*(y - rotate_y));
        
        double newx = rotate_x + Math.cos(current_angle+degrees)*radius;
        
        double newy = rotate_y + Math.sin(current_angle+degrees)*radius;
        
        return new Point((int)Math.round(newx),(int)Math.round(newy));
    }
    
    public static float getDistance(Point p1, float x2, float y2){
        return getDistance(p1.x,p1.y,x2,y2);
    }
    
    public static float getDistance(float x1, float x2, Point p2){
        return getDistance(x1,x2,p2.x,p2.y);
    }
    
    public static float getDistance(Point p1, Point p2){
        return getDistance(p1.x,p1.y,p2.x,p2.y);
    }
    
    public static float getDistance(float x1, float y1, float x2, float y2){
        return (float)Math.sqrt(((x1 - x2)*(x1 - x2))+((y1 - y2)*(y1 - y2)));
    }
    
}
