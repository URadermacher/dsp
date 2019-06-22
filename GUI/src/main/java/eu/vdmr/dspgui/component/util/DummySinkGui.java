package eu.vdmr.dspgui.component.util;

import java.awt.Graphics;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dsp.component.util.DummySink;
import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.component.connector.Connector;
import eu.vdmr.dspgui.component.connector.ConnectorDirection;
import eu.vdmr.dspgui.component.connector.ConnectorPlacement;
import eu.vdmr.dspgui.component.connector.ConnectorType;
import eu.vdmr.dspgui.component.io.player.WAVPlayerGui;
import eu.vdmr.dspgui.popups.GeneralInfoPopUp;
import eu.vdmr.dspgui.util.Globals;
import eu.vdmr.dspgui.util.project.ComponentFiler;
import eu.vdmr.dspgui.util.project.component.util.DummySinkFiler;

public class DummySinkGui extends DSPGuiComponentImpl {
	
	private static Logger LOG = LogManager.getLogger(WAVPlayerGui.class);
	private GeneralInfoPopUp createPopUp;
	
	public DummySinkGui() {
		LOG.debug("DummySink inits");
		setInitialized(false);
		// TODO make transparent
		loadImage("sink.png");
		LOG.debug("creating connectors");
		connectors = new Connector[1];
		Connector in = new Connector(ConnectorType.MONO, ConnectorPlacement.WEST, ConnectorDirection.PULLING, this);
		connectors[0] = in;
	}
	public ComponentFiler getFiler() {
		return new DummySinkFiler() ;
	}

	@Override
	public void paint(Graphics g) {
		super.paintDefault(g);
	}

	@Override
	public void paintName(Graphics g) {
		g.setColor(C_TEXT);
		g.drawString("Sink", pos.x, pos.y + dim.height);
	}

	@Override
	public void showPopUp(){
		if (!initialized){
			showCreatePopUp();
		}
	}
	
	private void showCreatePopUp(){
		if (createPopUp == null){
			boolean cont = false;
			if (getImpl() != null){
				 cont = ((DummySink)getImpl()).isContinuous();
			}
			JComponent[] compArr = new JComponent[1];
			JCheckBox chkbx = new JCheckBox("Continuous", cont);
			compArr[0] = chkbx;
			createPopUp = new GeneralInfoPopUp(Globals.getInstance().getMasterFrame(), "DummySink", new String[] {"Continuous"}, compArr);
		}
		createPopUp.setVisible(true);
		LOG.debug("back from dialog");
		String res = createPopUp.getResultbutton();
		if ("OK".equals(res)){
			LOG.debug("OK pressed");
			JCheckBox box = (JCheckBox)createPopUp.getValue(0);
			((DummySink)impl).setContinuous(box.isSelected());
			setInitialized(true);
			// show new color:
			Globals.getInstance().getPanel().repaint();
		}
	}
	
}
