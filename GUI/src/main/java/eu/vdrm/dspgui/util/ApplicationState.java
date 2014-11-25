package eu.vdrm.dspgui.util;

public enum ApplicationState {
		EDIT ("edit"),
		RUN ("running"),
		SEL ("select");
		
		private String name;
		
		ApplicationState(String name){
			this.name = name;
		}
		
		public String toString(){
			return name;
		}
		

}
