package eu.vdmr.dspgui.util.project;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import eu.vdmr.dspgui.component.DSPGuiComponent;
import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.component.connector.Connector;
import eu.vdmr.dspgui.component.connector.ConnectorDirection;
import eu.vdmr.dspgui.component.connector.ConnectorPlacement;
import eu.vdmr.dspgui.component.connector.ConnectorType;
import eu.vdmr.dspgui.util.Globals;
import eu.vdmr.util.clazz.ObjectInitialiser;
import eu.vdmr.util.xml.XMLFileWriter;


/**
 * reads and writes a project to/from a xml-file
 * can handle all generic stuff, for component specific stuff the DSPGuiComponent must provide a ComponentFiler
 * 
 * @author ura03640
 *
 */
public  class ProjectFiler extends DefaultHandler {
	private static Logger LOG = LogManager.getLogger(ProjectFiler.class);
	private boolean withinComponent = false;

	private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
	private File target;
	private List<DSPGuiComponent> components; // components to write...

	private DSPGuiComponentImpl guiComponent;
	private StringBuilder sb;
	private ComponentFiler componentFiler;
	private int tmpInt;
	
	DSPGuiComponent readGUIComponent;
	List<Connector> readConnectors;
	Connector readConnector;
	List<Connector> allConnectors;

	public void saveFile(File file) throws IOException {
		target = file;
		components = Globals.getInstance().getGuiComponents();
		if (components.size() == 0) {
			throw new IllegalArgumentException("No Component to save!");
		}
		XMLFileWriter fw = new XMLFileWriter(target.getCanonicalFile(), false); // no NOT append
		writeHeader(fw);
		for (DSPGuiComponent guiComponent : components) {
			writeComponent(fw, (DSPGuiComponentImpl) guiComponent);
		}
		writeConnectors(fw);
		writeConnections(fw);
		writeFooter(fw);
		fw.flush();
		fw.close();
	}

	public void loadFile(File file) throws IOException {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(file, this);
			Globals.getInstance().getPanel().repaint();
		} catch (SAXException e) {
			LOG.error("SAXException " + e,e);
		} catch (ParserConfigurationException e) {
			LOG.error("ParserConfigurationException " + e,e);
		}
	}

	
	/**
	 * we only handle outgoing connectors, thus we font have any doubles.
	 * @param fw
	 * @throws IOException
	 */
	private void writeConnectors(XMLFileWriter fw) throws IOException {
		LOG.debug("Writing cconnectors..");
		fw.startLabel(0,XMLLabel.CONNECTORS);
		for (DSPGuiComponent guiComponent : components) {
			String[] keys = new String[]{XMLLabel.PARENTIDATTRIBUTE};
			String[] values = new String[]{guiComponent.getId()};
			fw.startAttributedLabel(1,XMLLabel.CONNECTORPARENT,keys,values);
			Connector[] connectors = guiComponent.getConnectors();
			if (connectors != null){
				for (int i = 0; i < connectors.length; i++){
					Connector connector = connectors[i];
					writeConnector(fw,connector,guiComponent);
				}
			}
			fw.endLabel(1,XMLLabel.CONNECTORPARENT);
		}
		fw.endLabel(0,XMLLabel.CONNECTORS);
	}
	
	private void writeConnector(XMLFileWriter fw, Connector connector,DSPGuiComponent guiComponent) throws IOException{
		String[] keys = new String[]{XMLLabel.IDATTRIBUTE};
		String[] values = new String[]{connector.getId()};
		fw.startAttributedLabel(2,XMLLabel.CONNECTOR,keys,values);
//		fw.makeLabel(3,XMLLabel.CONNECTORID,""+connector.getId());
		fw.makeLabel(3,XMLLabel.CONNECTORTYPE,""+connector.getType().getName());
		fw.makeLabel(3,XMLLabel.CONNECTORPOSITION,""+connector.getPosition().getName());
		fw.makeLabel(3,XMLLabel.CONNECTORDIRECTON,""+connector.getDirection().getName());
		fw.endLabel(2,XMLLabel.CONNECTOR);
	}
	

	/**
	 * we only handle outgoing connectors, thus we won't have any doubles.
	 * @param fw
	 * @throws IOException
	 */
	private void writeConnections(XMLFileWriter fw) throws IOException {
		LOG.debug("Writing cconnections..");
		for (DSPGuiComponent guiComponent : components) {
			// check whether this component has any outgoing connections..
			boolean outgoing = false;
			Connector[] connectors = guiComponent.getConnectors();
			if (connectors != null){
				for (int i = 0; i < connectors.length; i++){
					Connector connector = connectors[i];
					if (  connector.getDirection() == ConnectorDirection.EMITTING) {
						outgoing = true;
					}
				}
				if (outgoing){
					String[] names = new String[]{XMLLabel.IDATTRIBUTE};
					String[] values = new String[]{guiComponent.getId()};
					LOG.debug("guicompponent of class " + guiComponent.getClass().getName() + " has id " + guiComponent.getId());
					fw.startAttributedLabel(1,XMLLabel.CONNECTEDCOMPONENT,names,values);
					for (int i = 0; i < connectors.length; i++){
						Connector connector = connectors[i];
						if (  connector.getDirection() == ConnectorDirection.EMITTING) {
							writeConnection(fw, connector);
						}
					}
					
					fw.endLabel(1,XMLLabel.CONNECTEDCOMPONENT);
					
				} else {
					LOG.debug("no outgoing connectors for " + guiComponent.getId());
				}
			}
		}
	}
	private void writeConnection(XMLFileWriter fw, Connector connector) throws IOException{
		fw.startLabel(2,XMLLabel.CONNECTION);
		fw.makeLabel(3,XMLLabel.CONNECTORID,""+connector.getId());
		fw.startLabel(3,XMLLabel.CONNECTORCONNECTIONS);
		List<Connector> others = connector.getOthers();
		if (others != null) {
			for (Connector other : others){
				fw.makeLabel(4,XMLLabel.CONNECTORCONNECTIONSID,other.getId());
			}
		}
		fw.endLabel(3,XMLLabel.CONNECTORCONNECTIONS);
		fw.endLabel(2,XMLLabel.CONNECTION);
	}

	private void writeComponent(XMLFileWriter fw, DSPGuiComponentImpl guiComponent) throws IOException {
		fw.startLabel(0, XMLLabel.GUICOMPONENT);
		fw.makeLabel(1, XMLLabel.CLASS, guiComponent.getClass().getName());
		fw.makeLabel(1, XMLLabel.ID, guiComponent.getId());
		fw.makeLabel(1, XMLLabel.X, "" + guiComponent.getPoint().x);
		fw.makeLabel(1, XMLLabel.Y, "" + guiComponent.getPoint().y);
		fw.makeLabel(1, XMLLabel.HEIGHT, "" + guiComponent.getDim().height);
		fw.makeLabel(1, XMLLabel.WIDTH, "" + guiComponent.getDim().width);
		fw.makeLabel(1, XMLLabel.IMAGE, guiComponent.getImageName());
		fw.makeLabel(1, XMLLabel.INITIALIZED, "" + guiComponent.isInitialized());
		fw.startLabel(1, XMLLabel.COMPSPECIFIC);
		ComponentFiler compFiler = guiComponent.getFiler();
		if (compFiler != null) {
			compFiler.write(1, fw, guiComponent);
		}
		fw.endLabel(1, XMLLabel.COMPSPECIFIC);
		fw.endLabel(0, XMLLabel.GUICOMPONENT);
	}

	private void writeHeader(XMLFileWriter fw) throws IOException {
		fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n");
		fw.write("<!--\n");
		fw.write("   generated on " + dateformat.format(new Date()) + " by RagaDSPGui\n");
		fw.write("   file = " + target.getCanonicalPath() + "\n");
		fw.write("-->\n\n");
		fw.startLabel(0, XMLLabel.DSPCUIPROJECT);
	}

	protected void writeFooter(XMLFileWriter fw) throws IOException {
		fw.endLabel(0, XMLLabel.DSPCUIPROJECT);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		LOG.debug("startElement called, qname = " + qName);
		sb = new StringBuilder();
		if ("CompSpecific".equals(qName)) {
			withinComponent = true; // next stuff is for specific stuff
			componentFiler = guiComponent.getFiler();
		} else {
			if (withinComponent) {
				componentFiler.startElement(uri, localName, qName, atts);
			} else if (XMLLabel.CONNECTORS.equals(qName)){
				// here we will keep all connectors..
				allConnectors = new ArrayList<Connector>();
			} else if (XMLLabel.CONNECTORPARENT.equals(qName)){
				// get the GUIComponent
				this.readGUIComponent= getGuiComponent(atts.getValue(XMLLabel.PARENTIDATTRIBUTE));
				// here we will keep all connectors for a specific component
				readConnectors = new ArrayList<Connector>();
			} else if (XMLLabel.CONNECTOR.equals(qName)){
				Connector con = new Connector();
				con.setParent(readGUIComponent);
				con.setId(atts.getValue(XMLLabel.IDATTRIBUTE));
				readConnectors.add(con);
				allConnectors.add(con);
			} else if (XMLLabel.CONNECTEDCOMPONENT.equals(qName)){ // we start the real connections for one component
				this.readGUIComponent= getGuiComponent(atts.getValue(XMLLabel.IDATTRIBUTE));
			}
		}
	}
	 
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		LOG.debug("endElement called, qname = " + qName);
		if (XMLLabel.COMPSPECIFIC.equals(qName)) {
			withinComponent = false;
			componentFiler = null;
			return;
		}
		if (withinComponent) {
//			LOG.debug("calling componentFile, guiComponent = " + (guiComponent==null?"NULL":"NOT NULL"));
			componentFiler.endElement(uri, localName, qName,guiComponent );
			return;
		}
		if (XMLLabel.CLASS.equals(qName)) {
			String clname = sb.toString();
			Object o = ObjectInitialiser.getObject(clname);
			guiComponent = (DSPGuiComponentImpl) o;
			LOG.debug("guiComponent set to " + o.getClass().getName());
		} else if (XMLLabel.X.equals(qName)) {
			tmpInt = Integer.parseInt(sb.toString());
		} else if (XMLLabel.Y.equals(qName)) {
			guiComponent.setPoint(new Point(tmpInt, Integer.parseInt(sb.toString())));
		} else if (XMLLabel.HEIGHT.equals(qName)) {
			tmpInt = Integer.parseInt(sb.toString());
		} else if (XMLLabel.WIDTH.equals(qName)) {
			guiComponent.setDimension(new Dimension(tmpInt, Integer.parseInt(sb.toString())));
		} else if (XMLLabel.IMAGE.equals(qName)) {
			guiComponent.setImageName(sb.toString());
		} else if (XMLLabel.INITIALIZED.equals(qName)) {
			guiComponent.setInitialized(Boolean.parseBoolean(sb.toString()));
		} else if (XMLLabel.ID.equals(qName)) {
			guiComponent.setId(sb.toString());
		} else if (XMLLabel.CONNECTORTYPE.equals(qName)) {
			Connector con = readConnectors.get(readConnectors.size()-1);
			con.setType(ConnectorType.forName(sb.toString()));
		} else if (XMLLabel.CONNECTORPOSITION.equals(qName)) {
			Connector con = readConnectors.get(readConnectors.size()-1);
			con.setPosition(ConnectorPlacement.forName(sb.toString()));
		} else if (XMLLabel.CONNECTORDIRECTON.equals(qName)) {
			Connector con = readConnectors.get(readConnectors.size()-1);
			con.setDirection(ConnectorDirection.forName(sb.toString()));
		} else if (  XMLLabel.CONNECTORPARENT.equals(qName)){
			//all connectors of the parent are collected in readConnectors
			//now we set those...
			readGUIComponent.replaceConnectors(readConnectors.toArray(new Connector[readConnectors.size()]));
		} else if (XMLLabel.GUICOMPONENT.equals(qName)) {
			// end of GuiComponent
			//components.add(guiComponent);
			Globals.getInstance().addGuiComponent(guiComponent);
		} else if (XMLLabel.CONNECTORID.equals(qName)){ // we start the real connections for one connector
			Connector[] cons = readGUIComponent.getConnectors();
			String definedId = sb.toString();
			for (int i = 0; i < cons.length; i++){
				if (cons[i].getId().equals(definedId)){
					// the connector we will connect
					this.readConnector = cons[i];
				}
			}
		} else if (XMLLabel.CONNECTORCONNECTIONSID.equals(qName)) {
			String targetConnectorId = sb.toString();
			for (int i = 0; i < allConnectors.size(); i++){
				Connector target = allConnectors.get(i);
				if (targetConnectorId.equals(target.getId())){
					this.readConnector.addOther(target);
				}
			}
		} else if (XMLLabel.DSPCUIPROJECT.equals(qName)) {
			// end of project
		} else if (  XMLLabel.CONNECTOR.equals(qName)  // Connector finished 
			      || XMLLabel.CONNECTORS.equals(qName)  // all Connectors finished
			      || XMLLabel.CONNECTORCONNECTIONS.equals(qName)  // all Connections finished
			      || XMLLabel.CONNECTION.equals(qName)  // one Connection finished
		          || XMLLabel.CONNECTEDCOMPONENT.equals(qName)) {  // connected component finished 
			// NOP no action on end of those tags..
		} else {
			LOG.error("Unknown tag " + qName);
		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		LOG.debug("characters called..  " + new String(ch, start, length));
		if (withinComponent) {
			componentFiler.characters(ch, start, length);
		} else {
			sb.append(ch, start, length);
		}
	}
	

	
	private DSPGuiComponent getGuiComponent(String id){
		List<DSPGuiComponent> searchComps = Globals.getInstance().getGuiComponents();
		for (DSPGuiComponent guiComponent : searchComps) {
			if (id.equals(guiComponent.getId())){
				return guiComponent;
			}
		}
		return null;
	}

}
