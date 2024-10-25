package engine.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author cookiebot
 */
public class ImageAnimation {
    
    private BufferedImage[] images_arr;
    private ArrayList<BufferedImage> images_list;
    
    private int i = 0;
    private int frame_rate = 1;
    private int i2 = -1;
    
    private boolean loop = true;
    
    public ImageAnimation(BufferedImage[] arr, int f_rate){
        images_arr = arr;
        images_list = new ArrayList<>();
        frame_rate = f_rate; 
    }
    public ImageAnimation(ArrayList<BufferedImage> arr, int f_rate){
        images_list = arr;
        images_arr = new BufferedImage[0];
        frame_rate = f_rate;
    }
    
    public void iterate(){
        i2 ++;
        if(i2 >= frame_rate){
            i2 = 0;
            i ++;
            if(images_list.isEmpty()){
                if(i >= images_arr.length){
                    if(loop)i = 0;
                    else i --;
                }
            }else{
                if(i >= images_list.size()){
                    if(loop)i = 0;
                    else i --;
                }
            }
        }
    }
    
    public BufferedImage getImage(){
        if(images_list.isEmpty()){
            if(images_arr.length <= i)return Textures.none;
            return images_arr[i];
        }else{
            if(images_list.size() <= i)return Textures.none;
            return images_list.get(i);
        }
    }
    
    
    public void setLoop(boolean loop){
        this.loop = loop;
    }
    
    public void setFrame(int frame){
        i = frame;
    }
}
