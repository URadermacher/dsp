package eu.vdmr.dspgui.listeners.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dsp.util.audio.AbstractAudioSink;
import eu.vdmr.dsp.util.data.ExecutionResult;
import eu.vdmr.dspgui.listeners.state.StateChangeListener;
import eu.vdmr.dspgui.util.ApplicationState;
import eu.vdmr.dspgui.util.Globals;

public class MenuStopActionListener implements ActionListener, StateChangeListener {
	private static Logger LOG = LogManager.getLogger(MenuStopActionListener.class);
	private JMenuItem mySelf;
	
	public MenuStopActionListener(JMenuItem stopMenuItem){
		mySelf = stopMenuItem;
	}

	public void actionPerformed(ActionEvent arg0) {
		LOG.debug("MenuStopAction action");
		List<Callable<ExecutionResult>> runningThreads = Globals.getInstance().getSinks();
		if (runningThreads == null){
			JOptionPane.showMessageDialog(Globals.getInstance().getMasterFrame(), "Nothing to stop");
			return;
		}
		for (Callable<ExecutionResult>runningThread : runningThreads) {
			// only AudioSinks can be started...
			AbstractAudioSink sink =  (AbstractAudioSink)runningThread;
			try {
				sink.stop();
			} catch (Exception e) {
				LOG.error("Exception stopping " + sink.getName() + ": " + e,e);
				JOptionPane.showMessageDialog(Globals.getInstance().getMasterFrame(), "Exception stopping " + sink.getName() + ": " + e + ". See logging for trace");
			}
			LOG.debug("sink stop called");
		}
		Globals.getInstance().setRunningThreads(null);
		LOG.debug("MenuStopAction finished");
		Globals.getInstance().setState(ApplicationState.EDIT);
	}
	
	public void stateChanged(ApplicationState oldState,	ApplicationState newState) {
		if (ApplicationState.RUN.equals(newState)){ // only available in running state..
			mySelf.setEnabled(true);
		} else {
			mySelf.setEnabled(false);
		}
	}

}
