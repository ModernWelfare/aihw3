package Attributes;

import java.util.ArrayList;
import java.util.List;

import util.Example;
import AttributeValues.Affirmative;
import AttributeValues.AttributeValue;
import AttributeValues.Negative;

public class IsPlayerOneMoveFirst extends AttributeImpl {

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
	public AttributeValue getExampleAttributeValue(Example e) {
		if (e.getFirstMover() == 1) {
			return affirm;
		} else {
			return nega;
		}
	}

}
