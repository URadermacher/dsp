package eu.vdrm.dspgui.listeners.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

import eu.vdrm.dspgui.app.DSPPanel;
import eu.vdrm.dspgui.listeners.state.StateChangeListener;
import eu.vdrm.dspgui.util.ApplicationState;
import eu.vdrm.dspgui.util.Globals;

public class MenuSelectActionListener implements ActionListener, StateChangeListener {
	private static Logger LOG = Logger.getLogger(MenuSelectActionListener.class);
	
	private JMenuItem mySelf;

	public MenuSelectActionListener(JMenuItem selectMenuItem){
		mySelf = selectMenuItem;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		LOG.debug("MenuSelectAction action");
		DSPPanel panel = Globals.getInstance().getPanel();
		MouseListener[] oldL =  panel.getMouseListeners();
		for (MouseListener curr : oldL){
			panel.removeMouseListener(curr);
		}
		MouseMotionListener[] oldM =  panel.getMouseMotionListeners();
		for (MouseMotionListener curr : oldM){
			panel.removeMouseMotionListener(curr);
		}
//		SelectMouseListener ml = new SelectMouseListener();
//		panel.addMouseMotionListener(ml);
//		panel.addMouseListener(ml);
		Globals.getInstance().setState(ApplicationState.SEL);
		panel.repaint();
	}
	public void stateChanged(ApplicationState oldState,	ApplicationState newState) {
		if (  (ApplicationState.RUN.equals(newState))
			||(ApplicationState.SEL.equals(newState)) ){
			mySelf.setEnabled(false);
		} else {
			mySelf.setEnabled(true);
		}
	}

}
