package engine.framework;

/**
 *
 * @author cookibot
 */
import com.sun.tools.javac.Main;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author cookibot
 */
public abstract class BasicEngine extends Canvas implements Runnable{
    
    Thread thread;
    Graphics graphics;
    int pause = 0;
    private int sync_timer = 0;
    
    public void start(){
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        
        int tps = 60;
        
        long time_per_tick = (long) (1000f/tps);

        while (true){
            
            long st = System.currentTimeMillis();
            
            tick();
            render();
            
            sync_timer ++;
            if(sync_timer > 60)sync_timer = 0;
            
            try {
                long ticktime = time_per_tick - (System.currentTimeMillis()-st);
                
                while(ticktime < 0){
                    ticktime += time_per_tick;
                }
                
                Thread.sleep(ticktime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    public abstract void tick();
    
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        graphics = bs.getDrawGraphics();
        
        render(graphics);
        
        graphics.dispose();
        bs.show();
        if(sync_timer == 0)Toolkit.getDefaultToolkit().sync();
    }
    
    public abstract void render(Graphics g);
}