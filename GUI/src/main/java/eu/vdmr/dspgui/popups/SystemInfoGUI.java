package eu.vdmr.dspgui.popups;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dsp.util.data.SystemInfoData;
import eu.vdmr.dsp.util.system.SystemInfo;

public class SystemInfoGUI  extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private static Logger LOG = LogManager.getLogger(SystemInfoGUI.class);
	JFrame parent;

	public SystemInfoGUI(final JFrame parent) {
		super(parent, "SystemInfo", true);
		LOG.debug("CTOR or SystemInfo");
		this.parent =parent;
		JPanel p = new JPanel(new BorderLayout());
		p.setPreferredSize(new Dimension(800,300));
		// get SystemInfo
		SystemInfo sInfo = new SystemInfo();
		SystemInfoData info = sInfo.getInfo();
		//TODO display it:
		JLabel topl = new JLabel("SystemInfo");
		p.add(topl,BorderLayout.NORTH);
		StringBuilder sb = new StringBuilder();
		sb.append("Known fileTypes: \n");
		AudioFileFormat.Type[] types = info.getFileTypes();
		for (AudioFileFormat.Type type : types){
			sb.append("       " + type.toString());
		}
		sb.append("\n\nMixers\n");
		Mixer.Info[]minfos = info.getMixerInfo();
		for (Info minfo : minfos ){
			sb.append(minfo.getName() + " : ");
			sb.append(minfo.getVendor()+ ";");
			sb.append(minfo.getVersion()+ ";");
			sb.append(minfo.getDescription());
			sb.append("\n");
			sb.append("SourceLines:\n");
			Line.Info[] linfos = sInfo.getSourceLineInfo(minfo);
			for (Line.Info linfo : linfos){
				sb.append("    " + linfo.toString() + "\n");
				if (linfo instanceof DataLine.Info){
					DataLine.Info dinfo = (DataLine.Info )linfo; // save cast: we asked for SourceLineInfos
					sb.append("    minBuffersize: " + dinfo.getMinBufferSize()+ ", maxBuffersize: " + dinfo.getMaxBufferSize() + "\n");
					sb.append("    supporting:\n");
					AudioFormat[] formats = dinfo.getFormats();
					for (AudioFormat format : formats){
						addFormat(sb,format);
						sb.append("\n");
					}
					sb.append("\n");
				} 
				
			}
			if (linfos.length == 0){
				sb.append("   no sourcelines..\n");
			}
			sb.append("TargetLines:\n");
			Line.Info[] tinfos = sInfo.getTargetLineInfo(minfo);
			for (Line.Info tinfo : tinfos){
				sb.append("    " + tinfo.toString() + "\n");
				if (tinfo instanceof DataLine.Info){
					DataLine.Info dinfo = (DataLine.Info )tinfo; // save cast: we asked for SourceLineInfos
					sb.append("    minBuffersize: " + dinfo.getMinBufferSize()+ ", maxBuffersize: " + dinfo.getMaxBufferSize() + "\n");
					sb.append("    supporting:\n");
					AudioFormat[] formats = dinfo.getFormats();
					for (AudioFormat format : formats){
						addFormat(sb,format);
						sb.append("\n");
					}
					sb.append("\n");
				} 
			}
			if (tinfos.length == 0){
				sb.append("   no targetlines..");
			}
			sb.append("\n\n");
		}
		JTextArea text = new JTextArea();
		text.setEditable(false);
		text.setText(sb.toString());
		JScrollPane scrollpane = new JScrollPane(text);
		p.add(scrollpane,BorderLayout.CENTER);
		JButton btCancel = new JButton("OK");
		btCancel.addActionListener(this);
		btCancel.setActionCommand("OK");
	    p.add(btCancel,BorderLayout.SOUTH);
	    getContentPane().add(p);
		pack();
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}
	
	private void addFormat(StringBuilder sb, AudioFormat format){
		sb.append("     Channels: " + format.getChannels());
		sb.append(";FrameRate: " + format.getFrameRate());
		sb.append(";FrameSize: " + format.getFrameSize());
		sb.append(";SampleRate: " + format.getSampleRate());
		sb.append(";SampleSizeInBits: " + format.getSampleSizeInBits());
		sb.append(";Encoding: " + format.getEncoding());
	}

}
