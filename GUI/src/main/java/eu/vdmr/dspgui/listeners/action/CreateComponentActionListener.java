package eu.vdmr.dspgui.listeners.action;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dsp.component.DSPComponentImpl;
import eu.vdmr.dspgui.app.DSPGui;
import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.util.Globals;
import eu.vdmr.util.clazz.ObjectInitialiser;

public class CreateComponentActionListener implements ActionListener {
	private static Logger LOG = LogManager.getLogger(CreateComponentActionListener.class);

	private String componentName;
	
	public CreateComponentActionListener(String cName){
		componentName = cName;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		LOG.debug("Component creation");
		// create an instance of the GUI class
		String clname = Globals.getInstance().getClassMap().get(componentName).getClassName();
		DSPGuiComponentImpl guiImpl = (DSPGuiComponentImpl)ObjectInitialiser.getObject(clname);
		guiImpl.setPoint(new Point(DSPGui.MARGIN,DSPGui.MARGIN));
		guiImpl.setDimension(new Dimension(DSPGui.COMPONENT_DIMENSION));
		Globals.getInstance().addGuiComponent(guiImpl);
		Globals.getInstance().getPanel().repaint();
		
		// now create the implementation class
		String implname = Globals.getInstance().getClassMap().get(componentName).getImplName();
		DSPComponentImpl compImpl = (DSPComponentImpl)ObjectInitialiser.getObject(implname);
		guiImpl.setImpl(compImpl);
	}

}
