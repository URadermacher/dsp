package eu.vdrm.dspgui.listeners.mouse;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import eu.vdrm.dspgui.component.DSPGuiComponent;
import eu.vdrm.dspgui.component.connector.Connector;
import eu.vdrm.dspgui.component.connector.ConnectorDirection;
import eu.vdrm.dspgui.util.Globals;

public class EditMouseListener extends DSPGuiMouseListener {
	private static Logger LOG = Logger.getLogger(EditMouseListener.class);	
	private Point lastPos;
	private boolean connecting = false;
	Connector con; // the connector we are busy to connect 
	
	//MouseLister methods

	public void mouseClicked(MouseEvent e){
		if (LOG.isDebugEnabled()){LOG.debug("mouseClicked called " + e.getX() + "-" + e.getY());}
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (getCurrentC() != null){
				getCurrentC().showPopUp();
			}
		}
	}
	
	public void mouseEntered(MouseEvent e){
		if (LOG.isDebugEnabled()){LOG.debug("mouseEntered called " + e.getX() + "-" + e.getY());}
	} 
	public void mouseExited(MouseEvent e){
		if (LOG.isDebugEnabled()){LOG.debug("mouseExited called " + e.getX() + "-" + e.getY());}
	} 
	
	
	@SuppressWarnings("deprecation")
	public void mousePressed(MouseEvent e){
		if (LOG.isDebugEnabled()){LOG.debug("mousePressed called " + e.getX() + "-" + e.getY()+ ", button = " + e.getButton());}
		// if the left button is pressed we keep the current position of the mouse to either
		// 	a) when the component is dragged we can calculate the amount of movement in the next dragged() call
		//  b) if it was pressed above a connector we will draw a line from the selected connector to the current point..
		if (e.getButton() == MouseEvent.BUTTON1) {
			lastPos = e.getPoint();
			// check whether we are over a connector
			LOG.debug("Checking connectors");
			if (getCurrentC() != null){
				con = getCurrentC().onConnector(e.getX(), e.getY()); 
				if ( con != null) {
					if (  con.getDirection() == ConnectorDirection.EMITTING){
						con.setSelected(true);
						Globals.getInstance().getMasterFrame().setCursor(Cursor.HAND_CURSOR);
						connecting = true;
					} else {
						//error message: start from emitting side!
						 JOptionPane.showMessageDialog(null, "Start connect from emmitting/pushing side!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {  // deselect evtl. earlier selected connectors
					getCurrentC().deselectCons();
					Globals.getInstance().getMasterFrame().setCursor(Cursor.DEFAULT_CURSOR );
					connecting = false;
				}
			}
		}
	} 
	@SuppressWarnings("deprecation")
	public void mouseReleased(MouseEvent e){
		if (LOG.isDebugEnabled()){LOG.debug("mouseReleased called " + e.getX() + "-" + e.getY());}
		lastPos = null;
		if (connecting){
			DSPGuiComponent targetC = isUnder(e);
			if (targetC != null ){ // mouse release above other component..
				Connector targetCon = targetC.onConnector(e.getX(), e.getY());
				if (targetCon != null){  // release above other connector: connect iff possible.
					String error  = targetCon.getRejectReason(con); 
					if ( error == null) {
						con.addOther(targetCon);
						targetCon.addOther(con);
						Globals.getInstance().getPanel().repaint();
					} else { 
						JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE); 
						
					}
					// anyway stop connecting action
					con.setSelected(false);
					lastPos = null;
					Globals.getInstance().getMasterFrame().setCursor(Cursor.DEFAULT_CURSOR );
					connecting = false;
				}
				
			} else {  // just stopped connect action
				con.setSelected(false);
				lastPos = null;
				Globals.getInstance().getMasterFrame().setCursor(Cursor.DEFAULT_CURSOR );
				connecting = false;
			}
		} else {
			LOG.debug("release without connect");
			// we moved a component. May be we have to extend the ViewPort size and tell the ScrollPane to adjust..
			Globals.getInstance().recalcPanelSize(); 
			
		}
	} 
	public void mouseWheelMoved(MouseEvent e){
		
	}
	
	// MouseMotionLister methods
	
	/**
	 * move a component iff there is a current component AND the lastPos is set (i.e. button 1 was used to pressed)
	 * (and we do not allow a component to get negative coordinates..)
	 */
	public void mouseDragged(MouseEvent e){
		if (LOG.isDebugEnabled()){LOG.debug("mouseDragged called " + e.getX() + "-" + e.getY() + ", button = " + e.getButton());}
		DSPGuiComponent c = getCurrentC();
		if (c != null && lastPos != null && ( ! connecting)){
			// we cannot drag left out or top out the image, we may drag right and down...
			int newX = c.getPoint().x  + e.getX() - lastPos.x;
			int newY = c.getPoint().y  + e.getY() - lastPos.y;
			if ((newX) < 0){
				newX = 0;
			}
			if ((newY) < 0){
				newY = 0;
			}
			c.setPoint(new Point(newX, newY));
			lastPos = e.getPoint();
			Globals.getInstance().getPanel().repaint();
		} else if (connecting){
			con.setEndPoint(e.getX(), e.getY());
			Globals.getInstance().getPanel().repaint();
		}
	}  

	
	public void mouseMoved(MouseEvent e){
		//if (LOG.isDebugEnabled()){LOG.debug("mouseMoved called " + e.getX() + "-" + e.getY());}
		DSPGuiComponent c = isUnder(e);
		if (c != null) {
			if (getCurrentC() != null){
				if (LOG.isDebugEnabled()){LOG.debug("found "+ getCurrentC().getClass().getName());}
				if (getCurrentC().equals(c)){
					//nop: moving within component
				} else { // left old, entered new
					getCurrentC().mouseExit();
					c.mouseEnter();
					setCurrentC(c);
				}
			} else { // entering component
				c.mouseEnter();
				setCurrentC(c);
			}
		} else { //moving around between components
			if (getCurrentC() != null){ // just left one
				if (LOG.isDebugEnabled()){LOG.debug("leaving "+ getCurrentC().getClass().getName());}
				getCurrentC().mouseExit();
				setCurrentC(null);
			} // else nothing
		}
	} 
}
