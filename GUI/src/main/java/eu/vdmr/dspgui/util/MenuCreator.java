package eu.vdmr.dspgui.util;

import java.util.Iterator;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import eu.vdmr.dspgui.listeners.action.CreateComponentActionListener;
import eu.vdmr.dspgui.listeners.action.MenuEditActionListener;
import eu.vdmr.dspgui.listeners.action.MenuLoadActionListener;
import eu.vdmr.dspgui.listeners.action.MenuNewActionListener;
import eu.vdmr.dspgui.listeners.action.MenuRunActionListener;
import eu.vdmr.dspgui.listeners.action.MenuSaveActionListener;
import eu.vdmr.dspgui.listeners.action.MenuSelectActionListener;
import eu.vdmr.dspgui.listeners.action.MenuStopActionListener;
import eu.vdmr.dspgui.listeners.action.MenuSystemInfoListener;
import eu.vdmr.dspgui.util.xmlentities.Entry;
import eu.vdmr.dspgui.util.xmlentities.Group;

public class MenuCreator {
	
	public JMenuBar createMenu(){                        
		JMenuBar bar = new JMenuBar();
		JMenu fmenu = createFileMenu();
		bar.add(fmenu);
		JMenu cmenu = createComponentMenu();
		bar.add(cmenu);
		JMenu amenu = createActionMenu();
		bar.add(amenu);
		JMenu hmenu = createHelpMenu();
		bar.add(hmenu);
		
		return bar;
	}

	private JMenu createFileMenu(){
		JMenu fmenu = new JMenu();
		fmenu.setText("File");
		JMenuItem miOpen = new JMenuItem("load");
		miOpen.addActionListener(new MenuLoadActionListener());
		fmenu.add(miOpen);
		JMenuItem miSave = new JMenuItem("save");
		miSave.addActionListener(new MenuSaveActionListener());
		fmenu.add(miSave);
		JMenuItem miNew = new JMenuItem("new");
		miNew.addActionListener(new MenuNewActionListener());
		fmenu.add(miNew);
		
		return fmenu;
	}
	private JMenu createActionMenu(){
		JMenu fmenu = new JMenu();
		fmenu.setText("Action");
		// run
		JMenuItem miRun = new JMenuItem("run");
		MenuRunActionListener runListener = new MenuRunActionListener(miRun);
		miRun.addActionListener(runListener);
		fmenu.add(miRun);
		// stop
		JMenuItem miStop = new JMenuItem("stop");
		miStop.setEnabled(false); // nothing to stop yet..
		MenuStopActionListener stopListener = new MenuStopActionListener(miStop);
		miStop.addActionListener(stopListener);
		fmenu.add(miStop);
		// edit
		JMenuItem miEdit = new JMenuItem("edit");
		MenuEditActionListener editListener = new MenuEditActionListener(miEdit);
		miEdit.addActionListener(editListener);
		fmenu.add(miEdit);
		// edit
		JMenuItem miSelect = new JMenuItem("select");
		MenuSelectActionListener selectListener = new MenuSelectActionListener(miSelect);
		miSelect.addActionListener(selectListener);
		fmenu.add(miSelect);
		
		// register some of those as StateChangeListener.
		Globals.getInstance().addStateChangeListener(runListener);
		Globals.getInstance().addStateChangeListener(stopListener);
		Globals.getInstance().addStateChangeListener(editListener);
		Globals.getInstance().addStateChangeListener(selectListener);
		
		
		return fmenu;
	}
	
	private JMenu createHelpMenu(){
		JMenu hmenu = new JMenu();
		hmenu.setText("Help");
		JMenuItem miEdit = new JMenuItem("SystemInfo");
		miEdit.addActionListener(new MenuSystemInfoListener());
		hmenu.add(miEdit);
		return hmenu;
	}
	private JMenu createComponentMenu(){
		// parse directly
		ComponentReader reader = new ComponentReader("Components.xml");
		Group g = reader.getRoot();
		JMenu cmenu = new JMenu();
		cmenu.setText("Add Component");
		handleGroup(g,cmenu);
		return cmenu; 
	}
	
	private void handleGroup(Group g, JMenu parent){
		if (g.getChilds() != null){
			Iterator<Group> iter = g.getChilds().iterator();
			while (iter.hasNext()){
				Group child = iter.next();
				JMenu childMenu = new JMenu();
				childMenu.setText(child.getName());
				handleGroup(child,childMenu);
				parent.add(childMenu);
			}
		} else {
			if (g.getEntries() != null){
				Iterator<Entry> iter = g.getEntries().iterator();
				while (iter.hasNext()){
					Entry child = iter.next();
					JMenuItem mi = new JMenuItem(child.getName());
					mi.addActionListener(new CreateComponentActionListener(child.getName()));
					parent.add(mi);
					Globals.getInstance().getClassMap().put(child.getName(), child);
				}
			}
			
		}
	}
}
