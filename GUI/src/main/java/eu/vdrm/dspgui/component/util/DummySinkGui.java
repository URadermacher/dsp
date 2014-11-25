package eu.vdrm.dspgui.component.util;

import java.awt.Graphics;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import org.apache.log4j.Logger;

import eu.vdrm.dsp.component.util.DummySink;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.component.connector.Connector;
import eu.vdrm.dspgui.component.connector.ConnectorDirection;
import eu.vdrm.dspgui.component.connector.ConnectorPlacement;
import eu.vdrm.dspgui.component.connector.ConnectorType;
import eu.vdrm.dspgui.component.io.player.WAVPlayerGui;
import eu.vdrm.dspgui.popups.GeneralInfoPopUp;
import eu.vdrm.dspgui.util.Globals;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.component.util.DummySinkFiler;

public class DummySinkGui extends DSPGuiComponentImpl {
	
	private static Logger LOG = Logger.getLogger(WAVPlayerGui.class);
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
