package eu.vdrm.dspgui.popups.data;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

public class DataWriterPopUp extends JDialog implements ActionListener {
	private static Logger LOG = Logger.getLogger(DataWriterPopUp.class);

	private static final long serialVersionUID = 1L;
	private String resultbutton;
	private JTextField[] myTexts;
	private JFileChooser fileChooser;
	
	public DataWriterPopUp(final JFrame parent, String name) {
		super(parent, name, true);
		LOG.debug("ctor of " + name);
		JPanel p = new JPanel(new BorderLayout());
		// this.setSize(300, 800);
		// this.setBounds(500, 500, 300, 300);
		p.setPreferredSize(new Dimension(800, 300));
		JLabel topLabel = new JLabel("enter properties for " + name);
		p.add(topLabel, BorderLayout.NORTH);
/**/
		JPanel tp   = new JPanel(new BorderLayout());
		p.add(tp, BorderLayout.CENTER);
		fileChooser = new JFileChooser();
		
		
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
		fileChooser.showSaveDialog(this);	
	}

	public void setText(int idx, String txt) {
		myTexts[idx].setText(txt);
	}
	
	public void actionPerformed(ActionEvent e) {
		if ("ok".equals(e.getActionCommand())) {
			resultbutton = "OK";
			close();
		} else if ("cancel".equals(e.getActionCommand())) {
			resultbutton = "CANCEL";
			close();
		}
	}
	

	public String getResultbutton() {
		return resultbutton;
	}

	public File getSelectedFile() {
		return fileChooser.getSelectedFile();
	}


	private void close() {
		setVisible(false);
	}
}
