package eu.vdrm.dspgui.util.filefilter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class WAVFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()){
			return true;
		}
		int idx = file.getName().lastIndexOf(".");
		if (idx > 0 ){
			String ss = file.getName().substring(idx+1);
			if ("wav".equalsIgnoreCase(ss)){
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "WAV encoded music file";
	}

}
