package AttributeValues;

public class Negative implements AttributeValue {
	@Override
	public boolean equals(AttributeValue a) {
		return a.getClass() == this.getClass();
	}
}
