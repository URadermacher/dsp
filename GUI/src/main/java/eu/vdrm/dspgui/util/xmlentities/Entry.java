package eu.vdrm.dspgui.util.xmlentities;

public class Entry {
	/**
	 * name to be displayed in lists/menus
	 */
	private String name;
	/**
	 * class of the GUI component
	 */
	private String className;
	/**
	 * ClassName of the implementation class
	 */
	private String implName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getImplName() {
		return implName;
	}

	public void setImplName(String implName) {
		this.implName = implName;
	}

}
