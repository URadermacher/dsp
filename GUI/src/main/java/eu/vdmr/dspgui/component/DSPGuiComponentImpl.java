package eu.vdmr.dspgui.component;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dsp.component.DSPComponent;
import eu.vdmr.dspgui.component.connector.Connector;
import eu.vdmr.dspgui.component.connector.ConnectorDirection;
import eu.vdmr.dspgui.component.connector.ConnectorPlacement;
import eu.vdmr.dspgui.util.Globals;
import eu.vdmr.dspgui.util.project.ComponentFiler;

public abstract class DSPGuiComponentImpl implements DSPGuiComponent{
	private static Logger LOG = LogManager.getLogger(DSPGuiComponentImpl.class);
	
	public static final Color C_UNITITIALIZED = Color.RED;
	public static final Color C_INITIALIZED = Color.ORANGE;
//	public static final Color C_RUNNING = Color.GREEN;
	public static final Color C_TEXT = Color.BLACK;
	
	protected Point pos = new Point(0,0);
	protected Dimension dim = new Dimension(0,0);
	protected boolean initialized = false;
	protected boolean underMouse = false;
//	protected Color color;
	protected Image image;
	protected String imageName;
	protected String id;
	
	protected Connector[] connectors;
	
	protected DSPComponent impl;
	protected ComponentFiler componentFiler;
	
	
	public DSPGuiComponentImpl() {
		// this id may be overwritten iff created from a file.
		this.id = UUID.randomUUID().toString();
	}
	
	public abstract void paint(Graphics g);
	
	public void loadImage(String name){
		String iconfile = System.getProperty("user.dir") + "\\images\\" + name;
		try {
			File input = new File(iconfile);
			image = ImageIO.read(input);
			imageName = name;
		} catch (IOException ie) {
			System.out.println("Error for file " + iconfile + ":" + ie.getMessage());
		}
	}

	public void paintDefault(final Graphics g){
		g.setColor(determineColor());
		g.fill3DRect(pos.x, pos.y, dim.width, dim.height, underMouse);
		if (image != null) {
			LOG.debug("painting image..");
			g.drawImage(image, pos.x, pos.y, null);
		}
		Font f = g.getFont();
		g.setFont(new Font(f.getName(),Font.BOLD, f.getSize()));
		paintName(g);
		if (connectors != null){
			paintConnectors(g);
		} else {
			LOG.debug("No connector");
		}
	}
	
	abstract public void paintName(Graphics g);//{
		//default noppes
	//}
	
	public void paintConnectors(Graphics g){
		for (int i = 0; i < connectors.length; i++){
			g.setColor(connectors[i].getColor());
			ConnectorPlacement position = connectors[i].getPosition();
			int x = 0;
			int y = 0;
			switch (position){
			case NORTH:
				x = pos.x + (dim.width / 2) - (connectors[i].getDim().width / 2);
				y = pos.y ;
				g.fillRect(x, y, connectors[i].getDim().width, connectors[i].getDim().height);
				break;
			case EAST:
				x = pos.x + dim.width - connectors[i].getDim().width ;
				y = pos.y + (dim.height / 2) -  (connectors[i].getDim().height / 2);
				g.fillRect(x, y, connectors[i].getDim().width, connectors[i].getDim().height);
				break;
			case SOUTH:
				x = pos.x + (dim.width / 2) - (connectors[i].getDim().width / 2);
				y = pos.y + dim.height - connectors[i].getDim().height ;
				g.fillRect(x, y, connectors[i].getDim().width, connectors[i].getDim().height);
				break;
			case WEST:
				x = pos.x;
				y = pos.y + (dim.height / 2) -  (connectors[i].getDim().height / 2);
				g.fillRect(x, y, connectors[i].getDim().width, connectors[i].getDim().height);
				break;
			default:
				throw new IllegalStateException("Connecor without position found");
			}
			if (LOG.isDebugEnabled()) {LOG.debug("connector painted at " + x + " : " + y);}
			if (connectors[i].isSelected() && connectors[i].getEndX() != 0){  // during connect action: no target yet found..
				g.setColor(Color.BLACK);
				((Graphics2D)g).setStroke(new BasicStroke(1F));
				g.drawLine(x +(connectors[i].getDim().width / 2) , y +(connectors[i].getDim().height / 2), connectors[i].getEndX(), connectors[i].getEndY());
			}
			if (connectors[i].getOthers()!= null 
					&& (  connectors[i].getDirection() == ConnectorDirection.EMITTING)){
				// paint lines to the target connectors
			   g.setColor(Color.BLACK);
			   ((Graphics2D)g).setStroke(new BasicStroke(3F));
				List<Connector> others = connectors[i].getOthers();
				for (Connector target : others){
				   Point targetPoint = target.getMidPoint();
				   g.drawLine(x +(connectors[i].getDim().width / 2) , y +(connectors[i].getDim().height / 2), targetPoint.x, targetPoint.y);
				}
			}
		}
		
	}
	
	
	/** 
	 * we paint all connections from the emitting to a receiving connector. This should cover all..
	 */
	public void paintConnections(Graphics g){
		for (int i = 0; i < connectors.length; i++){
			if (connectors[i].getOthers()!= null && connectors[i].getDirection() == ConnectorDirection.EMITTING){
				// paint lines to the target connectors
			   g.setColor(Color.BLACK);
			   ((Graphics2D)g).setStroke(new BasicStroke(3F));
				List<Connector> others = connectors[i].getOthers();
				for (Connector target : others){
				   Point targetPoint = target.getMidPoint();
				   Point sourcePoint = getMyMidPoint(connectors[i]);
				   g.drawLine(sourcePoint.x , sourcePoint.y , targetPoint.x, targetPoint.y);
				}
			}
		}
	}
	
	public boolean contains(Point p){
		//if (LOG.isDebugEnabled()){LOG.debug("point: " + p.x + "-" + p.y + "  me " + pos.x +"-" + pos.y + "w: " + dim.width +  " h: " + dim.height);}
		if (p.x > pos.x && p.x < (pos.x + dim.width)) {
			if (p.y > pos.y && p.y < (pos.y + dim.height)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * for the given x,y values: is there a connector?
	 * @return true if the x/y values are withing a connector
	 */
	public Connector onConnector(int X, int Y){
		for (int i = 0; i < connectors.length; i++){
			int x = 0;
			int y = 0;
			ConnectorPlacement position = connectors[i].getPosition();
			switch (position){
			case NORTH:
				x = pos.x + (dim.width / 2) - (connectors[i].getDim().width / 2);
				y = pos.y ;
				break;
			case EAST:
				x = pos.x + dim.width - connectors[i].getDim().width ;
				y = pos.y + (dim.height / 2) -  (connectors[i].getDim().height / 2);
				break;
			case SOUTH:
				x = pos.x + (dim.width / 2) - (connectors[i].getDim().width / 2);
				y = pos.y + dim.height - connectors[i].getDim().height ;
				break;
			case WEST:
				x = pos.x;
				y = pos.y + (dim.height / 2) -  (connectors[i].getDim().height / 2);
				break;
			default:
				throw new IllegalStateException("Connecor without position found");
			}
			if (X > x && (X < x + connectors[i].getDim().width)){
				if ( Y > y && (Y < y +  connectors[i].getDim().height)) {
					if (LOG.isDebugEnabled()){
						LOG.debug("X/Y = " + X + "/" + Y);
						LOG.debug("x/y = " + x + "/" + y);
						LOG.debug("conn x/y " + connectors[i].getDim().getWidth() + "/" + connectors[i].getDim().getHeight());
					}
					return connectors[i];
				}
			}
		}
		return null;
	}
	
	public void deselectCons() {
		for (int i = 0; i < connectors.length; i++){
			connectors[i].setSelected(false);
		}
	}

	public void setPoint(Point p){
		pos = p;
	}
	
	public Point getPoint(){
		return pos;
	}
	
	
	public void mouseExit(){
		underMouse = false;
		Globals.getInstance().getPanel().repaint();
	}
	
	public void mouseEnter(){
		underMouse = true;
		Globals.getInstance().getPanel().repaint();
	}

	public void showPopUp(){
		// nop
	}
	
	public void setImpl(DSPComponent impl){
		this.impl = impl;
	}
	public DSPComponent getImpl(){
		return impl;
	}

	/**
	 * @return for the given connector the Point where (approx) the middle point of the connector is
	 */
	public Point getMyMidPoint(Connector connector){
		int x = 0;
		int y = 0;
		ConnectorPlacement position = connector.getPosition();
		switch (position){
		case NORTH:
			x = pos.x + (dim.width / 2);
			y = pos.y + (connector.getDim().height / 2);
			break;
		case EAST:
			x = pos.x + dim.width - (connector.getDim().width / 2) ;
			y = pos.y + (dim.height / 2);
			break;
		case SOUTH:
			x = pos.x + (dim.width / 2);
			y = pos.y + dim.height - (connector.getDim().height / 2) ;
			break;
		case WEST:
			x = pos.x + (connector.getDim().width / 2) ;
			y = pos.y + (dim.height / 2);
			break;
		default:
			throw new IllegalStateException("Connecor without position found");
		}
		return new Point(x,y);
	}

	public boolean isInRect(Rectangle rect){
		Point middle = getMidPoint();
		if (  middle.x > rect.x && middle.x < rect.x + rect.width 
			&& middle.y > rect.y && middle.y < rect.y + rect.height){
			return true;
		}
		return false;
	}
	
	public Point getMidPoint(){
		return new Point(pos.x + dim.width/2,pos.y + dim.height/2);
	}

	public Dimension getDim() {
		return dim;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public Color getColor() {
		return determineColor();
	}
	
	public Color determineColor(){
		if (initialized){
			return C_INITIALIZED;
		} else {
			return C_UNITITIALIZED;
		}
	}

	public String getImageName() {
		return imageName;
	}

	public void setDimension(Dimension dim) {
		this.dim = dim;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * if created from a project file, the id is set from outside
	 * @param id the saved d
	 */
	public void setId(String id) {
		this.id = id;
	}
	public Connector[] getConnectors(){
		return this.connectors;
	}
	
	public void replaceConnectors(Connector[] newConnectors){
		this.connectors = newConnectors;
	}
}
