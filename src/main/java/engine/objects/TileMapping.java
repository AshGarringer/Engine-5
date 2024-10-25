package engine.objects;

import java.awt.Color;

/**
 *
 * @author cookibot
 */
public class TileMapping {
    
    public Class tileclass;
    public Color mappingcolor;
    
    public TileMapping (Class tileclass, Color mappingcolor){
        this.tileclass = tileclass;
        this.mappingcolor = mappingcolor;
    }
}
