package eu.vdmr.dspgui.component.converter.channel;

import java.awt.Graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.component.connector.Connector;
import eu.vdmr.dspgui.component.connector.ConnectorDirection;
import eu.vdmr.dspgui.component.connector.ConnectorPlacement;
import eu.vdmr.dspgui.component.connector.ConnectorType;
import eu.vdmr.dspgui.util.project.ComponentFiler;
import eu.vdmr.dspgui.util.project.component.converter.channel.MonoToStereoFiler;

public class MonoToStereoGui extends DSPGuiComponentImpl {
	private static Logger LOG = LogManager.getLogger(MonoToStereoGui.class);

	
	public MonoToStereoGui() {
		LOG.debug("MonoToStereoGUI inits");
		// do not need initialization...
		setInitialized(true);
		loadImage("splitter_icon.png");
		LOG.debug("creating connectors");
		connectors = new Connector[2];
		Connector in = new Connector(ConnectorType.MONO, ConnectorPlacement.WEST, ConnectorDirection.PULLING, this);
		connectors[0] = in;
		Connector out = new Connector(ConnectorType.STEREO, ConnectorPlacement.EAST, ConnectorDirection.EMITTING, this);
		connectors[1] = out;
	}

	public void paint(final Graphics g){
		super.paintDefault(g);
	}
	
	@Override
	public void paintName(Graphics g){
		g.setColor(C_TEXT);
		g.drawString("MS", pos.x, pos.y + dim.height);
	}
	

	public ComponentFiler getFiler() {
		return new MonoToStereoFiler();
	}


}
