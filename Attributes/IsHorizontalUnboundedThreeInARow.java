package Attributes;

import java.util.ArrayList;
import java.util.List;

import util.Example;
import AttributeValues.Affirmative;
import AttributeValues.AttributeValue;
import AttributeValues.Negative;

public class IsHorizontalUnboundedThreeInARow extends AttributeImpl {

	private final AttributeValue affirm = new Affirmative();
	private final AttributeValue nega = new Negative();

	private boolean leftUnbound = false;
	private boolean rightUnbound = false;

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

		int countOfTokensEncounteredHorizontally = 0; // counter

		// bottom index is (5, 0)
		for (int i = height - 1; i > -1; i--) {
			for (int j = 0; j < width; j++) {
				if (e.getBoard().boardArray[i][j] == playerToken) { // current
																	// player's
																	// tokens
					for (int k = j; k < width; k++) {
						if (e.getBoard().boardArray[i][j] == e.getBoard().boardArray[i][k]) {
							countOfTokensEncounteredHorizontally++;
						} else {
							if (countOfTokensEncounteredHorizontally == numTokensCountingFor) {
								int leftBound = k - numTokensCountingFor - 1;
								int rightBound = k;
								if (leftBound >= 0
										&& (e.getBoard().boardArray[i][leftBound] == 0))
									leftUnbound = true;
								if (rightBound < width
										&& (e.getBoard().boardArray[i][rightBound] == 0))
									rightUnbound = true;
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

		if ((countOfTokensEncounteredHorizontally >= numTokensCountingFor)
				&& (leftUnbound || rightUnbound)) {
			return affirm;
		} else {
			return nega;
		}
	}
}
