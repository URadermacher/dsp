package eu.vdrm.dsp.exceptions;

public class FileExistsException extends IllegalStateException {

	private static final long serialVersionUID = 1L;
	
	public FileExistsException(String msg){
		super(msg);
	}

	public FileExistsException(String msg, Throwable cause){
		super(msg, cause);
	}

	public FileExistsException(Throwable cause){
		super(cause);
	}
}
