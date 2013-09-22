package Attributes;

import java.util.ArrayList;
import java.util.List;

import util.Example;
import AttributeValues.Affirmative;
import AttributeValues.AttributeValue;
import AttributeValues.Negative;

public class IsWinner implements Attribute {

	private final AttributeValue affirm = new Affirmative();
	private final AttributeValue nega = new Negative();

	@Override
	public List<AttributeValue> getAttributeValues() {
		List<AttributeValue> attributeList = new ArrayList<AttributeValue>();
		attributeList.add(affirm);
		attributeList.add(nega);
		return attributeList;
	}

	@Override
	public int getImportance(List<Example> exampleCollection) {
		return 10;
	}

	@Override
	public AttributeValue getExampleAttributeValue(Example e) {
		return (e.getWinner() == 1) ? affirm : nega;
	}

}
