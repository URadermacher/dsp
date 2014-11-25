package eu.vdrm.dspgui.component.analyzer.counter;

import java.awt.Graphics;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import eu.vdrm.dsp.component.analyzer.counter.ZeroCrossingCounter;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.component.connector.Connector;
import eu.vdrm.dspgui.component.connector.ConnectorDirection;
import eu.vdrm.dspgui.component.connector.ConnectorPlacement;
import eu.vdrm.dspgui.component.connector.ConnectorType;
import eu.vdrm.dspgui.popups.GeneralTextPopUp;
import eu.vdrm.dspgui.util.Globals;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.component.analyzer.ZeroCrossingCounterFiler;

public class ZeroCrossingCounterGui extends DSPGuiComponentImpl {
	private static Logger LOG = Logger.getLogger(ZeroCrossingCounterGui.class);
	private GeneralTextPopUp createPopUp;

	
	public ZeroCrossingCounterGui() {
		LOG.debug("ZeroCrossingCounterGui inits");
		// do need initialization...
		setInitialized(false);
		loadImage("zero_counter_icon.png");
		LOG.debug("creating connectors");
		connectors = new Connector[2];
		Connector in = new Connector(ConnectorType.MONO, ConnectorPlacement.WEST, ConnectorDirection.PULLING, this);
		connectors[0] = in;
		Connector out = new Connector(ConnectorType.MONO, ConnectorPlacement.EAST, ConnectorDirection.EMITTING, this);
		connectors[1] = out;
	}
	
	public ComponentFiler getFiler() {
		return new ZeroCrossingCounterFiler();
	}

	@Override
	public void paint(Graphics g) {
		super.paintDefault(g);
	}

	@Override
	public void paintName(Graphics g){
		g.setColor(C_TEXT);
		g.drawString("ZC", pos.x, pos.y + dim.height);
	}
	
	@Override
	public void showPopUp(){
		if (!initialized){
			showCreatePopUp();
		}
	}
	
	
	private void showCreatePopUp(){
		if (createPopUp == null){
			createPopUp = new GeneralTextPopUp(Globals.getInstance().getMasterFrame(), "ZeroCrossingCounter", new String[] {"milliseconds to accumulate","File to log in"});
			if (getImpl() != null){
				int setInt = ((ZeroCrossingCounter)getImpl()).getInterval() ;
				if (setInt != 0){
					createPopUp.setText(0,Integer.toString(setInt));
				}
			}
		}
		createPopUp.setVisible(true);
		LOG.debug("back from dialog");
		String res = createPopUp.getResultbutton();
		if ("OK".equals(res)){
			LOG.debug("OK pressed");
			((ZeroCrossingCounter)impl).setInterval(Integer.parseInt(createPopUp.getValue(0)));
			try {
				((ZeroCrossingCounter)impl).SetFileName(createPopUp.getValue(1));
			} catch (Exception e) {
				LOG.error("Exception setting filename " + createPopUp.getValue(1) + ": " + e,e);
				JOptionPane.showMessageDialog(Globals.getInstance().getMasterFrame(), "Exception setting filename " + createPopUp.getValue(1) + ": " + e + ". See logging for trace");
			}
			setInitialized(true);
			// show new color:
			Globals.getInstance().getPanel().repaint();
		}
	}
	
	

}
