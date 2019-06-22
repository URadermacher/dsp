package eu.vdmr.dspgui.component.io.player;

import java.awt.Graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.component.connector.Connector;
import eu.vdmr.dspgui.component.connector.ConnectorDirection;
import eu.vdmr.dspgui.component.connector.ConnectorPlacement;
import eu.vdmr.dspgui.component.connector.ConnectorType;
import eu.vdmr.dspgui.util.project.ComponentFiler;
import eu.vdmr.dspgui.util.project.component.io.player.WAVPlayerFiler;

public class WAVPlayerGui extends DSPGuiComponentImpl {
	private static Logger LOG = LogManager.getLogger(WAVPlayerGui.class);
	
	public WAVPlayerGui() {
		LOG.debug("WAVplayer inits");
		setInitialized(true);
		// TODO make transparent
		loadImage("speaker_icon.png");
		LOG.debug("creating connectors");
		connectors = new Connector[1];
		Connector in = new Connector(ConnectorType.STEREO, ConnectorPlacement.WEST, ConnectorDirection.PULLING, this);
		connectors[0] = in;
	}

	@Override
	public void paint(final Graphics g) {
		super.paintDefault(g);
	}
	
	@Override
	/**
	 * called from paintDefault 
	 */
	public void paintName(Graphics g){
		g.setColor(C_TEXT);
		g.drawString("WP", pos.x, pos.y + dim.height);
	}

	public ComponentFiler getFiler() {
		return new WAVPlayerFiler();
	}

}
