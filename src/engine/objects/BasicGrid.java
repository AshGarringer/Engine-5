package engine.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author cookibot
 */
public class BasicGrid {
    
    ArrayList<ArrayList<Rectangle>> grid;
    int grid_x = 0;
    int grid_y = 0;
    int tilewidth = 0;
    int tileheight = 0;
    
    public BasicGrid(int width, int height, int tilewidth, int tileheight){
        this.tileheight = tileheight;
        this.tilewidth = tilewidth;
        grid = new ArrayList<>();
        for(int i = 0; i < width; i ++){
            grid.add(new ArrayList<>());
            for(int j = 0; j < height; j ++){
                grid.get(i).add(new Rectangle(tilewidth,tileheight));
            }
        }
    }
    public BasicGrid(int x, int y, int width, int height, int tilewidth, int tileheight){
        this.tileheight = tileheight;
        this.tilewidth = tilewidth;
        grid_x = x;
        grid_y = y;
        grid = new ArrayList<>();
        for(int i = 0; i < width; i ++){
            grid.add(new ArrayList<>());
            for(int j = 0; j < height; j ++){
                grid.get(i).add(new Rectangle(tilewidth,tileheight));
            }
        }
    }
    
    public ArrayList<ArrayList<Rectangle>> getGrid(){
        return grid;
    }
    
    public void render(Graphics g, BufferedImage tileimage){
        for(int i = 0; i < grid.size(); i ++){
            for(int j = 0; j < grid.size(); j ++){
                g.drawImage(tileimage, grid_x + tilewidth*i, grid_y + tileheight*j,tilewidth,tileheight, null);
            }
        }
    }
}
