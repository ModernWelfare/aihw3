package AttributeValues;

public class Affirmative implements AttributeValue {

	@Override
	public boolean equals(AttributeValue a) {
		return a.getClass().equals(this.getClass());
	}

}
