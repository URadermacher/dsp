package eu.vdrm.util.xml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * reads a file with XML all in one line and outputs more or less formatted xml
 * @author ura03640
 *
 */
public class XMLIndentWriter {
	private static final int BUFF_SIZE = 100;
	private static final int INDENT = 3;
	private static final int DEBUG = 0;
	private static final int INFO = 1;
	private static final int WARN = 2;
	private static final int ERROR = 3;
	

	private FileReader reader = null;
	private FileWriter writer = null;
	private int written = 0;
	private int read = 0;
	private int inBuffPos = Integer.MAX_VALUE;
	private char[] outBuff = new char[BUFF_SIZE];
	private char[] inBuff = new char[BUFF_SIZE];
	private int indent = 0;
	private int logLevel = INFO; 
	private long bytesWritten = 0L;

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XMLIndentWriter writer = new XMLIndentWriter();
		long start = System.currentTimeMillis();
		writer.doit(args);
		writer.log("Transformation took " + (System.currentTimeMillis() - start) + " milliseconds", INFO);
	}

	private void doit(String[] args) {
		if (args.length != 2){
			log("need 2 params:  inputfile and outputfile ");
			return;
		}


		try {
			long charcnt = 0;
			initFiles(args);
			StringBuilder sb = new StringBuilder();

			boolean inTag = false;
			//boolean startTag = false;
			boolean endTag = false;
			boolean prefTag = false;
			boolean leavingTag = false;
			int endTagWritten = 0; // our last action was writing an endtag
			boolean firstAfterPrefTag = false;
			
			int readIntChar = readNextChar();
			
			while (readIntChar > -1){
				char readChar = (char) readIntChar;
				charcnt++;
				if (charcnt > 90){
					charcnt = charcnt+1;
				}
				log("reading " + readChar);
				if (readChar == '<') {  // start reading a tag
					inTag = true;
					sb.delete(0, sb.length() + 10); // empty buffer
					sb.append('<');
				} else if (inTag && readChar == '>'){  //tags end
					inTag = false;
					leavingTag = true;
					sb.append('>');
				} else if (inTag && readChar == '/'){  // we are reading an endtag
					endTag = true;
					sb.append('/');
				} else if (inTag && readChar == '?'){  // we are reading a prefix
					prefTag = true;
					sb.append('?');
				} else { // normal char
					if (inTag){
						sb.append(readChar);
					} else {
						write((char)readChar);
					}
				}
				
				// if we end a tag, what was it and what do we have to do..
				if (leavingTag){
					if (prefTag){ 
						write(sb.toString());
						prefTag = false;
						firstAfterPrefTag = true;
					} else 
						if (endTag){
						String endingTag = sb.toString();
						if (endTagWritten > 0){
							write('\n');
							if (endTagWritten > 1){
								decreaseIndentation();
							}
							writeIndent();
						} else {
							decreaseIndentation();
						}
						write(endingTag);
						endTag = false;
						endTagWritten++;
					} else { // must be end of start tag 
						if (! firstAfterPrefTag){ // not first tag after prefix?
							write('\n');
							if (endTagWritten < 2){
								increaseIndentation();
							}
							writeIndent();
						} else {
							write('\n');
							firstAfterPrefTag = false;
						}
						write(sb.toString());
						endTagWritten = 0;
					}
					leavingTag = false;
				}
				readIntChar = readNextChar();
			}
			writeEnd();
		} catch (IOException ioe){
			log("ioexception: " + ioe, ERROR);
			ioe.printStackTrace(System.out);
		} finally {
			try {
				if (reader != null){
					reader.close();
				}
				if (writer != null){
					writer.flush();
					writer.close();
				}
			} catch (IOException ioe){
				log("ioexception (closing..): " + ioe, ERROR);
				ioe.printStackTrace(System.out);				
			}
		}
		
	}

	private void initFiles(String[] args) throws IOException {
		File inFile = new File(args[0]);
		File outFile = new File(args[1]);
		
		reader = new FileReader(inFile);
		writer = new FileWriter(outFile);
		
		
		for (int i = 0; i < 100 ; i++){
			inBuff[i] = ' ';
			outBuff[i] = ' ';
		}
	}
	
	private int readNextChar() throws IOException{
		if (inBuffPos >= read){          // buffer completely read.
			read = reader.read(inBuff);  // read next chunk
			inBuffPos = 0;               // reposition read pointer
		}
		if (read == -1 ){
			return read;
		}
		return inBuff[inBuffPos++];
	}
		
	private void write (String tagcont) throws IOException {
		char[] pp = tagcont.toCharArray();
		for (char p : pp){
			write(p);
		}
	}
	
	private void write(char toWrite) throws IOException{
		log("write " + toWrite);
		if (written < (BUFF_SIZE - 1)){
			outBuff[written] = toWrite;
			written++;
			return;
		}
		outBuff[written] = toWrite;
		writer.write(outBuff);
		written = 0;
		bytesWritten++;
		if (bytesWritten % 10000 == 0){
			log("characters written: " + bytesWritten, INFO);
		}
	}
	
	private void writeEnd() throws IOException {
		if (written > 0){
			writer.write(outBuff, 0, written);
		}
	}
	
	private void log(String msg){
		log(msg, DEBUG);
	}
	
	private void log(String msg, int level){
		if (level >= logLevel){
			System.out.println(msg);
		}
	}
	
	private void decreaseIndentation(){
		indent -= INDENT;
		if (indent < 0){
			log("oeps, indent < 0!");
			indent  = 0;
		}
	}

	private void increaseIndentation(){
		indent += INDENT;
	}
	
	private void writeIndent() throws IOException {
		int x = indent;
		while (x > 0){
			write(' ');
			x--;
		}
	}


}
