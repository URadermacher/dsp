package eu.vdmr.dspgui.component.filter;

import java.awt.Graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.component.connector.Connector;
import eu.vdmr.dspgui.component.connector.ConnectorDirection;
import eu.vdmr.dspgui.component.connector.ConnectorPlacement;
import eu.vdmr.dspgui.component.connector.ConnectorType;
import eu.vdmr.dspgui.util.project.ComponentFiler;
import eu.vdmr.dspgui.util.project.component.filter.DecracklerFiler;

public class DecracklerGui extends DSPGuiComponentImpl {
	private static Logger LOG = LogManager.getLogger(DecracklerGui.class);

	public DecracklerGui() {
		LOG.debug("Decrackler inits");
		setInitialized(false);
		loadImage("decrack_icon.png");
		LOG.debug("creating connectors");
		connectors = new Connector[2];
		Connector in = new Connector(ConnectorType.STEREO, ConnectorPlacement.WEST, ConnectorDirection.PULLING, this);
		connectors[0] = in;
		Connector out = new Connector(ConnectorType.DATA, ConnectorPlacement.EAST, ConnectorDirection.EMITTING, this);
		connectors[1] = out;

	}
	
	//TODO make it work with the WavReader
	
	public void paint(final Graphics g){
		super.paintDefault(g);
	}
	
	 @Override
		public void paintName(Graphics g){
			g.setColor(C_TEXT);
			g.drawString("DC", pos.x, pos.y + dim.height);
		}
		

		public ComponentFiler getFiler() {
			return new DecracklerFiler();
		}

}
