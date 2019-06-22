package eu.vdmr.dspgui.component.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum ConnectorPlacement {
	NORTH ("north"),
	EAST ("east"),
	SOUTH ("south"),
	WEST ("west");
	private static Logger LOG = LogManager.getLogger(ConnectorPlacement.class);
	
	private String name;
	
	ConnectorPlacement(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	public String getName(){
		return name;
	}

	public static ConnectorPlacement forName(String name){
		if (name.equals(NORTH.getName())){
			return NORTH;
		} else if (name.equals(EAST.getName())){
			return EAST;
		} else if (name.equals(SOUTH.getName())){
			return SOUTH;
		} else if (name.equals(WEST.getName())){
			return WEST;
		} else {
			LOG.error("ConnectorPlacement " + name + " is not known!");
		}
		return null;
	}

}
