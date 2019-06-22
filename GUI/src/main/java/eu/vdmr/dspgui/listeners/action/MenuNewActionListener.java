package eu.vdmr.dspgui.listeners.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.component.DSPGuiComponent;
import eu.vdmr.dspgui.util.Globals;

public class MenuNewActionListener implements ActionListener {
	private static Logger LOG = LogManager.getLogger(MenuNewActionListener.class);

	public void actionPerformed(ActionEvent e) {
		LOG.debug("MenuNewActionListener.actionPerformed");
		List<DSPGuiComponent> comps = Globals.getInstance().getGuiComponents();
		if (comps.size() > 0) {
			int answ = JOptionPane.showConfirmDialog(Globals.getInstance().getMasterFrame(), "You wonna save current stuff?", "ConfirmQuestion", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			if (answ == JOptionPane.CANCEL_OPTION){
				return; // do nothing
			}
			if (answ == JOptionPane.NO_OPTION){ // no save required
				Globals.getInstance().freeGuiComponents();
				Globals.getInstance().getPanel().repaint();
			}
			if (answ == JOptionPane.YES_OPTION) {
//				FileSaver saver = new FileSaver();
//				if (saver.saveFile()) {
//					Globals.getInstance().freeGuiComponents();
//					Globals.getInstance().getPanel().repaint();
//				} else {
//					LOG.debug("not saved, thus not cleaned..");
//				}
			}
		}


	}

}
