package engine.sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 *
 * @author cookibot
 */
public class SoundPlayer {
    
    public static final int LOOP = -1;
    
    ArrayList<Sound> sounds = new ArrayList<Sound>();
    
    int soundsplayed = 0;
    
    public void update(){
        for(int i = 0; i < sounds.size(); i ++){
            Sound sound = sounds.get(i);
            if(sound.over()){
                sounds.remove(i);
                i --;
            }
            else{
                sound.update();
            }
        }
    }
    
    public void stopAll(){
        System.out.println("sounds stopped");
        while(!sounds.isEmpty()){
            sounds.get(0).close();
            sounds.remove(0);
        }
    }
    
    public void addSound(String file, int loop, boolean safe){
        AudioInputStream audioInputStream = null;
        try {
            Clip clip;
            
            audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResource("/sounds/"+file+".wav"));
            
            clip = AudioSystem.getClip();
            
            clip.open(audioInputStream);
            
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue((volume.getMaximum() - volume.getMinimum())*0.80f+volume.getMinimum());
            
            if(safe)clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP)
                        clip.close();
                }
            });
            Sound sound  = new Sound(clip, loop);
            
            sounds.add(sound);
            
        } catch (Exception ex) {
            System.out.println("error");
        } finally {
            try {
                audioInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
