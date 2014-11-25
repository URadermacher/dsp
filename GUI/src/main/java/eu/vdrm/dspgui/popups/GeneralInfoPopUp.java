package eu.vdrm.dspgui.popups;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.apache.log4j.Logger;

import eu.vdrm.dspgui.util.layout.SpringUtilities;

public class GeneralInfoPopUp extends JDialog implements ActionListener {
	private static Logger LOG = Logger.getLogger(GeneralInfoPopUp.class);

	private static final long serialVersionUID = 1L;
	private String resultbutton;
	private JComponent[] myComponents;
	
	public GeneralInfoPopUp(final JFrame parent, String name, String[] labels, JComponent[] myComponents) {
		super(parent, name, true);
		LOG.debug("ctor of " + name);
		this.myComponents = myComponents;
		JPanel p = new JPanel(new BorderLayout());
		// this.setSize(300, 800);
		// this.setBounds(500, 500, 300, 300);
		p.setPreferredSize(new Dimension(800, 300));
		JLabel topLabel = new JLabel("enter properties for " + name);
		p.add(topLabel, BorderLayout.NORTH);
/**/
		JPanel tp   = new JPanel(new SpringLayout());
		p.add(tp, BorderLayout.CENTER);
		
		for (int i = 0; i < labels.length; i++){
			JLabel newLabel = new JLabel(labels[i], JLabel.TRAILING);
			tp.add(newLabel);
			newLabel.setLabelFor(myComponents[i]);
			tp.add(myComponents[i]);
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

	public JComponent getValue(int idx) {
		return myComponents[idx];
	}


	private void close() {
		setVisible(false);
	}
}
