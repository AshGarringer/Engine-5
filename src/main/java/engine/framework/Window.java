package engine.framework;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import engine.logic.Calcs;

/**
 *
 * @author cookibot
 */
public class Window {
    
    public JFrame frame;
    boolean decorated;
    private boolean wasResized;
    private Point size;
    
    public Window(String name,int width,int height, int max,boolean full){
        frame = new JFrame(name);
        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(width, height+Calcs.TITLE_BAR_HEIGHT));
        frame.setMinimumSize(new Dimension(width, height+Calcs.TITLE_BAR_HEIGHT));
        frame.setMaximumSize(new Dimension(max, max+Calcs.TITLE_BAR_HEIGHT));
        if(full){
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            frame.setUndecorated(true);
        }
        decorated = !full;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    	size = new Point(frame.getWidth(),frame.getHeight());
        
    }
    public void start(Engine engine){
        
        frame.setVisible(true);
        frame.add(engine);
        
    }
    
    public void update() {
    	if(!wasResized && frame.getWidth() != size.x || frame.getHeight() != size.y) {
    		wasResized = true;
    	}
    	size = new Point(frame.getWidth(),frame.getHeight());
    }
    
    public int getWidth(){
        return frame.getWidth();
    }
    
    public int getHeight(){
        if(decorated) return frame.getHeight()-Calcs.TITLE_BAR_HEIGHT;
        else return frame.getHeight();
    }
    
    public boolean wasResized() {
    	boolean holder = wasResized;
    	wasResized = false;
    	return holder;
    }
    
}
