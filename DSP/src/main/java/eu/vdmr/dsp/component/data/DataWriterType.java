package eu.vdmr.dsp.component.data;

public enum DataWriterType {
	FFTDataType("FFT"),
	ZEROCNTType("ZRCNT"),
	OTHER("OTHER");
	
	private String name;
	
	DataWriterType(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
}
