package eu.vdrm.dspgui.popups.generator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

public class SingleTextCreatePopUp extends JDialog implements ActionListener {
	private static Logger LOG = Logger.getLogger(SingleTextCreatePopUp.class);

	private static final long serialVersionUID = 1L;
	private String resultbutton;
	private final JTextField myText;


	
	public void setText(String txt) {
		myText.setText(txt);
	}
	/**
	 * ctor
	 * 
	 * @param parent
	 *            the ultimate parent frame
	 */
	public SingleTextCreatePopUp(final JFrame parent, String name, String labl) {
		super(parent, name, true);
		LOG.debug("ctor of " + name);
		JPanel p = new JPanel(new BorderLayout());
		// this.setSize(300, 800);
		// this.setBounds(500, 500, 300, 300);
		p.setPreferredSize(new Dimension(800, 300));
		JLabel topLabel = new JLabel("enter properties for " + name);
		p.add(topLabel, BorderLayout.NORTH);
		JLabel fileLabel = new JLabel(labl);
		p.add(fileLabel, BorderLayout.WEST);
		myText = new JTextField();
		
		myText.setPreferredSize(new Dimension(300, 30));
		p.add(myText, BorderLayout.CENTER);
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
			close();
		} else if ("cancel".equals(e.getActionCommand())) {
			resultbutton = "CANCEL";
			close();
		}
	}

	public String getResultbutton() {
		return resultbutton;
	}

	public String getValue() {
		return myText.getText();
	}

	private void close() {
		setVisible(false);
	}

}
