package eu.vdrm.dspgui.component.filter;

import java.awt.Graphics;

import org.apache.log4j.Logger;

import eu.vdrm.dsp.component.filter.average.MovingAverageFilter;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.component.connector.Connector;
import eu.vdrm.dspgui.component.connector.ConnectorDirection;
import eu.vdrm.dspgui.component.connector.ConnectorPlacement;
import eu.vdrm.dspgui.component.connector.ConnectorType;
import eu.vdrm.dspgui.popups.generator.SingleTextCreatePopUp;
import eu.vdrm.dspgui.util.Globals;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.component.filter.MovingAverageFilterFiler;

public class MovingAverageFilterGui extends DSPGuiComponentImpl {
	private static Logger LOG = Logger.getLogger(MovingAverageFilterGui.class);
	private SingleTextCreatePopUp createPopUp;
	
	
	public MovingAverageFilterGui() {
		LOG.debug("MovingAverageFilterGui inits");
		setInitialized(false);
		loadImage("moving_average_filter_icon.jpg");
		LOG.debug("creating connectors");
		connectors = new Connector[2];
		Connector out = new Connector(ConnectorType.MONO, ConnectorPlacement.EAST, ConnectorDirection.EMITTING, this);
		Connector in = new Connector(ConnectorType.MONO, ConnectorPlacement.WEST, ConnectorDirection.PULLING, this);
		connectors[0] = out;
		connectors[1] = in;
	}
	
	public void paint(final Graphics g){
		super.paintDefault(g);
	}
	
	@Override
	public void paintName(Graphics g){
		g.setColor(C_TEXT);
		g.drawString("MAF", pos.x, pos.y + dim.height);
	}
	@Override
	public void showPopUp(){
		if (!initialized){
			showCreatePopUp();
		}
	}
	
	private void showCreatePopUp(){
		if (createPopUp == null){
			createPopUp = new SingleTextCreatePopUp(Globals.getInstance().getMasterFrame(), " MovingAverageFilter", "nr of samples");
			if (getImpl() != null){
				if (((MovingAverageFilter)getImpl()).getPoints() != 0){
					createPopUp.setText(""+((MovingAverageFilter)getImpl()).getPoints());
				}
			}
		}
		createPopUp.setVisible(true);
		LOG.debug("back from dialog");
		String res = createPopUp.getResultbutton();
		if ("OK".equals(res)){
			LOG.debug("OK pressed");
			((MovingAverageFilter)impl).setPoints(Integer.parseInt(createPopUp.getValue()));
			setInitialized(true);
			// show new color:
			Globals.getInstance().getPanel().repaint();
		}
	}
	
	
	public ComponentFiler getFiler() {
		return new MovingAverageFilterFiler();
	}
}	
