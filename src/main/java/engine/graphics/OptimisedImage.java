package engine.graphics;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import engine.graphics.Textures;

/**
 *
 * @author cookiebot
 */
public class OptimisedImage {
    
    public static int ROTATION_CACHE_LENGTH = 4;
    
    private BufferedImage[] image;
    
    private LinkedList<Rotation> rotations;
    
    private BufferedImage scaled;
    Point scale;
    float graphicScale;
    
    public OptimisedImage(BufferedImage image){
        this.image = new BufferedImage[] {
        		image, Textures.scaleImage(image, image.getWidth()/2, image.getHeight()/2),
        		Textures.scaleImage(image, image.getWidth()/4, image.getHeight()/4)
        };
        rotations = new LinkedList<>();
        scale = new Point(image.getWidth(),image.getHeight());
        scaled = image;
        graphicScale = 1;
    }
    public void drawRotated(int x, int y, int width, int height, float degrees, Graphics g){
    	drawRotated(x,y,width,height,1,degrees,g);
    }
    
    public void drawRotated(int x, int y, int width, int height, float scale, float degrees, Graphics g){
    	
    	int scaledX = (int)(x*scale);
    	int scaledY = (int)(y*scale);
    	
    	for(Rotation rot : rotations) {
    		if(rot.width == width && rot.height == height && rot.scale == scale && rot.rotation == degrees) {
    			g.drawImage(rot.image, scaledX-rot.image.getWidth()/2, scaledY-rot.image.getHeight()/2, rot.image.getWidth(), rot.image.getHeight(), null);
    			return;
    		}
    	}
    	
    	BufferedImage scImage = Textures.rotateImage(getScaledImage(width,height,scale),degrees);
    	Rotation rot = new Rotation(width,height,scale,degrees,scImage);
    	rotations.add(rot);
		g.drawImage(rot.image, scaledX-rot.image.getWidth()/2, scaledY-rot.image.getHeight()/2, rot.image.getWidth(), rot.image.getHeight(), null);
		if(rotations.size() > 4)rotations.remove(0);
    }
    public void draw(int x, int y, Graphics g){
    	draw(x,y,scale.x,scale.y,g);
    }
    public void draw(int x, int y, int width, int height, Graphics g){
    	draw(x,y,width,height,1,g);
    }
    public void draw(int x, int y, float graphicScale, Graphics g){
    	draw(x,y,scale.x,scale.y,graphicScale,g);
    }
    public void draw(int x, int y, int width, int height, float graphicScale, Graphics g){
        g.drawImage(getScaledImage(width,height,graphicScale), (int)(x*graphicScale), (int)(y*graphicScale), null);
    }
    private BufferedImage getScaledImage(int width, int height, float newGraphicScale){
        if(scale.x == width && scale.y == height && graphicScale == newGraphicScale)return scaled;

        int scaledWidth = Math.max(1, Math.round(width*newGraphicScale));
        int scaledHeight = Math.max(1, Math.round(height*newGraphicScale));
        
        scaled = new BufferedImage(scaledWidth,scaledHeight,2);
        if(scaledWidth < image[2].getWidth() && scaledHeight < image[2].getWidth())
        	scaled.createGraphics().drawImage(image[2], 0,0,scaledWidth,scaledHeight, null);
        else if(scaledWidth < image[1].getWidth() && scaledHeight < image[1].getWidth())
        	scaled.createGraphics().drawImage(image[1], 0,0,scaledWidth,scaledHeight, null);
        else
        	scaled.createGraphics().drawImage(image[0], 0,0,scaledWidth,scaledHeight, null);
        
        scale = new Point(width, height);
        return scaled;
    }
    
    public int getWidth(){
        return image[0].getWidth();
    }
    
    public int getHeight(){
        return image[0].getHeight();
    }
    
    public BufferedImage getImage() {
    	return image[0];
    }
    
    public BufferedImage[] getImages() {
    	return image;
    }
    
    public BufferedImage getImage(float width, float height) {
    	float sc = Math.max(width/image[0].getWidth(), height/image[0].getHeight());
    	return image[0];
    }
    
}
class Rotation {
	public int width;
	public int height;
	public float scale;
	public float rotation;
	public BufferedImage image;
	
	public Rotation(int width, int height, float scale, float rotation, BufferedImage image) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.rotation = rotation;
		this.image = image;
	}
	
}