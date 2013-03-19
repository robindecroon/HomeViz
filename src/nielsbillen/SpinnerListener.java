package nielsbillen;

/**
 * 
 * @author Niels
 * @version 0.1
 */
public interface SpinnerListener {
	/**
	 * Notified when the option in the spinner is changed.
	 * 
	 * @param index
	 * @param name
	 */
	public void optionChanged(int index, String name);
}