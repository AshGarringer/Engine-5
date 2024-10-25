package engine.graphics;

import java.util.ArrayList;

/**
 *
 * @author cookiebot
 */
public class SimpleAnimation {
    
    public static final int HOLD = 0;
    public static final int SLOW_TOWARDS = 1;
    public static final int SPEED_TOWARDS = 2;
    public static final int SMOOTH = 3;
    public static final int MOVE_EVEN = 4;
    
    private ArrayList<Integer> animation;
    
    private int frame = 0;
    private boolean loop;
    private boolean finished;
    
    public SimpleAnimation(int start, boolean loop){
        animation = new ArrayList<>();
        animation.add(start);
        this.loop = loop;
        this.finished = false;
    }
    
    public void tick(){
        if(frame == animation.size()-1){
            if(loop)frame = 0;
            else finished = true;
        }
        else{
            frame ++;
        }
    }
    
    public void addHold(int frames){
        Integer point = animation.get(animation.size()-1);
        for(int i = 1; i < frames+1; i ++){
            animation.add(point);
        }
    }
    
    public void addMotion(int x, int frames, int type){
        Integer start = animation.get(animation.size()-1);
        Integer end = x;
        Integer distance = end-start;
        
        switch(type){
            case HOLD:
                for(int i = 1; i < frames+1; i ++){
                    animation.add(end);
                }
                break;
            case SLOW_TOWARDS:
                
                for(int i = 1; i < frames+1; i ++){
                    animation.add(start + Math.round((float)(Math.sin(((float)i/frames)*Math.PI/2)*distance)));
                }
                break;
            case SPEED_TOWARDS:
                
                for(int i = 1; i < frames+1; i ++){
                    animation.add(start + Math.round(((float)(Math.sin(((float)i/frames)*Math.PI/2-Math.PI/2)+1)*distance)));
                }
                
                break;
            case SMOOTH:
                
                for(int i = 1; i < frames+1; i ++){
                    animation.add( start + Math.round((float)(((-Math.cos(((float)i/frames)*Math.PI)+1f)/2f)*distance)));
                }
                
                break;
            case MOVE_EVEN:
                
                for(int i = 1; i < frames+1; i ++){
                    animation.add(start + Math.round(((float)i/frames)*distance));
                }
                
                break;
        }
    }
    public int value(){
        return animation.get(frame);
    }
    
    public boolean isFinished(){
        return finished;
    }
    public void setFrame(int frame){
        this.frame = frame;
    }
    public int getFrame(){
        return frame;
    }
}
