package AttributeValues;

/**
 * Value for an attribute
 * Affirmative implies that the attribute contributes to the "goodness" of the board
 * for the current player.
 * 
 * @author bli tnarayan
 * 
 */
public class Affirmative implements AttributeValue {

	@Override
	public boolean equals(AttributeValue a) {
		return a.getClass().equals(this.getClass());
	}

}
