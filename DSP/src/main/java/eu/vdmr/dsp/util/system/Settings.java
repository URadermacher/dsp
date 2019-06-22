package eu.vdmr.dsp.util.system;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Settings {
	private static Logger LOG = LogManager.getLogger(Settings.class);
	
	public static final String MIXERNAME = "MixerName";
	public static final String PLAYBACK = "Playback";
	private static final String PROPNAME = "DSP.properties";
	
	private static Settings _instance;
	
	static {
		_instance = new Settings();
	}
	
	private Properties props;
	
	private Settings() {
		props = new Properties();
		try {
			InputStream in = ClassLoader.getSystemResourceAsStream(PROPNAME);
			props.load(in);
		} catch (FileNotFoundException e) {
			LOG.error("Cannot find properties file " + PROPNAME,e);
			throw new IllegalArgumentException("Cannot find properties file " + PROPNAME,e);
		} catch (IOException e) {
			LOG.error("IOException on properties file " + PROPNAME + ": " + e,e);
			throw new IllegalArgumentException("IOException on properties file " + PROPNAME + ": " + e,e);
		}
	}
	
	public static Settings getInstance(){
		return _instance;
	}
	
	public String getMixername(){
		return props.getProperty(MIXERNAME);
	}
	
	public String getPlaybackString(){
		return props.getProperty(PLAYBACK);
	}
}
