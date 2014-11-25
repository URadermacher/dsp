package eu.vdrm.dspgui.util.filefilter;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class ProjectFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()){
			return true;
		}
		int idx = file.getName().lastIndexOf(".");
		if (idx > 0 ){
			String ss = file.getName().substring(idx+1);
			if ("dgp".equalsIgnoreCase(ss)){
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "RagaDSP Project file";
	}

}
