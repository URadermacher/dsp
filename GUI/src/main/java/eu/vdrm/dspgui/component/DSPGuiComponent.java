package eu.vdrm.dspgui.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import eu.vdrm.dsp.component.DSPComponent;
import eu.vdrm.dspgui.component.connector.Connector;
import eu.vdrm.dspgui.util.project.ComponentFiler;



public interface DSPGuiComponent {
	
	/**
	 * call to paint itself
	 * @param g
	 */
	public void paint(Graphics g);


	/**
	 * check whether the given point falls within the boundaries of this component
	 * @param p
	 * @return true if the point is within the boundaries of this component
	 */
	public boolean contains(Point p);

	/**
	 * check whether the given point is over a connector
	 * @param x
	 * @param y
	 * @return the Connector, iff found or null if the x/y are not within a connector
	 */
	public Connector onConnector(int x, int y);
	
	
	/**
	 * deselect all connectors
	 */
	public void deselectCons();
	
	/**
	 * 
	 * @return the connectors of this component..
	 */
	public Connector[] getConnectors();
	
	/**
	 * the the x/y position of the component 
	 * @param p he new left top point
	 */
	public void setPoint(Point p);
	
	
	/**
	 * get the position
	 */
	public Point getPoint();
	
	/**
	 * set the size of the component
	 * @param d
	 */
	public void setDimension(Dimension d);
	
	/**
	 * do whatever has to be done when the mouse leaves the component's area
	 */
	public void mouseExit();
	
	/**
	 * do whatever has to be done when the mouse enters the component's area
	 */
	public void mouseEnter();
	
	/**
	 * the right mouse button was pressed, the component will probably display a PopUp-menu or dialog.. 
	 */
	public void showPopUp();
	
	/**
	 * connection to the implementation class, i.e. the class whose GUI this component is.
	 * @param impl
	 */
	public void setImpl(DSPComponent impl);
	

	/**
	 * @return for the given connector the Point where (approximately) the middle point of the  connector is
	 */
	public Point getMyMidPoint(Connector connector);
	
	/**
	 * whether the middle point of the component falls into the parameter rectangle
	 */
	public boolean isInRect(Rectangle rect);
	
	/**
	 * paint connections (only for emitting connectors)
	 */
	public void paintConnections(Graphics g);
	

	/**
	 * 
	 * @return the implementation component
	 */
	public DSPComponent getImpl();
	
	/**
	 * @return the component used to write/parse xml 
	 */
	public ComponentFiler getFiler();
	
	/**
	 * each gui component has an unique id (e.g. uuid)
	 * @return an unique id (used for connectors..)
	 */
	public String getId();
	
	/**
	 * to be used by XML reader
	 * @param newConnectors
	 */
	public void replaceConnectors(Connector[] newConnectors);
}
