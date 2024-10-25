package engine.input;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;


public class ControllerHandler implements Runnable {

    public static int A = 0;
    public static int B = 1;
    public static int X = 2;
    public static int Y = 3;
    public static int UP = 4;
    public static int DOWN = 5;
    public static int LEFT = 6;
    public static int RIGHT = 7;
    public static int LTRIGGER = 8;
    public static int RTRIGGER = 9;
    public static int SELECT = 10;
    public static int START = 11;
    
    public ArrayList<SnesController> list = new ArrayList<>();
    
    private Thread thread;
    
    public ControllerHandler(){
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.STICK) {
                list.add(new SnesController(controller));
            }
        }
        thread = new Thread(this);
        thread.start();
    }
    
    
    @Override
    public void run() {
        
        boolean stopped = false;
        while (!stopped){
            for(SnesController controller : list){
                
                Boolean[] gamePadState = new Boolean[12];
                for(int i = 0; i < 12; i ++)gamePadState[i] = false;

                Event event = controller.getControllerEvent();
                Component component = event.getComponent();

                if(component != null){
                    Component.Identifier identifier = component.getIdentifier();
                    float value = event.getValue();

                    if (identifier == Component.Identifier.Axis.X) {
                        if (value == -1.0f) {
                            gamePadState[LEFT] = true;
                        } else if (value == 1.0f) {
                            gamePadState[RIGHT] = true;
                        } else {
                            gamePadState[LEFT] = false;
                            gamePadState[RIGHT] = false;
                        }
                    }
                    if (identifier == Component.Identifier.Axis.Y) {
                        if (value == -1.0f) {
                            gamePadState[UP] = true;
                        } else if (value == 1.0f) {
                            gamePadState[DOWN] = true;
                        } else {
                            gamePadState[UP] = false;
                            gamePadState[DOWN] = false;
                        }
                    }
                    if(identifier == Component.Identifier.Button.TRIGGER){
                        gamePadState[A] = value == 1.0f;
                    }
                    if(identifier == Component.Identifier.Button.THUMB){
                        gamePadState[B] = value == 1.0f;
                    }
                    if(identifier == Component.Identifier.Button.THUMB2){
                        gamePadState[X] = value == 1.0f;
                    }
                    if(identifier == Component.Identifier.Button.TOP){
                        gamePadState[Y] = value == 1.0f;
                    }
                    if(identifier == Component.Identifier.Button.TOP2){
                        gamePadState[LTRIGGER] = value == 1.0f;
                    }
                    if(identifier == Component.Identifier.Button.PINKIE){
                        gamePadState[RTRIGGER] = value == 1.0f;
                    }
                    if(identifier == Component.Identifier.Button.BASE3){
                        gamePadState[SELECT] = value == 1.0f;
                    }
                    if(identifier == Component.Identifier.Button.BASE4){
                        gamePadState[START] = value == 1.0f;
                    }

                    try {
                        thread.sleep(20);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ControllerHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                controller.update();
            }
        }
    }
    
}
