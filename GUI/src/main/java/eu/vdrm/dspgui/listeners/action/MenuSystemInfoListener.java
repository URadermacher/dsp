package eu.vdrm.dspgui.listeners.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import eu.vdrm.dspgui.popups.SystemInfoGUI;
import eu.vdrm.dspgui.util.Globals;

public class MenuSystemInfoListener implements ActionListener {
	private static Logger LOG = Logger.getLogger(MenuSystemInfoListener.class);

	public void actionPerformed(ActionEvent arg0) {
		LOG.debug("MenuSystemInfo action");
		JFrame frame = Globals.getInstance().getMasterFrame();
		SystemInfoGUI gui = new SystemInfoGUI(frame);
		gui.setVisible(true);
	}

}
