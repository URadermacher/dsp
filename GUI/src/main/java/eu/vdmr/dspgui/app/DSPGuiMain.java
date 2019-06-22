package eu.vdmr.dspgui.app;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DSPGuiMain {
	 private static Logger LOG = LogManager.getLogger(DSPGuiMain.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LOG.info("Starting DSPApplication");
		configure(args);
		final DSPGui gui = new DSPGui();
       
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                gui.start();
            }
        });
		
	}
	
	private static void configure(String[] args){
		// noppes
	}

}
