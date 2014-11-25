package eu.vdrm.dspgui.listeners.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import eu.vdrm.dspgui.listeners.action.helper.FileSaver;

public class MenuSaveActionListener implements ActionListener {
	private static Logger LOG = Logger.getLogger(MenuSaveActionListener.class);


	public void actionPerformed(ActionEvent e) {
		LOG.debug("MenuSaveActionListener.actionPerformed()");
		FileSaver saver = new FileSaver();
		saver.saveFile();
	}
	


}
