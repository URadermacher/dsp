package eu.vdmr.dspgui.listeners.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.util.Globals;
import eu.vdmr.dspgui.util.filefilter.ProjectFileFilter;
import eu.vdmr.dspgui.util.project.ProjectFiler;

public class MenuLoadActionListener implements ActionListener {
	private static Logger LOG = LogManager.getLogger(MenuLoadActionListener.class);


	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(new ProjectFileFilter());
		int res = fc.showOpenDialog(Globals.getInstance().getMasterFrame());
        if (res == JFileChooser.APPROVE_OPTION) {
        	File file = null;
            file = fc.getSelectedFile();
            try {
            	file.getCanonicalPath();
			} catch (IOException e2) {
            	JOptionPane.showMessageDialog(Globals.getInstance().getMasterFrame(), "Illegal file name");
            	return;
			}
			if (!file.exists()){
            	JOptionPane.showMessageDialog(Globals.getInstance().getMasterFrame(), "File not found");
            	return;
			} else{
				loadFile(file);
			}
        } else {
            LOG.debug("Load command cancelled by user.");
        }
	}
	
	private void loadFile(File target){
		ProjectFiler filer = new ProjectFiler();
		try {
			filer.loadFile(target);
		} catch (IOException e) {
			try {
				LOG.error("Error writing file " + target.getCanonicalPath() + ":" + e,e);
			} catch (IOException ioe2){
				LOG.error("Error writing file (getCanonical path also throwed ("+ioe2+")" + e,e);
			}
		}
	}

}
