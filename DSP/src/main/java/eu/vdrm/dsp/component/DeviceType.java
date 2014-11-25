package eu.vdrm.dsp.component;

public enum DeviceType {
	//ALLTYPES("alltypes"),
	NOTYPE("notype"),
	SOURCE("source"),
	PROCESSOR("processor"),
	MONITOR("monitor"),
	SINK("sink");
	
	private String name;
	
	DeviceType(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	

}
