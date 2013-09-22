package Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.Board;
import util.Example;
import util.FileReader;
import util.Result;
import AttributeValues.AttributeValue;
import Attributes.Attribute;
import Attributes.IsWinner;

/*******************************************************************************
 * This files was developed for CS4341: Artificial Intelligence. The course was
 * taken at Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

/**
 * Main class for project implementation
 * 
 * @author bli tnarayan
 * 
 */
public class DecisionTree {
	// a map of the board and the result
	private List<Board> boardCollection;
	private List<Result> resultCollection;
	private List<Example> exampleCollection;
	// file directory to store the board csv files and the result csv files,
	// expect to change
	private final String DIRECTORY = "test_dataset/";

	/**
	 * load the contents of the csv files and store them in lists, map the board
	 * states to results of games
	 * 
	 * @param none
	 * 
	 */
	public void setup() {
		FileReader fr = FileReader.getInstance();
		List<String> bufferList;
		boardCollection = new ArrayList<Board>();
		resultCollection = new ArrayList<Result>();
		exampleCollection = new ArrayList<Example>();
		String filePath;

		// get the board information and store them in the board list
		for (int i = 1; i <= 80; i++) {
			filePath = DIRECTORY + i + ".csv";
			try {
				bufferList = fr.readCSVFile(filePath);
				boardCollection.add(new Board(bufferList));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// get the result information and store them in the result list
		filePath = DIRECTORY + "training_results.csv";
		try {
			bufferList = fr.readCSVFile(filePath);
			bufferList.remove(0);
			for (String resultString : bufferList) {
				resultCollection.add(new Result(resultString));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// map the boards to their results
		for (int i = 0; i < 80; i++) {
			exampleCollection.add(new Example(boardCollection.get(i),
					resultCollection.get(i)));
		}
	}

	/**
	 * Function to build a decision tree based on a decision tree learning
	 * algorithm
	 * 
	 * @param examples
	 * @param attributes
	 * @param parentExamples
	 * @return
	 */
	public DecisionTreeNode decisionTreeLearning(List<Example> examples,
			List<Attribute> attributes, List<Example> parentExamples) {
		Attribute mostImportantAttribute = null;
		if (examples.isEmpty()) {
			return new DecisionTreeNode(pluralityValue(parentExamples));
		} else if (allInSameClass(examples)) {
			return new DecisionTreeNode(examples.get(0).getWinner());
		} else if (attributes.isEmpty()) {
			return new DecisionTreeNode(pluralityValue(examples));
		} else {
			int maxAttributeValue = Integer.MIN_VALUE;
			for (Attribute a : attributes) {
				if (a.getImportance(examples) > maxAttributeValue) {
					mostImportantAttribute = a;
				}
			}

			DecisionTreeNode root = new DecisionTreeNode(mostImportantAttribute);

			for (AttributeValue aValue : mostImportantAttribute
					.getAttributeValues()) {
				List<Example> filteredExamples = new ArrayList<Example>();
				for (Example e : examples) {
					if (mostImportantAttribute.getExampleAttributeValue(e)
							.equals(aValue)) {
						filteredExamples.add(e);
					}
				}

				List<Attribute> newAttributeList = new ArrayList<Attribute>();
				for (Attribute a : attributes) {
					if (a != mostImportantAttribute) {
						newAttributeList.add(a);
					}
				}

				DecisionTreeNode newNode = decisionTreeLearning(
						filteredExamples, newAttributeList, examples);
				newNode.setAttributeValue(aValue);

				root.addChild(newNode);
			}

			return root;
		}
	}

	private int pluralityValue(List<Example> examples) {
		int pluWinner;
		int winner1Count = 0;
		int winner2Count = 0;
		for (Example e : examples) {
			if (e.getWinner() == 1) {
				winner1Count++;
			} else {
				winner2Count++;
			}
		}

		pluWinner = (winner1Count > winner2Count) ? 1 : 2;
		return pluWinner;
	}

	private boolean allInSameClass(List<Example> examples) {
		boolean allSame = true;
		int firstValue = examples.get(0).getWinner();
		for (Example e : examples) {
			if (e.getWinner() != firstValue) {
				allSame = false;
			}
		}
		return allSame;
	}

	/**
	 * Main function to run the program and to generate predictions
	 * 
	 */
	public static void main(String[] args) {
		DecisionTree dTree = new DecisionTree();

		dTree.setup();

		List<Attribute> attributeList = new ArrayList<Attribute>();

		attributeList.add(new IsWinner());

		DecisionTreeNode root = dTree
				.decisionTreeLearning(dTree.exampleCollection, attributeList,
						dTree.exampleCollection);

		root.printTree();

	}
}
