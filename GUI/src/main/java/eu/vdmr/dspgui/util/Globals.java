package eu.vdmr.dspgui.util;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dsp.util.data.ExecutionResult;
import eu.vdmr.dspgui.app.DSPGui;
import eu.vdmr.dspgui.app.DSPPanel;
import eu.vdmr.dspgui.component.DSPGuiComponent;
import eu.vdmr.dspgui.listeners.state.StateChangeListener;
import eu.vdmr.dspgui.util.xmlentities.Entry;

public class Globals {
	private static Logger LOG = LogManager.getLogger(Globals.class);

	
	private static Globals instance = new Globals();
	
	private Map<String,Entry>classMap;
	private ApplicationState state = null;
	private List<DSPGuiComponent> guiComponents = new ArrayList<DSPGuiComponent>(); 
	private DSPPanel panel;
	private DSPGui masterFrame;
	private List<Future<ExecutionResult>> runningThreads;
	// the last pulling objects of each thread
	private List<Callable<ExecutionResult>> sinks;
	private ImageUtil imageUtil;
	private List<StateChangeListener> stateChangeListeners = new ArrayList<StateChangeListener>();
	
	
	private Globals(){
		classMap = new HashMap<String,Entry>();
		imageUtil = new ImageUtil();
	}

	public static Globals getInstance(){
		return instance;
	}
	
	public void addStateChangeListener(StateChangeListener listener){
		stateChangeListeners.add(listener);
	}
	
	public boolean removeStateChangeListener(StateChangeListener listener){
		return stateChangeListeners.remove(listener);
	}
	
	public Map<String,Entry> getClassMap(){
		return classMap;
	}

	public ApplicationState getState() {
		return state;
	}

	public void setState(ApplicationState state) {
		if (this.state != null && this.state.equals(state)){
			return;
		}
		ApplicationState old = this.state;
		this.state = state;
		for (StateChangeListener listener : stateChangeListeners){
			listener.stateChanged(old, state);
		}
		getPanel().repaint();
	}

	public List<DSPGuiComponent> getGuiComponents() {
		return guiComponents;
	}
	
	public void addGuiComponent(DSPGuiComponent comp){
		guiComponents.add(comp);
	}

	public void freeGuiComponents() {
		guiComponents = new ArrayList<DSPGuiComponent>();
		recalcPanelSize();
	}
	
	public void setPanel(DSPPanel panel){
		this.panel = panel; 
	}
	
	public DSPPanel getPanel(){
		return panel;
	}

	public DSPGui getMasterFrame() {
		return masterFrame;
	}

	public void setMasterFrame(DSPGui masterFrame) {
		this.masterFrame = masterFrame;
	}

	public List<Future<ExecutionResult>> getRunningThreads() {
		return runningThreads;
	}

	public void setRunningThreads(List<Future<ExecutionResult>> runningThreads) {
		this.runningThreads = runningThreads;
	}
	
	public Image getImageFromFile(String filename){
		return imageUtil.loadImage(filename);
	}
	
	public ImageIcon getImageIconFromFile(String filename){
		return imageUtil.loadImageIcon(filename);
	}
	
	public void setRunningSinks(List<Callable<ExecutionResult>> sinks){
		this.sinks = sinks;
	}
	
	public List<Callable<ExecutionResult>> getSinks(){
		return sinks;
	}

	/**
	 * as the size of the painted area may change (i.e. components may be dragged outside the current panel size,
	 * we have to recalculate the size after dragging (also after clearAll()). We also have to trigger the ScrollPane
	 * to recalculate the bars.
	 */
	public void recalcPanelSize(){
		// new size
		if (guiComponents.size() == 0){
			LOG.debug("cleared");
			panel.setPreferredSize(DSPGui.DEFAULT_DIMENSION);
		} else {
			int maxX = Integer.MIN_VALUE;
			int maxY = Integer.MIN_VALUE;
			for (DSPGuiComponent component : guiComponents){
				if (component.getPoint().x > maxX){
					maxX = component.getPoint().x;
				}
				if (component.getPoint().y > maxY){
					maxY = component.getPoint().y;
				}
			}
			Dimension newDim = new Dimension(maxX + (4*DSPGui.MARGIN), maxY + (4*DSPGui.MARGIN));
			if (LOG.isDebugEnabled()){
				LOG.debug("calculated size = "  + maxX + " - "  + maxY);
				LOG.debug("newDim = " + newDim.width + "/" + newDim.height);
			}
			panel.setPreferredSize(newDim);
		}
		panel.revalidate();
		panel.repaint();
	}
}
