package eu.vdmr.dspgui.listeners.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.popups.SystemInfoGUI;
import eu.vdmr.dspgui.util.Globals;

public class MenuSystemInfoListener implements ActionListener {
	private static Logger LOG = LogManager.getLogger(MenuSystemInfoListener.class);

	public void actionPerformed(ActionEvent arg0) {
		LOG.debug("MenuSystemInfo action");
		JFrame frame = Globals.getInstance().getMasterFrame();
		SystemInfoGUI gui = new SystemInfoGUI(frame);
		gui.setVisible(true);
	}

}
