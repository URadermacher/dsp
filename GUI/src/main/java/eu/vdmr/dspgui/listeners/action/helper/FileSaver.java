package eu.vdmr.dspgui.listeners.action.helper;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.util.Globals;
import eu.vdmr.dspgui.util.filefilter.ProjectFileFilter;
import eu.vdmr.dspgui.util.project.ProjectFiler;

public class FileSaver {
	private static Logger LOG = LogManager.getLogger(FileSaver.class);
	
	/**
	 * 
	 * @return true if really saved, else false
	 */
	public boolean saveFile(){
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
            	return false;
			}
			if (file.exists()){
            	int response = JOptionPane.showConfirmDialog(Globals.getInstance().getMasterFrame(), "File exists. Overwrite?", "Confirmation", JOptionPane.YES_NO_OPTION);
            	if (response == 0){ //OK, overwrite
            		return saveFile(file);
            	} else {
            	     LOG.debug("Save command cancelled by user (exists).");
            	     return false;
            	}
			} else{
				return saveFile(file);
			}
        } else {
            LOG.debug("Save command cancelled by user.");
            return false;
        }
		
	}
	private boolean saveFile(File target){
		ProjectFiler filer = new ProjectFiler();
		try {
			filer.saveFile(target);
		} catch (IOException e) {
			try {
				LOG.error("Error writing file " + target.getCanonicalPath() + ":" + e,e);
				return false;
			} catch (IOException ioe2){
				LOG.error("Error writing file (getCanonical path also throwed ("+ioe2+")" + e,e);
				return false;
			}
		}
		return true;
	}
}
