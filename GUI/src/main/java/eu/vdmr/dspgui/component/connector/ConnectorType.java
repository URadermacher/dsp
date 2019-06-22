package eu.vdmr.dspgui.component.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum ConnectorType {
	STEREO ("stereo"),
	MONO ("mono"),
	MONO_R ("mono_r"),  // MONO as half stereo
	MONO_L ("mono_l"),  // MONO as half stereo
	DATA ("data"),  
	EVENT ("event"); // idea copied from Puredata. Not used (yet)
	
	private static Logger LOG = LogManager.getLogger(ConnectorType.class);
	private String name;
	
	ConnectorType(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	public String getName(){
		return name;
	}
	
	public static ConnectorType forName(String name){
		if (name.equals(STEREO.getName())){
			return STEREO;
		} else if (name.equals(MONO.getName())){
			return MONO;
		} else if (name.equals(MONO_R.getName())){
			return MONO_R;
		} else if (name.equals(MONO_L.getName())){
			return MONO_L;
		} else if (name.equals(DATA.getName())){
			return DATA;
		} else if (name.equals(EVENT.getName())){
			return EVENT;
		} else {
			LOG.error("Connector type " + name + " is not known!");
		}
		return null;
	}
	
}
