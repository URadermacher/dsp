package eu.vdmr.dsp.component.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import eu.vdmr.dsp.component.DSPComponentImpl;

public abstract class AbstractDataWriter extends DSPComponentImpl implements DataWriter {
	
	protected DataWriterType type;
	
	protected String fileName;
	protected File outFile;
	protected boolean stopped = false;
	
	public AbstractDataWriter(DataWriterType type){
		this.type = type;
	}
	
	public void setFileName(String path) throws IOException{
		this.fileName = path;
		outFile = new File(path);
		setFile(outFile);
	}
	
	public void setFile(File file) throws IOException{
		this.fileName = file.getCanonicalPath();
		outFile = file;
		if (outFile.isDirectory()){
			throw new IllegalStateException("File \"" + fileName + "\" is a directory!");
		}
		if (outFile.exists()){
			//throw new FileExistsException("File \"" + fileName + "\" already exists!");
			outFile.delete();
		}
		if (! outFile.createNewFile()){
			throw new IllegalStateException("File \"" + fileName + "\" cannot be created!");
		}
		if (! outFile.canWrite()){
			throw new IllegalStateException("Cannot write to File \"" + fileName + "\"!");
		}
		fileSet();
	}
	
	
	/**
	 * what to do when the file is known and checked, depends on the writer..
	 */
	protected abstract void fileSet() throws FileNotFoundException, IOException ;
	
	public DataWriterType getWriterType(){
		return type;
	}
	
	public File getFile(){
		return outFile;
	}
	@Override
	public int getSamples(short[] buffer, int length) {
		throw new IllegalStateException("getSamples90 not allowed for  " + this.getClass().getCanonicalName());
	}

}
