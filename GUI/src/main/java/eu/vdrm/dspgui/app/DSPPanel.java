package eu.vdrm.dspgui.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.Iterator;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import eu.vdrm.dspgui.component.DSPGuiComponent;
import eu.vdrm.dspgui.util.Globals;

/**
 * Main drawing area
 */
public class DSPPanel extends JPanel{
			private static Logger LOG = Logger.getLogger(DSPPanel.class);

			private static final long serialVersionUID = 1L;
			private static final int MAX = 8;
		    private static final int SIZE = 480;
//		    private static final Color light = new Color(0x40C040);
//		    private static final Color dark  = new Color(0x408040);
		    private static final Color light = new Color(0xfef556);
		    private static final Color dark  = new Color(0xdcfe56);
		    private Rectangle selectRectangle;
			
			public DSPPanel(String name){
				super(true);
				this.setLayout(new GridLayout(MAX, MAX, MAX, MAX));
		        this.setPreferredSize(new Dimension(SIZE, SIZE));
		        Globals.getInstance().setPanel(this);
		 	}
			
			 @Override
		     public void paintComponent(final Graphics g) {
				 LOG.debug("paint on DSPPAnel");
//				 // paint background
//		         int w = this.getWidth()/MAX;
//		         int h = this.getHeight()/MAX;
//		         for (int row = 0; row < MAX; row++) {
//		             for (int col = 0; col < MAX; col++) {
//		                 g.setColor((row + col) % 2 == 0 ? light : dark);
//		                 g.fillRect(col * w, row * h, w, h);
//		             }
//		         }
		         // paint components
		         Iterator<DSPGuiComponent> iter = Globals.getInstance().getGuiComponents().iterator();
		         while (iter.hasNext()){
		        	 iter.next().paint(g);
		         }
//		         // paint connections (will set stroke to 3)
//		         Iterator<RagaDSPGUIComponent> iter2 = Globals.getInstance().getGuiComponents().iterator();
//		         while (iter2.hasNext()){
//		        	 iter2.next().paintConnections(g);
//		         }
//		         // evtl paint select rectangle
//		         if (selectRectangle != null){
//		        	 LOG.debug("painting select rectangle");
//				     ((Graphics2D)g).setStroke(new BasicStroke(1F));
//		        	 g.setColor(Color.WHITE);
//		        	 g.setXORMode(Color.BLACK);
//		        	 g.drawRect(selectRectangle.x, selectRectangle.y, selectRectangle.width, selectRectangle.height);
//		        	 g.setPaintMode();
//		         }
		     }
			 
			 public void suggestPaint(){
				 this.invalidate();
			 }

			public Rectangle getSelectRectangle() {
				return selectRectangle;
			}

			public void setSelectRectangle(Rectangle selectRectangle) {
				this.selectRectangle = selectRectangle;
			}
}
