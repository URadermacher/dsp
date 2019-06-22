package eu.vdmr.dspgui.listeners.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.listeners.action.helper.FileSaver;

public class MenuSaveActionListener implements ActionListener {
	private static Logger LOG = LogManager.getLogger(MenuSaveActionListener.class);


	public void actionPerformed(ActionEvent e) {
		LOG.debug("MenuSaveActionListener.actionPerformed()");
		FileSaver saver = new FileSaver();
		saver.saveFile();
	}
	


}
