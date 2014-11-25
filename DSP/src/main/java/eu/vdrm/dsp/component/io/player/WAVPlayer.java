package eu.vdrm.dsp.component.io.player;

import java.util.concurrent.Callable;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import org.apache.log4j.Logger;

import eu.vdrm.dsp.util.audio.AbstractAudioSink;
import eu.vdrm.dsp.util.data.ExecutionResult;
import eu.vdrm.dsp.util.system.Settings;
import eu.vdrm.util.bits.BBConverter;

public class WAVPlayer extends AbstractAudioSink implements Callable<ExecutionResult>{
	private static Logger LOG = Logger.getLogger(WAVPlayer.class);
	private Mixer mixer;
	private SourceDataLine line;
	private short[] buffer = new short[DEFAULTBUFFERSIZE];
	
	public WAVPlayer(){
		super("WAVPlayer");
	}

	

	//@Override
	public ExecutionResult call() throws Exception {
		stopped = false;
		boolean ok = false;
		Mixer.Info[] minfos = AudioSystem.getMixerInfo();
		String mName = Settings.getInstance().getMixername();
		String pName = Settings.getInstance().getPlaybackString();
		for (Mixer.Info minfo : minfos){
//			String minfoName = minfo.getName();
//			String minfoDesc = minfo.getDescription();
			if (mName.equals(minfo.getName()) && minfo.getDescription().contains(pName)){
				mixer = AudioSystem.getMixer(minfo);
			}
		}
		if (mixer == null){
			LOG.error("Cannot find mixer with name " + mName);
			return new ExecutionResult(false);
		}
		if (! mixer.isOpen()) {
			mixer.open();
		}
		AudioFormat audioFormat = new AudioFormat(Encoding.PCM_SIGNED, 44100, 16, 2, 2*16/8,88200,true);
//		AudioFormat audioFormat = new AudioFormat(Encoding.PCM_SIGNED, 44100, 8, 2, 2 ,88200,false);
		DataLine.Info	info = new DataLine.Info(SourceDataLine.class, audioFormat, DEFAULTBUFFERSIZE);
//		boolean	bIsSupportedDirectly = AudioSystem.isLineSupported(info);
		line = (SourceDataLine)mixer.getLine(info);
		if (line != null){
			if (!line.isOpen()){
				line.open();
			}
			ok = true;
		}
		
		if (ok){
			line.start();
			int len = previous.getSamples(buffer, DEFAULTBUFFERSIZE);
			LOG.debug("before loop, read " + len);
			while (len != -1 && ! stopped){
				byte[] res = BBConverter.shortToByte2(buffer);
				/*int	nBytesWritten =*/ line.write(res, 0, len);
				len = previous.getSamples(buffer, DEFAULTBUFFERSIZE);
				LOG.debug("in loop, read " + len);
			}
			
		}
		line.stop();
		line.close();
		mixer.close();
		return new ExecutionResult(true);
	}
	
	
	
	

}
