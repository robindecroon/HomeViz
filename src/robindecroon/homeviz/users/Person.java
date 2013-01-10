/**
 * 
 */
package robindecroon.homeviz.users;

import robindecroon.homeviz.exceptions.ParseXMLException;

/**
 * @author Robin
 * 
 */
public class Person {

	private String name;

	public Person(String name) {
		this.setName(name);
	}

	public Person() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		if (name == null) {
			throw new ParseXMLException(this);
		}
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person named " + getName();
	}

	// Use @Override to avoid accidental overloading.
	@Override
	public boolean equals(Object o) {
		// Return true if the objects are identical.
		// (This is just an optimization, not required for correctness.)
		if (this == o) {
			return true;
		}

		// Return false if the other object has the wrong type.
		// This type may be an interface depending on the interface's
		// specification.
		if (!(o instanceof Person)) {
			return false;
		}

		// Cast to the appropriate type.
		// This will succeed because of the instanceof, and lets us access
		// private fields.
		Person lhs = (Person) o;

		// Check each field. Primitive fields, reference fields, and nullable
		// reference
		// fields are all treated differently.
		return name.equals(lhs.getName());
	}

	@Override
	public int hashCode() {
		// Start with a non-zero constant.
		int result = 17;

		// Include a hash for each field.
		result = 31 * result + name.hashCode();

		return result;
	}

}
