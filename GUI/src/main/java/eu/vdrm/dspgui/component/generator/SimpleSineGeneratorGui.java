package eu.vdrm.dspgui.component.generator;

import java.awt.Graphics;

import org.apache.log4j.Logger;

import eu.vdrm.dsp.component.generator.SimpleSineGenerator;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.component.connector.Connector;
import eu.vdrm.dspgui.component.connector.ConnectorDirection;
import eu.vdrm.dspgui.component.connector.ConnectorPlacement;
import eu.vdrm.dspgui.component.connector.ConnectorType;
import eu.vdrm.dspgui.popups.GeneralTextPopUp;
import eu.vdrm.dspgui.util.Globals;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.component.generator.SimpleSineGeneratorFiler;
import eu.vdrm.util.string.StrUtil;

public class SimpleSineGeneratorGui extends DSPGuiComponentImpl {
	private static Logger LOG = Logger.getLogger(SimpleSineGeneratorGui.class);
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
