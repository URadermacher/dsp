package eu.vdmr.dsp.util.data;

public class ExecutionResult {
	
	private boolean success;
	
	
	public ExecutionResult(){
		
	}
	public ExecutionResult(boolean ok){
		this.success = ok;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
