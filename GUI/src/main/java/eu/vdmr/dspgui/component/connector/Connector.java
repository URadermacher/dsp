package eu.vdmr.dspgui.component.connector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import eu.vdmr.dspgui.component.DSPGuiComponent;

public class Connector {
	
	private ConnectorType type;
	private ConnectorPlacement position;
	private ConnectorDirection direction;
	private Dimension dim;
	private Dimension smallDim;
	private Dimension bigDim;
	private boolean selected;
	private int endX;
	private int endY;
	private List<Connector> others;
	private DSPGuiComponent parent;
	private String id;
	

	public Connector() {
		// only to be used by xml reader
		defaultDIM();
	}

	public Connector(ConnectorType type, ConnectorPlacement position, ConnectorDirection direction, DSPGuiComponent parent){
		this.type = type;
		this.position = position;
		this.direction = direction;
		defaultDIM();
		this.parent = parent;
		setId(UUID.randomUUID().toString());
	}
	
	private void defaultDIM(){
		this.smallDim = new Dimension(6,6);
		this.bigDim = new Dimension(8,8);
		this.dim = smallDim;
	}

	public ConnectorType getType() {
		return type;
	}

	public ConnectorPlacement getPosition() {
		return position;
	}
	
	public Color getColor() {
		switch(type){
		case STEREO:
			return Color.GREEN;
		case MONO:
			return Color.BLUE;
		case MONO_R:
			return Color.WHITE;
		case MONO_L:
			return Color.RED;
		case DATA:
			return Color.BLACK;
		case EVENT:
			return Color.ORANGE;
		default:
			throw new IllegalStateException("Cannot exist without type");
		}
	}

	public Dimension getDim() {
		return dim;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected == true){
			dim = bigDim;
		} else {
			dim = smallDim;
		}
	}
	
	public void setEndPoint(int x, int y){
		endX = x;
		endY = y;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}
	
	/**
	 * @param target the target connector
	 * @return null iff OK, otherwise the reject reason
	 */
	public String getRejectReason(Connector target){
		switch (this.type){
		case STEREO:
			if (target.type != ConnectorType.STEREO) {
				return "Only stereo can be connected to stereo"; 
			}
			break;
		case DATA:
			if (target.type != ConnectorType.DATA) {
				return "Only data can be connected to data"; 
			}
			break;
		case EVENT:
			if (target.type != ConnectorType.EVENT) {
				return "Only event can be connected to event"; 
			}
			break;
		default:
			if ( ! (target.type == ConnectorType.MONO ||target.type == ConnectorType.MONO_R || target.type == ConnectorType.MONO_L)) {
				return "Only mono can be connected to mono"; 
			}
		}
		if ((this.direction == ConnectorDirection.PULLING) && others != null){
			return "Can only receive ONE input"; 
		}
		return null;
	}

	public List<Connector> getOthers() {
		return others;
	}

	/**
	 * connect 2 connectors. Also connect the implementations..
	 * @param other
	 */
	public void addOther(Connector other) {
		if (others == null){
			others = new ArrayList<Connector>();
		}
		others.add(other);
		if (  this.direction == ConnectorDirection.PULLING){
			this.parent.getImpl().setPrevious(other.getParent().getImpl());
			other.getParent().getImpl().addNext(this.parent.getImpl());
		} else { // EMITTING or PUSHING 
			this.parent.getImpl().addNext(other.getParent().getImpl());
			other.getParent().getImpl().setPrevious(this.parent.getImpl());
		}
	}

	public ConnectorDirection getDirection() {
		return direction;
	}

	public void setDirection(ConnectorDirection direction) {
		this.direction = direction;
	}

	public DSPGuiComponent getParent() {
		return parent;
	}

	public void setParent(DSPGuiComponent parent) {
		this.parent = parent;
	}
	
	/**
	 * @return the point which is more the less the middle point of this connector
	 */
	public Point getMidPoint(){
		return parent.getMyMidPoint(this);
	}
	
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setType(ConnectorType type) {
		this.type = type;
	}

	public void setPosition(ConnectorPlacement position) {
		this.position = position;
	}

	public void setOthers(List<Connector> others) {
		this.others = others;
	}

}
