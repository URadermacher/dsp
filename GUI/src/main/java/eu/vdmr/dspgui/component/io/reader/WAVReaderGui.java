package eu.vdmr.dspgui.component.io.reader;

import java.awt.Graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.component.connector.Connector;
import eu.vdmr.dspgui.component.connector.ConnectorDirection;
import eu.vdmr.dspgui.component.connector.ConnectorPlacement;
import eu.vdmr.dspgui.component.connector.ConnectorType;
import eu.vdmr.dspgui.popups.io.reader.WavReaderCreatePopUp;
import eu.vdmr.dspgui.util.Globals;
import eu.vdmr.dspgui.util.project.ComponentFiler;
import eu.vdmr.dspgui.util.project.component.io.reader.WAVReaderFiler;

public class WAVReaderGui extends DSPGuiComponentImpl {
	private static Logger LOG = LogManager.getLogger(WAVReaderGui.class);
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
