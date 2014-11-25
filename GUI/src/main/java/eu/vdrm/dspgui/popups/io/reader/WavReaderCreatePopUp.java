package eu.vdrm.dspgui.popups.io.reader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;


public class WavReaderCreatePopUp extends JDialog implements ActionListener {
	private static Logger LOG = Logger.getLogger(WavReaderCreatePopUp.class);

	private static final long serialVersionUID = 1L;
	private String resultbutton;
	private final JFrame parent;
	private final JTextField filetext;
	/**
	 * ctor
	 * @param parent the ultimate parent frame
	 * @param reader WAVReader (implements RagaDSPComponent)
	 */
	public WavReaderCreatePopUp(final JFrame parent){
		super(parent, "Edit WavReader", true);
		this.parent =parent;
		LOG.debug("ctor of WavReaderCreatePopUp");
		JPanel p = new JPanel(new BorderLayout());
//		this.setSize(300, 800);
//		this.setBounds(500, 500, 300, 300);
		p.setPreferredSize(new Dimension(800,300));
		JLabel topLabel = new JLabel("enter properties for WavReader");
		p.add(topLabel, BorderLayout.NORTH);
		JLabel fileLabel = new JLabel("filename");
		p.add(fileLabel, BorderLayout.WEST);
		filetext = new JTextField();
		filetext.setPreferredSize(new Dimension(300,30));
		p.add(filetext, BorderLayout.CENTER);
		JButton btBrowse = new JButton("search file..");
		btBrowse.setActionCommand("browse");
		btBrowse.addActionListener(this);
		p.add(btBrowse, BorderLayout.EAST);
		JPanel southPanel = new JPanel(new FlowLayout());
		JButton btOK = new JButton("OK");
		btOK.addActionListener(this);
		btOK.setActionCommand("ok");
		southPanel.add(btOK);
		JButton btCancel = new JButton("Cancel");
		btCancel.addActionListener(this);
		btCancel.setActionCommand("cancel");
		southPanel.add(btCancel);
		p.add(southPanel, BorderLayout.SOUTH);
		getContentPane().add(p);
		pack();
	}


	public void actionPerformed(ActionEvent e) {
		 if ("ok".equals(e.getActionCommand())) {
			 resultbutton = "OK";
			 if (checkfile(filetext.getText())){
				 close();
			 }
		 } else  if ("cancel".equals(e.getActionCommand())){
			 resultbutton = "CANCEL";
			 close();
		 } else { // will be browse for file
			JFileChooser fc = new JFileChooser();
			//fc.addChoosableFileFilter(new WAVFileFilter());
			int res = fc.showOpenDialog(parent);
	        if (res == JFileChooser.APPROVE_OPTION) {
	        	File file = null;
	            file = fc.getSelectedFile();
	            String filename = null;
	            try {
	            	filename = file.getCanonicalPath();
				} catch (IOException e2) {
	            	JOptionPane.showMessageDialog(parent, "Illegal file name");
	            	return;
				}
	            if (! checkfile(filename)){
	            	return;
				}
				// file is OK
				filetext.setText(file.getAbsolutePath());
	        } else {
	            LOG.debug("Open command cancelled by user.");
	        }
		 }
	}

	public boolean checkfile(String filename){
//		boolean isOK = WAVReader.isWaveFile(filename);
//		if (!isOK){
//        	JOptionPane.showMessageDialog(parent, "Not a valid wav file!");
//        	return false;
//		}
		return true;
	}
	
	public String getResultbutton() {
		return resultbutton;
	}

	public String getFileName() {
		return filetext.getText();
	}
	
	private void close(){
		setVisible(false);
	}

}
