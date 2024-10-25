package engine.objects;

import engine.graphics.Textures;
import java.awt.image.BufferedImage;

/**
 *
 * @author cookibot
 */
public abstract class Tile {
    
    BufferedImage image;
    
    int x; int y;
    
    public Tile(int x, int y){
        this.image = Textures.none;
        this.x = x;
        this.y = y;
    }
    
}
