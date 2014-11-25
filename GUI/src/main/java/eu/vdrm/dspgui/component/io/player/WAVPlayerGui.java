package eu.vdrm.dspgui.component.io.player;

import java.awt.Graphics;

import org.apache.log4j.Logger;

import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.component.connector.Connector;
import eu.vdrm.dspgui.component.connector.ConnectorDirection;
import eu.vdrm.dspgui.component.connector.ConnectorPlacement;
import eu.vdrm.dspgui.component.connector.ConnectorType;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.component.io.player.WAVPlayerFiler;

public class WAVPlayerGui extends DSPGuiComponentImpl {
	private static Logger LOG = Logger.getLogger(WAVPlayerGui.class);
	
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
