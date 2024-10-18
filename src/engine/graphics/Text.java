package engine.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author cookibot
 */
public class Text {
    
    public static boolean SHADOW = false;
    public static boolean OUTLINE = true;
    
    public Font font;
    public Font unsizedfont;
    public Color color;
    public int size;
    public String path;
    boolean around;
    int shadow_height; 
    public Color shadow_color;
    ArrayList<SmoothedTextImage> outline_cache;
    ArrayList<SmoothedTextImage> shadow_cache;
    boolean removing = false;

    public Text(String p,int s,Color c) {
        fontify(p,s,c);
        outline_cache = new ArrayList<SmoothedTextImage>();
        shadow_cache = new ArrayList<SmoothedTextImage>();
    }
    public Text(String p,int s,Color c, int effect_height, Color effect_color, boolean shadow_or_outline) {
        this.shadow_height = effect_height;
        this.shadow_color = effect_color;
        this.around = shadow_or_outline;
        outline_cache = new ArrayList<SmoothedTextImage>();
        shadow_cache = new ArrayList<SmoothedTextImage>();
        fontify(p,s,c);
    }
    public void setFont(String p){
        fontify(p,size,color);
    }
    public void setSize(int s){
        fontify(path,s,color);
    }
    public void setColor(Color c){
        fontify(path,size,c);
    }
    public void setAll(String p, int s, Color c){
        fontify(p,s,c);
    }
    public void drawString(int X, int Y, String T, Graphics g){
        Color col = g.getColor();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int x = X - fm.stringWidth(T)/2;
        int y = (int)(Y + (fm.getHeight()+ fm.getAscent())/4.8f);
        
        if(shadow_height > 0){
            drawEffect(T, x, y, g);
        }
        g.setColor(color);
        g.drawString(T,x, y);
        g.setColor(col);
    }
    public void drawString(int X, int Y, String T, Graphics g, Color c){
        Color col = g.getColor();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int x = X - fm.stringWidth(T)/2;
        int y = (int)(Y + (fm.getHeight()+ fm.getAscent())/4.8f);
        
        if(shadow_height > 0){
            drawEffect(T, x, y, g);
        }
        g.setColor(color);
        g.drawString(T,x, y);
        g.setColor(col);
    }
    public void drawStringUncenteredLeft(int X, int Y, String T, Graphics g){
        g.setFont(font);
        Color col = g.getColor();
        FontMetrics fm = g.getFontMetrics(font);
        int y = (int)(Y + (fm.getHeight()+ fm.getAscent())/4.8f);
        for(int i = 1; i <= shadow_height;i +=1){
            g.setColor(shadow_color);
            g.drawString(T, X, y+i);
            if(around == Text.OUTLINE){
                g.drawString(T, X, y-i);
                g.drawString(T, X+i, y);
                g.drawString(T, X-i, y);
                
                g.drawString(T, X+i, y+i);
                g.drawString(T, X-i, y+i);
                g.drawString(T, X+i, y-i);
                g.drawString(T, X-i, y-i);
            }
        }
        g.setColor(color);
        g.drawString(T, X, y);
        g.setColor(col);
    }
    public void drawStringUncenteredLeft(int X, int Y, String T, Graphics g, Color c){
        g.setColor(c);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int y = (int)(Y + (fm.getHeight()+ fm.getAscent())/4.8f);
        g.drawString(T, X, y);
    }
    public void drawStringUncenteredLeftAndUp(int X, int Y, String T, Graphics g){
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        Y += fm.getHeight()-fm.getDescent();
        for(int i = 1; i <= shadow_height;i +=1){
            g.setColor(shadow_color);
            g.drawString(T, X, Y+i);
            if(around == Text.OUTLINE){
                g.drawString(T, X, Y-i);
                g.drawString(T, X+i, Y);
                g.drawString(T, X-i, Y);
                
                g.drawString(T, X+i, Y+i);
                g.drawString(T, X-i, Y+i);
                g.drawString(T, X+i, Y-i);
                g.drawString(T, X-i, Y-i);
            }
        }
        g.setColor(color);
        g.drawString(T, X, Y);
        
    }
    public void drawStringUncenteredRight(int X, int Y, String T, Graphics g){
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        X = X - fm.stringWidth(T);
        Y = (int)(Y + (fm.getHeight()+ fm.getAscent())/4.8f);for(int i = 1; i <= shadow_height;i +=1){
            g.setColor(shadow_color);
            g.drawString(T, X, Y+i);
            if(around == Text.OUTLINE){
                g.drawString(T, X, Y-i);
                g.drawString(T, X+i, Y);
                g.drawString(T, X-i, Y);
                
                g.drawString(T, X+i, Y+i);
                g.drawString(T, X-i, Y+i);
                g.drawString(T, X+i, Y-i);
                g.drawString(T, X-i, Y-i);
            }
        }
        g.setColor(color);
        g.drawString(T, X, Y);
        
    }
    public void drawStringUncenteredRightAndUp(int X, int Y, String T, Graphics g){
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int x = X - fm.stringWidth(T);
        int y = (int)(Y + fm.getHeight()-fm.getDescent());
        
        for(int i = 1; i <= shadow_height;i +=1){
            g.setColor(shadow_color);
            g.drawString(T, x, y+i);
            if(around == Text.OUTLINE){
                g.drawString(T, x, y-i);
                g.drawString(T, x+i, y);
                g.drawString(T, x-i, y);
                
                g.drawString(T, x+i, y+i);
                g.drawString(T, x-i, y+i);
                g.drawString(T, x+i, y-i);
                g.drawString(T, x-i, y-i);
            }
        }
        g.setColor(color);
        g.drawString(T, x, y);
        
    }
    
    public void fontify(String p, int s, Color c){
        path = p;size = s;color = c;
        try{
            if(unsizedfont==null){
                unsizedfont = Font.createFont(Font.TRUETYPE_FONT,
                        this.getClass().getClassLoader().getResourceAsStream("fonts/"+path+".ttf"));
            }
                font = unsizedfont.deriveFont(Font.TRUETYPE_FONT,size);
        }catch(FontFormatException | IOException e){
            e.printStackTrace();
        }
    }
    
    private void drawEffect(String text, int x, int y, Graphics g){
        
        FontMetrics fm = g.getFontMetrics(font);
        int height = fm.getHeight()-fm.getDescent();
        int off = shadow_height;
        
        boolean removing_this_time = removing;
        boolean drawn = false;
        ArrayList<SmoothedTextImage> tbr = new ArrayList<>();
        
        for(SmoothedTextImage cachedEffect : shadow_cache){
            
            if(removing_this_time && cachedEffect.timer < 1){
                tbr.add(cachedEffect);
            }
            else if(removing_this_time){
                cachedEffect.setTimer(0);
            }
            
            if(cachedEffect.text.equals(text)){
                cachedEffect.wasDrawn();
                if(cachedEffect.timer == 3){
                    removing = true;
                }
                
                g.drawImage(cachedEffect.image, x-off,y-height-off,null);
                drawn = true;
            }
        }
        for(SmoothedTextImage cachedEffect : tbr){
            shadow_cache.remove(cachedEffect);
        }
        if(removing_this_time)removing = false;
        
        if(drawn)return;
        
        BufferedImage image;
        image = new BufferedImage(fm.stringWidth(text)+shadow_height*2,fm.getHeight()+shadow_height*2,BufferedImage.TYPE_INT_ARGB);
        
        Graphics ig = image.getGraphics();
        ig.setFont(font);
        ig.setColor(shadow_color);
        
        if(!around)for(int it = 1; it <= shadow_height; it ++)
            ig.drawString(text, 0+off, it+height+off);
        
        else{
            int def = Math.max(1,size/15);
            int rot_def = Math.min(shadow_height*8,180);
            for(int it = shadow_height%def; it <= shadow_height; it +=def){
                
                for(int i = 0; i < rot_def; i ++){
                    float angle = i*(365f/rot_def);
                    int x2 = (int)(Math.cos(Math.toRadians(angle))*it);
                    int y2 = (int)(Math.sin(Math.toRadians(angle))*it);
                    if(it == shadow_height){
                        Color col = ig.getColor();
                        ig.setColor(new Color(col.getRed(),col.getGreen(),col.getBlue(),95));
                    }
                    ig.drawString(text, x2+off, y2+height+off);
                }
            }
            
        }
        
        shadow_cache.add(new SmoothedTextImage(image,text));
        
        g.drawImage(image, x-off,y-height-off,null);
    }
    
    public FontMetrics getMetrics(Graphics g){
        return g.getFontMetrics(font);
    } 
    
    public static Graphics optimizeGraphics(Graphics g){
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        return g;
    }
}
class SmoothedTextImage { 
    
    public BufferedImage image;
    public int timer = 1;
    public String text;
    
    public SmoothedTextImage(BufferedImage image, String text){
        this.image = image;
        this.text = text;
    }
    
    public void setTimer(int timer){
        this.timer = timer;
    }
    
    public void wasDrawn(){
        timer ++;
    }
    
}