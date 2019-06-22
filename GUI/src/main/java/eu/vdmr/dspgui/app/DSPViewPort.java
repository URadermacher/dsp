package eu.vdmr.dspgui.app;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JViewport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.util.ApplicationState;
import eu.vdmr.dspgui.util.Globals;

public class DSPViewPort extends JViewport {
	private static Logger LOG = LogManager.getLogger(DSPViewPort.class);

	private static final long serialVersionUID = 1L;

	@Override
	public void paintChildren(Graphics g) {
        // Invoke the superclass behavior first
        // to paint the children.
        super.paintChildren(g);
        //paint state indication
        LOG.debug("painting viewport");
        ApplicationState state = Globals.getInstance().getState();
        switch (state) {
        case EDIT :
            g.setColor(Color.LIGHT_GRAY);
        	break;
        case SEL :
            g.setColor(Color.CYAN);
        	break;
        case RUN:
        	g.setColor(Color.RED);
        }
        g.fillRect(0, 0, 60, 20);
        g.setColor(Color.BLACK);
        g.drawString(state.toString(), 2, 12);
    }
}
