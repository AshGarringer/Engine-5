package engine.input;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Objects;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 *
 * @author cookiebot
 */
public class SnesController {
    
    public static int X = 0;
    public static int A = 1;
    public static int B = 2;
    public static int Y = 3;
    public static int UP = 4;
    public static int DOWN = 5;
    public static int LEFT = 6;
    public static int RIGHT = 7;
    public static int LTRIGGER = 8;
    public static int RTRIGGER = 9;
    public static int SELECT = 10;
    public static int START = 11;
    
    public boolean active = true;
    
    private Boolean[] pressed = new Boolean[12];
    private Boolean[] released = new Boolean[12];
    private Boolean[] held = new Boolean[12];
    private Controller controller;
    EventQueue eventQueue;
    Event event;

    public SnesController(Controller controller) {
        this.controller = controller;
        eventQueue = controller.getEventQueue();
        event = new Event();
        for(int i = 0; i < 12; i ++){
            pressed[i] = false;
            released[i] = false;
            held[i] = false;
        }
    }
    
    public SnesController(){
        active = false;
        for(int i = 0; i < 12; i ++){
            pressed[i] = false;
            released[i] = false;
            held[i] = false;
        }
    }
    
    public void update(){
        if(!active)return;

        Event event = getControllerEvent();
        Component component = event.getComponent();

        if(component != null){
            Component.Identifier identifier = component.getIdentifier();
            float value = event.getValue();

            if (identifier == Component.Identifier.Axis.X) {
                if (value == -1.0f) {
                    set(LEFT,true);
                    set(RIGHT,false);
                } else if (value == 1.0f) {
                    set(LEFT,false);
                    set(RIGHT,true);
                } else {
                    set(LEFT,false);
                    set(RIGHT,false);
                }
            }
            if (identifier == Component.Identifier.Axis.Y) {
                if (value == -1.0f) {
                    set(UP,true);
                    set(DOWN,false);
                } else if (value == 1.0f) {
                    set(UP,false);
                    set(DOWN,true);
                } else {
                    set(UP,false);
                    set(DOWN,false);
                }
            }
            if(identifier == Component.Identifier.Button.TRIGGER){
                set(A,value == 1.0f);
            }
            if(identifier == Component.Identifier.Button.THUMB){
                set(B,value == 1.0f);
            }
            if(identifier == Component.Identifier.Button.THUMB2){
                set(X,value == 1.0f);
            }
            if(identifier == Component.Identifier.Button.TOP){
                set(Y,value == 1.0f);
            }
            if(identifier == Component.Identifier.Button.TOP2){
                set(LTRIGGER,value == 1.0f);
            }
            if(identifier == Component.Identifier.Button.PINKIE){
                set(RTRIGGER,value == 1.0f);
            }
            if(identifier == Component.Identifier.Button.BASE3){
                set(SELECT,value == 1.0f);
            }
            if(identifier == Component.Identifier.Button.BASE4){
                set(START,value == 1.0f);
            }
        }
    }
    
    public void set(int i, boolean b){
        if(!Objects.equals(held[i], b)){
            if(b) pressed[i] = true;
            else released[i] = true;
            held[i] = b;
        }
    }
    
    public Event getControllerEvent(){
        
        controller.poll();
        eventQueue.getNextEvent(event);

        return event;

    }
    
    public boolean pressed(int button_code){
        boolean holder = pressed[button_code];
        pressed[button_code] = false;
        return holder;
    }
    public boolean released(int button_code){
        boolean holder = released[button_code];
        released[button_code] = false;
        return holder;
    }
    public boolean held(int button_code){
        return held[button_code];
    }
    public void clearPressed(){
        for(int i = 0; i < 12; i ++){
            pressed[i] = false;
        }
    }
    public void clearReleased(){
        for(int i = 0; i < 12; i ++){
            released[i] = false;
        }
    }
    public void clearHeld(){
        for(int i = 0; i < 12; i ++){
            held[i] = false;
        }
    }
    public void clearChanges(){
        for(int i = 0; i < 12; i ++){
            pressed[i] = false;
            released[i] = false;
        }
    }
    
    public static ArrayList<SnesController> getControllers(){
        Controller[] controllers;
        
        try {
            PrintStream error = System.err;
            System.setErr(new PrintStream(new OutputStream(){
                public void write(int a){}
            }));
            controllers = createDefaultEnvironment().getControllers();
            ArrayList<SnesController> list = new ArrayList<>();
            for (Controller controller : controllers) {
                if (controller.getType() == Controller.Type.STICK) {
                    list.add(new SnesController(controller));
                }
            }
            System.setErr(error);
            return list;
        } catch (ReflectiveOperationException ex) {
            return new ArrayList<>();
        }
        
    }
    
    private static ControllerEnvironment createDefaultEnvironment() throws ReflectiveOperationException {

        Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>)
            Class.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructors()[0];

        constructor.setAccessible(true);

        return constructor.newInstance();
    }
    
    public boolean equalsController(SnesController snes){
        System.out.println("Port1 " + snes.controller.hashCode());
        System.out.println("Port2 " + controller.hashCode());
        return controller.getPortNumber() == snes.controller.getPortNumber();
    }
}