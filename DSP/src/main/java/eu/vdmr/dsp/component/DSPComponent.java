package eu.vdmr.dsp.component;



public interface DSPComponent {
	

	/**
	 * Implement this method if reset functionality is required
	 * for your AbstractAudio device derivative. If not overridden
	 * nothing will happen in this device when a reset operation
	 * occurs.
	 */

	public void reset();
	
	/**
	 * Propagate reset call to all processing stages
	 */
	public void propagateReset();
	
	/**
	 * Called to perform a reset operation on all participating device 
	 * stages.
	 */
	public void doReset();
	
	/**
	 * set the previous RagaDSPComponent, where to pull data from
	 * (not applicable for SOURCE type components)
	 */
	public void setPrevious(DSPComponent previous);
	
	/**
	 *  Add a component to the list of next's. (An emitter/pusher can have several pullers/receivers)
	 */
	public void addNext(DSPComponent next);

	public int getSamples(short [] buffer, int length);
	
	public String getName();
	
	public void setName(String name);

	public DeviceType getType();
	
	public String getId();
	
	public void setId(String id);
	
	/**
	 * do anything to clean up, close or so, run stopped..
	 */
	public void stop() throws Exception;

}
