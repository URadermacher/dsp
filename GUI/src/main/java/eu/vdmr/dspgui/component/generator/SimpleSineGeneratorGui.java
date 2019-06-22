package eu.vdmr.dspgui.component.generator;

import java.awt.Graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dsp.component.generator.SimpleSineGenerator;
import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.component.connector.Connector;
import eu.vdmr.dspgui.component.connector.ConnectorDirection;
import eu.vdmr.dspgui.component.connector.ConnectorPlacement;
import eu.vdmr.dspgui.component.connector.ConnectorType;
import eu.vdmr.dspgui.popups.GeneralTextPopUp;
import eu.vdmr.dspgui.util.Globals;
import eu.vdmr.dspgui.util.project.ComponentFiler;
import eu.vdmr.dspgui.util.project.component.generator.SimpleSineGeneratorFiler;
import eu.vdmr.util.string.StrUtil;

public class SimpleSineGeneratorGui extends DSPGuiComponentImpl {
	private static Logger LOG = LogManager.getLogger(SimpleSineGeneratorGui.class);
	private GeneralTextPopUp createPopUp;
	
	
	public SimpleSineGeneratorGui() {
		LOG.debug("SimpleSineGenerator inits");
		setInitialized(false);
		loadImage("SSineGen_icon.png");
		LOG.debug("creating connectors");
		connectors = new Connector[1];
		Connector out = new Connector(ConnectorType.MONO, ConnectorPlacement.EAST, ConnectorDirection.EMITTING, this);
		connectors[0] = out;
	}
	
	public void paint(final Graphics g){
		super.paintDefault(g);
	}
	
	@Override
	public void paintName(Graphics g){
		g.setColor(C_TEXT);
		g.drawString("SG", pos.x, pos.y + dim.height);
	}
	@Override
	public void showPopUp(){
		if (!initialized){
			showCreatePopUp();
		}
	}
	
	private void showCreatePopUp(){
		if (createPopUp == null){
			createPopUp = new GeneralTextPopUp(Globals.getInstance().getMasterFrame(), "SimpleSineGenerator", new String[] {"Frequencies"});
			if (getImpl() != null){
				if (((SimpleSineGenerator)getImpl()).getFrequencies() != null){
					createPopUp.setText(0,StrUtil.IntArr2String(((SimpleSineGenerator)getImpl()).getFrequencies()));
				}
			}
		}
		createPopUp.setVisible(true);
		LOG.debug("back from dialog");
		String res = createPopUp.getResultbutton();
		if ("OK".equals(res)){
			LOG.debug("OK pressed");
			((SimpleSineGenerator)impl).setFrequencies(StrUtil.String2IntArr(createPopUp.getValue(0)));
			setInitialized(true);
			// show new color:
			Globals.getInstance().getPanel().repaint();
		}
	}
	
	
	public ComponentFiler getFiler() {
		return new SimpleSineGeneratorFiler();
	}
}	
