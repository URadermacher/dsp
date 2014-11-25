package eu.vdrm.dspgui.component.analyzer.transform;

import java.awt.Graphics;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import eu.vdrm.dsp.component.analyzer.transform.SimpleFFT;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.component.connector.Connector;
import eu.vdrm.dspgui.component.connector.ConnectorDirection;
import eu.vdrm.dspgui.component.connector.ConnectorPlacement;
import eu.vdrm.dspgui.component.connector.ConnectorType;
import eu.vdrm.dspgui.popups.GeneralTextPopUp;
import eu.vdrm.dspgui.util.Globals;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.component.analyzer.SimpleFFTFiler;

public class SimpleFFTGui extends DSPGuiComponentImpl {
    private static Logger LOG = Logger.getLogger(SimpleFFTGui.class);
    private GeneralTextPopUp createPopUp;

    public SimpleFFTGui() {
        LOG.debug("SimpleFFTGui inits");
        setInitialized(false);
        loadImage("SimpleFFT_icon.png");
        LOG.debug("creating connectors");
        connectors = new Connector[2];
        Connector in = new Connector(ConnectorType.MONO, ConnectorPlacement.WEST, ConnectorDirection.PULLING, this);
        connectors[0] = in;
        Connector out = new Connector(ConnectorType.MONO, ConnectorPlacement.EAST, ConnectorDirection.EMITTING, this);
        connectors[1] = out;
    }

    // TODO make it work with the WavReader

    public void paint(final Graphics g) {
        super.paintDefault(g);
    }

    @Override
    public void paintName(Graphics g) {
        g.setColor(C_TEXT);
        g.drawString("SFFT", pos.x, pos.y + dim.height);
    }

    public ComponentFiler getFiler() {
        return new SimpleFFTFiler();
    }

    @Override
    public void showPopUp() {
        if (!initialized) {
            showCreatePopUp();
        }
    }

    private void showCreatePopUp() {
        if (createPopUp == null) {
            createPopUp = new GeneralTextPopUp(Globals.getInstance().getMasterFrame(), "SimpleFFT", 
                    new String[] { "number of samples",
                                   "overlap nr of samples",
                                   "File to log in" });
            if (getImpl() != null) {
                int setInt = ((SimpleFFT) getImpl()).getNumberOfSamples();
                if (setInt != 0) {
                    createPopUp.setText(0, Integer.toString(setInt));
                }
                setInt = ((SimpleFFT) getImpl()).getNumberOfOverlap();
                if (setInt != 0) {
                    createPopUp.setText(1, Integer.toString(setInt));
                }
                try {
                    String filename =  ((SimpleFFT) getImpl()).getFileName();
                    createPopUp.setText(2,filename);
                } catch (IOException ioe){
                    LOG.error("IOException getting filename: " + ioe,ioe);
                    // nothing further, just let the file empty
                }
            }
        }
        createPopUp.setVisible(true);
        LOG.debug("back from dialog");
        String res = createPopUp.getResultbutton();
        if ("OK".equals(res)) {
            LOG.debug("OK pressed");
            ((SimpleFFT) impl).setNumberOfSamples(Integer.parseInt(createPopUp.getValue(0)));
            ((SimpleFFT) impl).setNumberOfOverlap(Integer.parseInt(createPopUp.getValue(1)));
            try {
                ((SimpleFFT) impl).SetFileName(createPopUp.getValue(2));
            } catch (Exception e) {
                LOG.error("Exception setting filename " + createPopUp.getValue(1) + ": " + e, e);
                JOptionPane.showMessageDialog(Globals.getInstance().getMasterFrame(), "Exception setting filename " + createPopUp.getValue(1) + ": " + e
                        + ". See logging for trace");
            }
            setInitialized(true);
            // show new color:
            Globals.getInstance().getPanel().repaint();
        }
    }

}
