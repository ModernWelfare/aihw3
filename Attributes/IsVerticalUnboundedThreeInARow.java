package Attributes;

import java.util.ArrayList;
import java.util.List;

import util.Example;
import AttributeValues.Affirmative;
import AttributeValues.AttributeValue;
import AttributeValues.Negative;

public class IsVerticalUnboundedThreeInARow extends AttributeImpl {

	private final AttributeValue affirm = new Affirmative();
	private final AttributeValue nega = new Negative();

	private boolean unbound = false;

	@Override
	public List<AttributeValue> getAttributeValues() {
		List<AttributeValue> attributeList = new ArrayList<AttributeValue>();
		attributeList.add(affirm);
		attributeList.add(nega);
		return attributeList;
	}

	@Override
	public AttributeValue getExampleAttributeValue(Example e) {
		int playerToken = e.getResult().currentTurn;
		int height = e.getBoard().height; // 6
		int width = e.getBoard().width; // 7

		int numTokensCountingFor = 3; // checking for 3 in a row

		int countOfTokensEncounteredVertically = 0; // counter

		// bottom index is (5, 0)
		for (int i = height - 1; i > -1; i--) {
			for (int j = 0; j < width; j++) {
				if (e.getBoard().boardArray[i][j] == playerToken) {
					// current player's tokens
					for (int k = i; k > -1; k--) {
						if (e.getBoard().boardArray[i][j] == e.getBoard().boardArray[k][j]) {
							countOfTokensEncounteredVertically++;
						} else {
							if (e.getBoard().boardArray[k][j] == 0) {
								// top of the connection of 3 tokens is empty
								unbound = true;
							} else {
								// since the continuity between tokens is
								// broken, stop
								break;
							}
						}
					}
				}
			}
		}

		if ((countOfTokensEncounteredVertically >= numTokensCountingFor)
				&& (unbound)) {
			return affirm;
		} else {
			return nega;
		}
	}
}
