package engine.graphics;

import engine.logic.Calcs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystems;

/**
 *
 * @author cookibot
 */
public class Textures {
    
    public static final BufferedImage none = new BufferedImage(2,2, BufferedImage.TYPE_INT_ARGB);
    public static String separator = FileSystems.getDefault().getSeparator();
    
    public static BufferedImage loadPng(String path){
        return loadImage(path+".png");
    }
    public static BufferedImage loadImage(String path){
        try {
            if(!path.substring(0, 1).equals("/"))path = "/"+path;
            return ImageIO.read(Textures.class.getResource(path));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Image '"+path+"' does not exist");
        }
        return none;
    }
    public static BufferedImage compositImage(BufferedImage image1, int w1, int h1,
            BufferedImage image2, int x,int y,int w2,int h2){
        BufferedImage b = new BufferedImage(w1,h1,BufferedImage.TYPE_INT_ARGB);
        Graphics g = b.getGraphics();
        g.drawImage(image1, 0, 0, null);
        g.drawImage(image2, x, y, w2, h2, null);
        return b;
    }
    
    public static BufferedImage compositImage(BufferedImage image1,int w, int h,
            BufferedImage image2, int x,int y){
        
        return compositImage(image1, w, h, image2,x,y,image2.getWidth(),image2.getHeight());
    }
    
    public static BufferedImage compositImage(BufferedImage image1,
            BufferedImage image2, int x,int y, int w, int h){
        
        return compositImage(image1, image1.getWidth(), image1.getHeight(),
            image2,x,y,w,h);
    }
    
    public static BufferedImage compositImage(BufferedImage image1, BufferedImage image2, int x,int y){
        
        return compositImage(image1, image1.getWidth(), image1.getHeight(),
            image2,x,y,image2.getWidth(),image2.getHeight());
    }
    
    public static BufferedImage clipComposit(BufferedImage image1, BufferedImage image2, int x,int y){
        return clipComposit(image1, image2, x, y, image2.getWidth(), image2.getHeight());
    }
    
    public static BufferedImage clipComposit(BufferedImage image1, BufferedImage image2, int x,int y,int w,int h){
        BufferedImage b = new BufferedImage(image1.getWidth(),image1.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics g = b.getGraphics();
        g.drawImage(image1, 0, 0, null);
        g.drawImage(image2, x, y, w, h, null);
        for(int i = 0; i < image1.getWidth(); i ++){
            for(int j = 0; j < image1.getHeight(); j ++){
                int alpha = new Color(image1.getRGB(i,j),true).getAlpha();
                Color c = new Color(b.getRGB(i,j));
                b.setRGB(i,j, new Color(c.getRed(),c.getGreen(),c.getRed(),alpha).getRGB());
            }
        }
        return b;
    }
    
    public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
    	BufferedImage newImage = new BufferedImage(width,height,2);
    	Graphics g2d = (Graphics2D)newImage.getGraphics();
    	g2d.drawImage(image,0,0,width,height,null);
    	return newImage;
    }
    
    public static BufferedImage rotateImage(BufferedImage image, double degrees){
        
        double angle = Math.toRadians(degrees);
        
        Point midpoint = new Point(image.getWidth()/2,image.getHeight()/2);
        
        Point p1 = Calcs.rotatePoint(0,0,midpoint,angle);
        Point p2 = Calcs.rotatePoint(0,image.getHeight(),midpoint,angle);
        Point p3 = Calcs.rotatePoint(image.getWidth(),image.getHeight(),midpoint,angle);
        Point p4 = Calcs.rotatePoint(image.getWidth(),0,midpoint,angle);
        
        int width = Math.max(Math.max(p1.x, p2.x), Math.max(p3.x, p4.x))-Math.min(Math.min(p1.x, p2.x), Math.max(p3.x, p4.x));
        
        int height = Math.max(Math.max(p1.y, p2.y), Math.max(p3.y, p4.y))-Math.min(Math.min(p1.y, p2.y), Math.max(p3.y, p4.y));
        
        BufferedImage newimage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        
        AffineTransform transform = new AffineTransform();
        transform.translate(width / 2, height / 2);
        transform.rotate(angle);
        transform.translate(-image.getWidth() / 2, -image.getHeight() / 2);

        ((Graphics2D)newimage.createGraphics()).drawImage(image, transform, null);
        
        return newimage;
    }

    public static BufferedImage[] loadAnimation(String path, int length){
        return loadAnimation(path,length,".png");
    }

    public static BufferedImage[] loadAnimation(String path, int length, String end){
        BufferedImage[] images = new BufferedImage[length];
        for(int i = 0; i < length; i ++){
            images[i] = loadImage(path+Calcs.fillInt(i)+end);
        }
        return images;
    }

    public static OptimizedImage[] loadOptimizedAnimation(String path, int length){
        return loadOptimizedAnimation(path,length,".png");
    }

    public static OptimizedImage[] loadOptimizedAnimation(String path, int length, String end){
        OptimizedImage[] images = new OptimizedImage[length];
        for(int i = 0; i < length; i ++){
            images[i] = new OptimizedImage(loadImage(path+Calcs.fillInt(i)+end));
        }
        return images;
    }
}
