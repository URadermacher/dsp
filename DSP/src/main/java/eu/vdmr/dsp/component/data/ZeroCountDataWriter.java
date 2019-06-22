package eu.vdmr.dsp.component.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class ZeroCountDataWriter extends AbstractDataWriter{
	

	
//	private FileOutputStream fos;
//  private DataOutputStream dos;
    private BigInteger sampleCount = new BigInteger("0");
    private BigInteger incr = new BigInteger("1");
    BufferedWriter realWriter;// = new BufferedWriter(new FileWriter("D:/tmp/cos.txt"));
	
    

	public ZeroCountDataWriter() {
		super(DataWriterType.ZEROCNTType);
	}

	public void setDataClass(Class<?> clazz) {
		// TODO Auto-generated method stub
		
	}
	
	public synchronized void writeHeader(Object[] parms) throws IOException {
		if (! stopped){
			realWriter.write("##;##\nSample;Value\n");
//		      dos.writeChar('#' );
//		      dos.writeChar('#' );
//		      dos.writeChar(';' );
//		      dos.writeChar('#' );
//		      dos.writeChar('#' );
//		      dos.writeChar('\n' );
//		      dos.writeChar('S' );
//		      dos.writeChar('a' );
//		      dos.writeChar('m' );
//		      dos.writeChar('p' );
//		      dos.writeChar('l' );
//		      dos.writeChar('e' );
//		      dos.writeChar(';' );
//		      dos.writeChar('V' );
//		      dos.writeChar('a' );
//		      dos.writeChar('l' );
//		      dos.writeChar('u' );
//		      dos.writeChar('e' );
//		      dos.writeChar('\n' );
			}
	}

	
	/**
	 * ZeroCountDataWriter expects just one object in data[0] : an Integer..
	 * 
	 * NOTA BENE this method may still be called after the 'stop'-menu action was done and the end() method of this object was called 
	 * which closed the stream.
	 * No we have a kind of security with the stopped switch, BUT, this way we will miss the last data. We need another mechanism, to close the stream 
	 * when the stop menu is hit, but write first all data out. 
	 * 
	 * We can do nothing on the end() call and wait for out previous to indicate that data are finished.. (another method again to be used....) 
	 */
	public synchronized void  addData(Object[] data) throws IOException {
		if (! stopped){
			sampleCount.add(incr);
			String sampleCountString = sampleCount.toString();
			realWriter.write(sampleCountString + ";" + ((Integer)data[0]).toString() + "\n");

//			dos.writeChars(sampleCountString);
//			dos.writeChar(';' );
//			dos.writeChars((((Integer)data[0]).toString()));
//			dos.writeChar('\n' );
		}
	}

	/** 
	 * file is set and ready, create writer
	 * @throws IOException 
	 */
	protected  void fileSet() throws IOException {
//		fos = new FileOutputStream(outFile, false);
//		dos = new DataOutputStream(fos);
		realWriter = new BufferedWriter(new FileWriter(outFile.getAbsolutePath()));
	}

	
	public synchronized void end() throws IOException {
		stopped = true;
//		if (dos != null) {
//			dos.close();
//		}
			if (realWriter != null){
				realWriter.flush();
				realWriter.close();
			}
		
	}

}
