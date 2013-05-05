package libraries.optionspinner;

/**
 * Abstract interface representing a class that can have listeners of type T
 * 
 * @author Niels Billen
 * @version 0.1
 */
public interface ListenerContainer<T> {
	/**
	 * Add's a listener to the container, does nothing when the listener is
	 * null.
	 * 
	 * @param listener
	 *            The listener to add to the container.
	 */
	public void addListener(T listener);

	/**
	 * Removes the listener from the container.
	 * 
	 * @param listener
	 *            The listener to remove.
	 */
	public void removeListener(T listener);
}