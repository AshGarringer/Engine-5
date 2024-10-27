package engine.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author cookiebot
 */
public class OptimizedImage {
    
    private BufferedImage[] imageMipMaps;
    BufferedImage scaledCacheImage;
    Point cacheImageSize;
    Point lastImageSize;
    
    public OptimizedImage(BufferedImage image){
        
        imageMipMaps = new BufferedImage[3];
        
        imageMipMaps[0] = image;
        imageMipMaps[1] = Textures.scaleImage(image, image.getWidth()/2,image.getHeight()/2);
        imageMipMaps[2] = Textures.scaleImage(image, image.getWidth()/4,image.getHeight()/4);
        
        scaledCacheImage = image;
        cacheImageSize = new Point(image.getWidth(),image.getHeight());
        lastImageSize = new Point(image.getWidth(),image.getHeight());
    }
    
    public void drawImage(float x, float y, float width, float height, Graphics2D g){
        g.drawImage(getMipMap(width,height),Math.round(x),Math.round(y),Math.round(width),Math.round(height),null);
    }
    
    public void drawRotated(float x, float y, int width, int height, double rotation, Graphics2D g){
        AffineTransform old = g.getTransform();
        g.translate(x,y);
        g.rotate(rotation);
        double scale = old.getScaleX()*(width/(float)imageMipMaps[0].getWidth());
        if(scale > 0.55f){
            g.translate(-width/2f, -height/2f);
            g.scale(width/(float)imageMipMaps[0].getWidth(), height/(float)imageMipMaps[0].getHeight());
            g.drawImage(getMipMap(width,height),0,0,null);
        }
        else if(scale > 0.30f){
            g.translate(-width/2f, -height/2f);
            g.scale(width/(float)imageMipMaps[1].getWidth(), height/(float)imageMipMaps[1].getHeight());
            g.drawImage(getMipMap(width,height),0,0,null);
        }
        else {
            g.translate(-width/2f, -height/2f);
            g.scale(width/(float)imageMipMaps[2].getWidth(), height/(float)imageMipMaps[2].getHeight());
            g.drawImage(getMipMap(width,height),0,0,null);
        }
        g.setTransform(old);
    }
    
    public BufferedImage getMipMap(float width, float height){
        if(width > imageMipMaps[1].getWidth() || height > imageMipMaps[1].getHeight()){
            return imageMipMaps[0];
        }
        if(width > imageMipMaps[2].getWidth() || height > imageMipMaps[2].getHeight()){
            return imageMipMaps[1];
        }
        return imageMipMaps[2];
    }
}
