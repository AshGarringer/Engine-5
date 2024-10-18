package engine.objects;

import java.util.ArrayList;

import game.Tiles;

/**
 *
 * @author cookibot
 */
public class DefaultImageGrid extends BasicGrid{
    
    public DefaultImageGrid(int width, int height, int tilewidth, int tileheight) {
        super(width, height, tilewidth, tileheight);
        
        ArrayList<TileMapping> mappings = Tiles.getTiles();
    }
    
}
