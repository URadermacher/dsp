package eu.vdmr.dspgui.util.xmlentities;


import java.util.ArrayList;
import java.util.List;

public class Group  {
	
	private List<Group> childs;
	private List<Entry> entries;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addEntry(Entry entry){
		if (childs != null){
			throw new IllegalStateException("Group cannot have subgroups and entries!");
		}
		if (entries == null){
			entries = new ArrayList<Entry>();
		}
		entries.add(entry);
	}

	public void addGroup(Group group){
		if (entries != null){
			throw new IllegalStateException("Group cannot have entries and subgroups !");
		}
		if (childs == null){
			childs = new ArrayList<Group>();
		}
		childs.add(group);
	}

	public List<Group> getChilds() {
		return childs;
	}

	public List<Entry> getEntries() {
		return entries;
	}

}
