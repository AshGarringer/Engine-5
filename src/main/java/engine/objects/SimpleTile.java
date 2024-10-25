package engine.objects;

import engine.graphics.Textures;
import java.awt.image.BufferedImage;

/**
 *
 * @author cookibot
 */
public abstract class SimpleTile extends Tile{
    
    public SimpleTile(int x, int y, String path) {
        super(x, y);
        image = Textures.loadImage(path);
    }
    
}
