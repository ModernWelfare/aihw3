package AttributeValues;

/**
 * Value for an attribute
 * Negative implies that the attribute takes away from the "goodness" of the board
 * for the current player.
 * 
 * @author bli tnarayan
 * 
 */
public class Negative implements AttributeValue {
	@Override
	public boolean equals(AttributeValue a) {
		return a.getClass() == this.getClass();
	}
}
