package eu.vdrm.dspgui.listeners.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

import eu.vdrm.dspgui.app.DSPPanel;
import eu.vdrm.dspgui.component.DSPGuiComponent;
import eu.vdrm.dspgui.listeners.mouse.EditMouseListener;
import eu.vdrm.dspgui.listeners.state.StateChangeListener;
import eu.vdrm.dspgui.util.ApplicationState;
import eu.vdrm.dspgui.util.Globals;

public class MenuEditActionListener implements ActionListener, StateChangeListener {
	private static Logger LOG = Logger.getLogger(MenuEditActionListener.class);

	private JMenuItem mySelf;

	public MenuEditActionListener(JMenuItem editMenuItem){
		mySelf = editMenuItem;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		LOG.debug("MenuEditAction action");
		DSPPanel panel = Globals.getInstance().getPanel();
		// remove existing other state's mouse listeners and other stuff
		panel.setSelectRectangle(null);
		MouseListener[] oldL =  panel.getMouseListeners();
		for (MouseListener curr : oldL){
			panel.removeMouseListener(curr);
		}
		MouseMotionListener[] oldM =  panel.getMouseMotionListeners();
		for (MouseMotionListener curr : oldM){
			panel.removeMouseMotionListener(curr);
		}
		// create our mouse listener and register
//		EditMouseListener ml = new EditMouseListener();
//		panel.addMouseMotionListener(ml);
//		panel.addMouseListener(ml);
		Globals.getInstance().setState(ApplicationState.EDIT);
		// deselect all components
		List<DSPGuiComponent> comps = Globals.getInstance().getGuiComponents();
		for (DSPGuiComponent comp : comps){
			comp.mouseExit();
		}
		panel.repaint();
	}

	public void stateChanged(ApplicationState oldState,	ApplicationState newState) {
		if (   (ApplicationState.RUN.equals(newState))       // first must stop before edit again
			|| (ApplicationState.EDIT.equals(newState))	){   // cannot go to edit when in edit...
			mySelf.setEnabled(false);
		} else {
			mySelf.setEnabled(true);
			if ((ApplicationState.EDIT.equals(newState))
				&& ! (ApplicationState.EDIT.equals(oldState))){ // w're just entering edit state
				DSPPanel panel = Globals.getInstance().getPanel();
		        EditMouseListener ml = new EditMouseListener();
		        panel.addMouseListener(ml);
		        panel.addMouseMotionListener(ml);
			}
		}
	}

}
