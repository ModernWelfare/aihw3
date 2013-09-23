package Attributes;

import java.util.ArrayList;
import java.util.List;

import util.Example;
import AttributeValues.Affirmative;
import AttributeValues.AttributeValue;
import AttributeValues.Negative;

public class IsHorizontalUnboundedThreeInARow implements Attribute {

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
	public double getImportance(List<Example> exampleCollection) {
		// assume winner 1 as positive, winner 2 as negative

		double startEntropy = getEntropy(exampleCollection);
		double entropyReminder = 0;

		List<AttributeValue> aValueList = getAttributeValues();
		List<Double> entropyList = new ArrayList<Double>();
		List<Double> sizeRatioList = new ArrayList<Double>();
		List<ArrayList<Example>> exampleSplit = new ArrayList<ArrayList<Example>>();

		for (AttributeValue aValue : aValueList) {
			exampleSplit.add(new ArrayList<Example>());

			int index = aValueList.indexOf(aValue);
			for (Example e : exampleCollection) {
				if (getExampleAttributeValue(e).equals(aValue)) {
					exampleSplit.get(index).add(e);
				}
			}
		}

		for (ArrayList<Example> eList : exampleSplit) {
			double subEntropy = getEntropy(eList);
			entropyList.add(subEntropy);
			sizeRatioList
					.add((double) (eList.size() / exampleCollection.size()));
		}

		for (int i = 0; i < aValueList.size(); i++) {
			entropyReminder += sizeRatioList.get(i) * entropyList.get(i);
		}

		return startEntropy - entropyReminder;
	}

	private double getEntropy(List<Example> exampleCollection) {
		double positiveExampleCount = 0;
		double negativeExampleCount = 0;

		for (Example e : exampleCollection) {
			if (e.getWinner() == 1) {
				positiveExampleCount++;
			} else {
				negativeExampleCount++;
			}
		}

		double q = positiveExampleCount
				/ (positiveExampleCount + negativeExampleCount);

		// make q not zero but very small for the sake of correctly carrying out
		// logarithmic calculations
		if (q == 0) {
			q = 0.01;
		} else if (q == 1) {
			q = 0.99;
		}

		double entropy = -(q * (Math.log(q) / Math.log(2)) + (1 - q)
				* (Math.log(1 - q) / Math.log(2)));

		return entropy;
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
