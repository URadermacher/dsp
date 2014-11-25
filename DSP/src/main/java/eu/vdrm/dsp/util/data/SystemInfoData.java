package eu.vdrm.dsp.util.data;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;

public class SystemInfoData {
	Info[] mixerInfo;
	AudioFileFormat.Type[] fileTypes;
	
	public Info[] getMixerInfo() {
		return mixerInfo;
	}
	public void setMixerInfo(Mixer.Info[] mixerInfo) {
		this.mixerInfo = mixerInfo;
	}
	public AudioFileFormat.Type[] getFileTypes() {
		return fileTypes;
	}
	public void setFileTypes(AudioFileFormat.Type[] fileTypes) {
		this.fileTypes = fileTypes;
	}

}
