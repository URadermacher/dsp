package eu.vdrm.dspgui.component.filter;

import java.awt.Graphics;

import org.apache.log4j.Logger;

import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.component.connector.Connector;
import eu.vdrm.dspgui.component.connector.ConnectorDirection;
import eu.vdrm.dspgui.component.connector.ConnectorPlacement;
import eu.vdrm.dspgui.component.connector.ConnectorType;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.component.filter.DecracklerFiler;

public class DecracklerGui extends DSPGuiComponentImpl {
	private static Logger LOG = Logger.getLogger(DecracklerGui.class);

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
