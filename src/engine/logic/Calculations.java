package engine.logic;

import java.awt.Rectangle;

/**
 *
 * @author cookibot
 */
public class Calculations {
    
    public static int TITLE_BAR_HEIGHT = 37;
    
    public static Rectangle getRectScaledtoCanvas(Rectangle scale_rect,Rectangle canvas){
        float scale = (float)canvas.height/scale_rect.height;
        return new Rectangle(-(Math.round(scale*scale_rect.width)-canvas.width)/2,0,(int)(scale_rect.width*scale),canvas.height);
    }
    
    public static Rectangle getRectScaledtoCanvas(int width, int height,Rectangle canvas){
        return getRectScaledtoCanvas(new Rectangle(0,0,width,height),canvas);
    }
    
    public static float getDistance(int x1, int y1, int x2, int y2){
        return (float)Math.sqrt(((x1 - x2)*(x1 - x2))+((y1 - y2)*(y1 - y2)));
    }
}
