package eu.vdmr.dsp.component.data;

import java.io.File;
import java.io.IOException;

import eu.vdmr.dsp.exceptions.FileExistsException;

public interface DataWriter {
	
	public void setDataClass(Class<?> clazz);
	
	public void setFileName(String path) throws IOException, IllegalStateException;
	
	public void setFile(File file) throws IOException, IllegalStateException, FileExistsException;
	
	
	/**
	 * get the file to write to (or null if not yet set)
	 * @return
	 */
	public File getFile();
	
	public void writeHeader(Object[] parms) throws IOException;
	
	public void addData(Object[] data) throws IOException;
	
	public void end()  throws IOException;
	
	public DataWriterType getWriterType();
	
	
	
}
