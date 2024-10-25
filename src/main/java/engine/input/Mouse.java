package engine.input;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;

/**
 *
 * @author cookibot
 */
public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
    
    
    int last_pressed;
    int last_clicked;
    int scroll_amount;
    boolean mouse_in_screen;
    LinkedList<Integer> currently_pressed_buttons;
    int X, Y;

    public Mouse(){
        last_clicked = 0;
        last_pressed = 0;
        scroll_amount = 0;
        mouse_in_screen = false;
        currently_pressed_buttons = new LinkedList<Integer>();
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        last_clicked = me.getButton();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        currently_pressed_buttons.add(me.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        for(int i = 0; i < currently_pressed_buttons.size(); i ++){
            if(currently_pressed_buttons.get(i) == me.getButton()){
                currently_pressed_buttons.remove(i);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {
        mouse_in_screen = false;
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        X = me.getX();
        Y = me.getY();
        mouse_in_screen = true;
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        X = me.getX();
        Y = me.getY();
        mouse_in_screen = true;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        int scrAmt = mwe.getScrollAmount();
        if(mwe.getWheelRotation() < 0)scrAmt *= -1;
        scroll_amount = scrAmt;
    }
    public int clearClicked(){
        int holder = last_clicked;
        last_clicked = 0;
        return holder;
    }
    public int clearScoll(){
        int holder = scroll_amount;
        scroll_amount = 0;
        return holder;
    }
    public int getLastClicked(){
        return last_clicked;
    }
    public LinkedList<Integer> getCurrentlyPressedButtons(){
        return currently_pressed_buttons;
    }
    public boolean inScreen(){
        return mouse_in_screen;
    }
    public int getX(){
        return X;
    }
    public int getY(){
        return Y;
    }
    public Point getPos(){
        return new Point(X,Y);
    }
    public boolean mouseIntersects(Rectangle r){
        boolean b = false;
        if(r.contains(new Point(X,Y)))b = true;
        return b;
    }
    public boolean scaledMouseIntersects(Rectangle r){
        boolean b = false;
        if(r.contains(new Point(X,Y)))b = true;
        return b;
    }
}
