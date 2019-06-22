package eu.vdmr.dspgui.popups;

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
import javax.swing.SpringLayout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.util.layout.SpringUtilities;

public class GeneralTextPopUp extends JDialog implements ActionListener {
	private static Logger LOG = LogManager.getLogger(GeneralTextPopUp.class);

	private static final long serialVersionUID = 1L;
	private String resultbutton;
	private JTextField[] myTexts;
	
	public GeneralTextPopUp(final JFrame parent, String name, String[] labels) {
		super(parent, name, true);
		LOG.debug("ctor of " + name);
		JPanel p = new JPanel(new BorderLayout());
		// this.setSize(300, 800);
		// this.setBounds(500, 500, 300, 300);
		p.setPreferredSize(new Dimension(800, 300));
		JLabel topLabel = new JLabel("enter properties for " + name);
		p.add(topLabel, BorderLayout.NORTH);
/**/
		JPanel tp   = new JPanel(new SpringLayout());
		p.add(tp, BorderLayout.CENTER);
		
		myTexts = new JTextField[labels.length];
		for (int i = 0; i < labels.length; i++){
			JLabel newLabel = new JLabel(labels[i], JLabel.TRAILING);
			tp.add(newLabel);
			JTextField myText = new JTextField(20);
			newLabel.setLabelFor(myText);
			tp.add(myText);
			myTexts[i] = myText;
		}
		//Lay out the panel.
        SpringUtilities.makeCompactGrid(tp,
        								labels.length, 2, //rows, cols
                                        6, 6,        	  //initX, initY
                                        6, 6);      	  //xPad, yPad
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

	public String getValue(int idx) {
		return myTexts[idx].getText();
	}


	private void close() {
		setVisible(false);
	}
}
