package eu.vdrm.dspgui.listeners.mouse;

import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.event.MouseInputAdapter;

import eu.vdrm.dspgui.component.DSPGuiComponent;
import eu.vdrm.dspgui.util.Globals;

public class DSPGuiMouseListener extends MouseInputAdapter {
	
	private DSPGuiComponent currentC = null;
	
	
	protected  DSPGuiComponent isUnder(MouseEvent e){
		Iterator<DSPGuiComponent> iter = Globals.getInstance().getGuiComponents().iterator();
		while (iter.hasNext()){
			DSPGuiComponent comp = iter.next();
			if (comp.contains(e.getPoint())){
				return comp;
			}
		}
		// not found:
		return null;
	}


	public void setCurrentC(DSPGuiComponent currentC) {
		this.currentC = currentC;
	}
	public DSPGuiComponent getCurrentC() {
		return currentC;
	}

}
