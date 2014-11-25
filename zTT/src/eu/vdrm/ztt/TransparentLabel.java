package eu.vdrm.ztt;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class TransparentLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	private float transparency = 0.0f; 
	
	public TransparentLabel(String text, float transparency){
		super(text);
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

