package eu.vdrm.dspgui.listeners.state;

import eu.vdrm.dspgui.util.ApplicationState;

public interface StateChangeListener {
	public void stateChanged(ApplicationState oldState, ApplicationState newState); 
}
