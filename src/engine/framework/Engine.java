package engine.framework;

import com.sun.tools.javac.Main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cookiebot
 */

public abstract class Engine extends Canvas implements Runnable{
    
    Thread thread;
    Graphics graphics;
    
    public int state = 0;
    public boolean multi_state = true;
    
    private float max_transition_in = 0;
    private int state_transition_in = 0;
    private float max_transition_out = 0;
    private int state_transition_out = 0;
    private int transition_state = 0;
    
    public Window window;
    
    public void start(String name, int width, int height,boolean full){
        window = new Window(name,width,height,5000,full);
        window.start(this);
        this.createBufferStrategy(3);
        
        new Thread() {
            public void run() {
                load();
            }
        }.start(); 
        
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        
        int tps = 60;
        
        int tickTimer = 0;
        
        long time_per_tick = (long) (1000f/tps);
        
        long lastTime = System.currentTimeMillis();
        
        boolean dropFrame = false;

        while (true){
            
            long st = System.currentTimeMillis();

            tick();
            if(!dropFrame)render();
            
            tickTimer ++;
            if(tickTimer > tps)tickTimer = 0;
            
            try {
                long ticktime = time_per_tick - (System.currentTimeMillis()-st);

                dropFrame = false;
                if(ticktime < 0) {
                	dropFrame = true;
                }
                
                if(tickTimer == 0) {
                	System.out.println((System.currentTimeMillis() - lastTime)/1000d);
                	lastTime = System.currentTimeMillis();
                	
                }
                
                Thread.sleep(Math.max(ticktime,0));
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void load(){
        
    }
    
    private void render() {
    	window.update();
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        graphics = bs.getDrawGraphics();
        render(graphics);
        graphics.dispose();
        bs.show();
        Toolkit.getDefaultToolkit().sync();
        
    }
    
    public void tick(){
        
    }
    
    public void render(Graphics g){
        
    }
    
    public void setTextHints(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
