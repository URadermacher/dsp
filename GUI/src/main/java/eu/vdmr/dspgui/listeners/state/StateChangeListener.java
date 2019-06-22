package eu.vdmr.dspgui.listeners.state;

import eu.vdmr.dspgui.util.ApplicationState;

public interface StateChangeListener {
	public void stateChanged(ApplicationState oldState, ApplicationState newState); 
}
