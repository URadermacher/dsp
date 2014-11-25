package eu.vdrm.dspgui.listeners.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import eu.vdrm.dsp.util.audio.AbstractAudioSink;
import eu.vdrm.dsp.util.data.ExecutionResult;
import eu.vdrm.dspgui.app.DSPPanel;
import eu.vdrm.dspgui.component.DSPGuiComponent;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.listeners.state.StateChangeListener;
import eu.vdrm.dspgui.util.ApplicationState;
import eu.vdrm.dspgui.util.Globals;

public class MenuRunActionListener implements ActionListener, StateChangeListener {
	private static Logger LOG = Logger.getLogger(MenuRunActionListener.class);
	
	private JMenuItem mySelf;
	
	public MenuRunActionListener(JMenuItem startMenuItem){
		mySelf = startMenuItem;
	}

	@SuppressWarnings("unchecked") // we know a SINK must implement Callable
	public void actionPerformed(ActionEvent arg0) {
		LOG.debug("MenuRunAction action");
		DSPPanel panel = Globals.getInstance().getPanel();
		MouseListener[] oldL =  panel.getMouseListeners();
		for (MouseListener curr : oldL){
			panel.removeMouseListener(curr);
		}
		MouseMotionListener[] oldM =  panel.getMouseMotionListeners();
		for (MouseMotionListener curr : oldM){
			panel.removeMouseMotionListener(curr);
		}
		
		ApplicationState oldState = Globals.getInstance().getState();
		Globals.getInstance().setState(ApplicationState.RUN);
		panel.repaint();
		List<Callable<ExecutionResult>> threads = new ArrayList<Callable<ExecutionResult>>();
		List<DSPGuiComponent> errorComps = new ArrayList<DSPGuiComponent>();
		List<DSPGuiComponent> guiComps =  Globals.getInstance().getGuiComponents();
		for (DSPGuiComponent guiComp : guiComps){
		    if (! ((DSPGuiComponentImpl)guiComp).isInitialized()) {
		    	errorComps.add(guiComp);
		    }
			if (guiComp.getImpl() instanceof AbstractAudioSink) {
//				AbstractAudio aa = (AbstractAudio)guiComp.getImpl();
//				if (aa.getType() == DeviceType.SINK){
					// each SINK implements callable-
					threads.add((Callable<ExecutionResult>)guiComp.getImpl());
//				}
			}
		}
		// check some..
		boolean error = false;
		if (threads.size() == 0){  // no sink in project
			JOptionPane.showMessageDialog(null, "No sink in project, cannot run..", "Error", JOptionPane.ERROR_MESSAGE);
			error = true;
		}
		if (errorComps.size() > 0){  // at least one component not intitialized
			String errCmpNames = "";
			for (DSPGuiComponent errcmp : errorComps){
				errCmpNames += "\n " + errcmp.getClass().getName() ;
			}
			JOptionPane.showMessageDialog(null, "Not all components are initialised, cannot run..:" + errCmpNames, "Error", JOptionPane.ERROR_MESSAGE);
			error = true;
		}
		if (error){
			threads = null; // release stuff
			// reset old mouse(motion)listeners
			for (MouseListener curr : oldL){
				panel.addMouseListener(curr);
			}
			for (MouseMotionListener curr : oldM){
				panel.addMouseMotionListener(curr);
			}
			Globals.getInstance().setState(oldState);
			panel.repaint();
			return;
		}
		
		List<Future<ExecutionResult>> results = new ArrayList<Future<ExecutionResult>>(threads.size());
		ExecutorService service = Executors.newFixedThreadPool(threads.size()); 
		for (Callable<ExecutionResult> thread : threads) {
			Future<ExecutionResult> result = service.submit(thread);
			results.add(result);
		}
		LOG.debug("All invoked, size of futures = " + results.size());
		if (results != null){
			Future<ExecutionResult> res = results.get(0);
			Globals.getInstance().setRunningThreads(results);
			Globals.getInstance().setRunningSinks(threads);
			LOG.debug("result done? " + res.isDone());
			if (res.isDone()){
				try {
					LOG.debug("result  = " + res.get());
				} catch (InterruptedException e) {
					LOG.error("InterruptedException " + e,e);
				} catch (ExecutionException e) {
					LOG.error("ExecutionException " + e,e);
				}
			}
		}
		Globals.getInstance().setState(ApplicationState.RUN);
	}
	
	
	public void stateChanged(ApplicationState oldState,	ApplicationState newState) {
		if (ApplicationState.RUN.equals(newState)){
			mySelf.setEnabled(false);
		} else {
			mySelf.setEnabled(true);
		}
	}


}
