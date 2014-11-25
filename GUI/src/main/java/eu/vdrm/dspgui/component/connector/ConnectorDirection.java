package eu.vdrm.dspgui.component.connector;

import org.apache.log4j.Logger;


/**
 * There are 4 possibilities
 * 	1) "Emitting" a connector to a previous to be used by a getSamples() call
 *  2) "pulling" a connector for the side which calls getSamples()
 *  3) "pushing" a (data-)pushing component
 *  4) "Receiving" connector to which something is pushed 
 * @author ura03640
 *
 */
public enum ConnectorDirection {
	EMITTING ("emitting"),
	PULLING ("pulling");
	

	private static Logger LOG = Logger.getLogger(ConnectorDirection.class);
	private String name;
	
	ConnectorDirection(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public static ConnectorDirection forName(String name){
		if (name.equals(EMITTING.getName())){
			return EMITTING;
		} else if (name.equals(PULLING.getName())){
			return PULLING;
		} else {
			LOG.error("ConnectorDirection " + name + " is not known!");
		}
		return null;
	}
}
