package eu.vdrm.dsp.util.system;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

import eu.vdrm.dsp.util.data.SystemInfoData;

public class SystemInfo {
	
	public SystemInfoData getInfo(){
		SystemInfoData info = new SystemInfoData();
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		AudioFileFormat.Type[] filetypes = AudioSystem.getAudioFileTypes();
		info.setMixerInfo(mixerInfos);
		info.setFileTypes(filetypes);
		
//		for (Info minfo : mixerInfos){
//			
//		}
		
		
		
		return info;
	}
	
	public Line.Info[] getSourceLineInfo(Mixer.Info mixerinfo){
		Mixer mixer = AudioSystem.getMixer(mixerinfo);
		return mixer.getSourceLineInfo();
	}
	public Line.Info[] getTargetLineInfo(Mixer.Info mixerinfo){
		Mixer mixer = AudioSystem.getMixer(mixerinfo);
		return mixer.getTargetLineInfo();
	}
}
