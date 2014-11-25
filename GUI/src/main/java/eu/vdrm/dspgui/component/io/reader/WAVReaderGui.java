package eu.vdrm.dspgui.component.io.reader;

import java.awt.Graphics;

import org.apache.log4j.Logger;

import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.component.connector.Connector;
import eu.vdrm.dspgui.component.connector.ConnectorDirection;
import eu.vdrm.dspgui.component.connector.ConnectorPlacement;
import eu.vdrm.dspgui.component.connector.ConnectorType;
import eu.vdrm.dspgui.popups.io.reader.WavReaderCreatePopUp;
import eu.vdrm.dspgui.util.Globals;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.component.io.reader.WAVReaderFiler;

public class WAVReaderGui extends DSPGuiComponentImpl {
	private static Logger LOG = Logger.getLogger(WAVReaderGui.class);
	private WavReaderCreatePopUp createPopUp;

	
	
	public WAVReaderGui() {
		LOG.debug("WAVReader inits");
		setInitialized(false);
		loadImage("disc_icon.png");
		LOG.debug("creating connectors");
		connectors = new Connector[1];
		Connector out = new Connector(ConnectorType.STEREO, ConnectorPlacement.NORTH, ConnectorDirection.EMITTING, this);
		connectors[0] = out;

	}

	@Override
	public void paint(final Graphics g) {
		super.paintDefault(g);
	}
	 @Override
	public void paintName(Graphics g){
		g.setColor(C_TEXT);
		g.drawString("WR", pos.x, pos.y + dim.height);
	}
	
	@Override
	public void showPopUp(){
		if (!initialized){
			showCreatePopUp();
		}
	}

	private void showCreatePopUp(){
		if (createPopUp == null){
			createPopUp = new WavReaderCreatePopUp(Globals.getInstance().getMasterFrame());
		}
		createPopUp.setVisible(true);
		LOG.debug("back from dialog");
		String res = createPopUp.getResultbutton();
		if ("OK".equals(res)){
			LOG.debug("OK pressed");
//			((WAVReader)impl).setFileName(createPopUp.getFileName());
			setInitialized(true);
			// show new color:
			Globals.getInstance().getPanel().repaint();
		}
	}
	

	public ComponentFiler getFiler() {
		return new WAVReaderFiler();
	}
}
