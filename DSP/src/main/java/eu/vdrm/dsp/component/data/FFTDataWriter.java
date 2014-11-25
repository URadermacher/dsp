package eu.vdrm.dsp.component.data;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.math3.complex.Complex;

public class FFTDataWriter extends AbstractDataWriter{
  private FileOutputStream fos;
  private DataOutputStream dos;
  private BufferedWriter realWriter;// = new BufferedWriter(new FileWriter("D:/tmp/cos.txt"));

	public FFTDataWriter() {
		super(DataWriterType.FFTDataType);
	}

	public void setDataClass(Class<?> clazz) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * we expect an array of Complex numbers.
	 * We write only the real part as a long
	 */
	public void addData(Object[] data) throws IOException {
		Complex[] myData = (Complex[])data;
		for (int i = 0; i < myData.length; i++){
		    dos.writeDouble(myData[i].getReal());
		    realWriter.write(""+myData[i].getReal()+";");
		}
		realWriter.newLine();
	}

	
	/** 
	 * file is set and ready, create writer
	 * @throws FileNotFoundException 
	 */
	protected  void fileSet() throws FileNotFoundException, IOException {
      fos = new FileOutputStream(outFile, false);
      dos = new DataOutputStream(fos);
      realWriter = new BufferedWriter(new FileWriter(outFile.getAbsolutePath()+ "_R"));


	}

	public void writeHeader(Object[] parms) throws IOException {
		// TODO Auto-generated method stub
		
	}
    public synchronized void end() throws IOException {
        stopped = true;
      if (dos != null) {
          dos.close();
      }
      if (realWriter != null){
          realWriter.close();
      }
    }
	
}
