package engine.framework;

public class MultiState {
	
	private int state = 0;
	private int nextState = 0;
	private boolean isTransitioning = false;
	private int outTimer = 0;
	private int maxOutTimer = 0;
	private int inTimer = 0;
	private int maxInTimer = 0;
	private float transitionPercent = 0;
	
	public void transition(int out, int in) {
		isTransitioning = true;
		outTimer = out;
		maxOutTimer = out-1;
		inTimer = in;
		maxInTimer = in-1;
		nextState = state + 1;
	}
	
	public void transition(int out, int newState, int in) {
		isTransitioning = true;
		outTimer = out;
		maxOutTimer = out-1;
		inTimer = in;
		maxInTimer = in-1;
		nextState = newState;
	}
	public void update() {
		if(isTransitioning) {
			
			if(outTimer > 0) {
				outTimer --;
				transitionPercent = (maxOutTimer - outTimer)/(float)maxOutTimer;
			}
			else if(inTimer > 0) {
				inTimer --;
				if(inTimer == maxInTimer)state = nextState;
				transitionPercent = -inTimer/(float)maxInTimer;
			}
			else {
				isTransitioning = false;
				transitionPercent = 0;
				state = nextState;
			}
		}
	}
	public boolean isTransit() {
		return isTransitioning;
	}
	public float getTransit() {
		return transitionPercent;
	}
	public int state() {
		return state;
	}
}
