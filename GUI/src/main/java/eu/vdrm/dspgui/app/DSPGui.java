package eu.vdrm.dspgui.app;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import eu.vdrm.dspgui.listeners.mouse.EditMouseListener;
import eu.vdrm.dspgui.util.ApplicationState;
import eu.vdrm.dspgui.util.Globals;
import eu.vdrm.dspgui.util.MenuCreator;

public class DSPGui extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 480;
	public static final Dimension DEFAULT_DIMENSION = new Dimension(SIZE - SIZE / 3, SIZE - SIZE / 3);
	public static final int MARGIN = 50;
	public static final Dimension COMPONENT_DIMENSION =  new Dimension(50, 50);

	
	public void start(){
		this.setLayout(new BorderLayout());  //TODO to be decided yet
        final DSPPanel panel = new DSPPanel("RagaDSP");
        EditMouseListener ml = new EditMouseListener();
        panel.addMouseListener(ml);
        panel.addMouseMotionListener(ml);
        panel.setOpaque(false); // why this? otherwise paint is not called if scrolling is done..
        panel.setPreferredSize(DEFAULT_DIMENSION);
        final DSPViewPort viewPort = new DSPViewPort();
        viewPort.setView(panel);
        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewport(viewPort);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, BorderLayout.CENTER);
        MenuCreator mc = new MenuCreator();
        this.setJMenuBar(mc.createMenu());
        this.pack();
        this.setSize(SIZE/* - SIZE / 3*/, SIZE /*- SIZE / 3*/);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        Globals.getInstance().setMasterFrame(this);
        Globals.getInstance().setState(ApplicationState.EDIT);
        ImageIcon icon = Globals.getInstance().getImageIconFromFile("ragahindi.png");
        this.setIconImage(icon.getImage());
        this.setVisible(true);	
	}

}
