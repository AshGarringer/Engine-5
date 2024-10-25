package engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

/**
 *
 * @author cookibot
 */
public class Keyboard implements KeyListener {
    
    int lastPressed;
    int lastTyped;
    LinkedList<Integer> currentlyPressedKeys;
    
    public Keyboard(){
        currentlyPressedKeys = new LinkedList<>();
        lastPressed = 0;
        lastTyped = 0;
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        lastTyped = ke.getKeyCode();
        currentlyPressedKeys.remove(Integer.valueOf(ke.getKeyCode()));
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        lastPressed = ke.getKeyCode();
        if(!currentlyPressedKeys.contains(ke.getKeyCode()))
        currentlyPressedKeys.add(ke.getKeyCode());
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        lastTyped = ke.getKeyCode();
        currentlyPressedKeys.remove(Integer.valueOf(ke.getKeyCode()));
    }
    
    public boolean pressed(int ke){
        return currentlyPressedKeys.contains(ke);
    }
    
    public int getLastPressed() {
        return lastPressed;
    }
    
    public int clearTyped() {
        int holder = lastTyped;
        lastTyped = 0;
        return holder;
    }
    public int clearPressed() {
        int holder = lastPressed;
        lastPressed = 0;
        return holder;
    }
    public int getLastTyped() {
        return lastTyped;
    }
    
    public LinkedList<Integer> getCurrentlyPressedKeys() {
        return currentlyPressedKeys;
    }
    
}
