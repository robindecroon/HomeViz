/**
 * 
 */
package robindecroon.homeviz.exceptions;

/**
 * @author Robin
 * 
 */
public class ParseXMLException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Object object;

	public ParseXMLException(Object object) {
		super("Possible parse exception for object:" + object.toString());
		this.setObject(object);
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}

}
