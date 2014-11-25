package eu.vdrm.util.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * a FileWriter with some additions to easily write xml files
 * @author ura03640
 *
 */
public class XMLFileWriter extends FileWriter {
//	private static Logger LOG = Logger.getLogger(XMLFileWriter.class);
	private static String[] ind = new String[] {""," ","  ","   ","    ","     ","      ","       ","        ","         ","           "};

	public XMLFileWriter(File file, boolean append) throws IOException {
		super(file, append);
	}

	public void startLabel(int indent,String l) throws IOException{
		write(ind[indent] + "<" + l + ">\n");
	}
	public void startAttributedLabel(int indent,String l, String[] names, String[] values) throws IOException{
		if (names == null || values == null){
			throw new IllegalArgumentException("startAtributedLabel must have names and values");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(ind[indent] + "<" + l + " ");
		for (int i = 0; i < names.length; i++){
			sb.append(names[i] + "=\"" + values[i] + "\" ");
		}
		sb.append(">\n");
		write(sb.toString());
	}
	public void endLabel(int indent,String l)throws IOException{
		write(ind[indent] + "</" + l + ">\n");
	}
	public void makeLabel(int indent,String l, String content)throws IOException{
		write(ind[indent] + "<" + l + ">" + content + "</" + l + ">\n");
	}

}
