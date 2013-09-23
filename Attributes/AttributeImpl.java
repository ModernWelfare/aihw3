package Attributes;

import java.util.ArrayList;
import java.util.List;

import util.Example;
import AttributeValues.AttributeValue;

public abstract class AttributeImpl implements Attribute {
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
			sizeRatioList.add((double) eList.size()
					/ (double) exampleCollection.size());
		}

		for (int i = 0; i < aValueList.size(); i++) {
			entropyReminder += sizeRatioList.get(i) * entropyList.get(i);
		}

		return startEntropy - entropyReminder;
	}

	private double getEntropy(List<Example> exampleCollection) {
		double positiveExampleCount = 0;
		double negativeExampleCount = 0;
		double q;

		for (Example e : exampleCollection) {
			if (e.getWinner() == 1) {
				positiveExampleCount++;
			} else {
				negativeExampleCount++;
			}
		}

		if (!((positiveExampleCount + negativeExampleCount) == 0)) {
			q = positiveExampleCount
					/ (positiveExampleCount + negativeExampleCount);
		} else {
			q = 1;
		}

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
}
