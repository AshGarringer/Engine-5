package engine.sound;

import javax.sound.sampled.Clip;

/**
 *
 * @author cookibot
 */
public class Sound {
    
    long end_time;
    int loop_number = 0;
    Clip clip;
    
    public Sound(Clip sound, int loop){
        clip = sound;
        loop_number = loop;
        clip.start();
        end_time = clip.getMicrosecondLength()/1000+System.currentTimeMillis();
    }
    
    public void update(){
        if(clipFinished()){
            clip.drain();
            if(loop_number != 0){
                if(loop_number > 0)loop_number --;
                clip.setFramePosition(0);
                clip.start();
                end_time = clip.getMicrosecondLength()/1000+System.currentTimeMillis();
            }
        }
    }
    
    public boolean over(){
        return System.currentTimeMillis() >= end_time && loop_number == 0;
    }
    
    private boolean clipFinished(){
        return System.currentTimeMillis() >= end_time;
    }
    public void close(){
        clip.stop();
        clip.drain();
    }
}
