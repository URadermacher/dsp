package eu.vdmr.ztt;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TransparentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private float transparency = 0.0f; 
	
	public TransparentPanel(boolean doubelbuffered, float transparency){
		super(doubelbuffered);
		this.transparency = transparency;
	}
	
	@Override
	 public void paint(Graphics g) {
		 Graphics2D g2 = (Graphics2D) g.create();
		 g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,transparency));
		 super.paint(g2);
		 g2.dispose();
	 }


}
