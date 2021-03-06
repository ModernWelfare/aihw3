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
import Attributes.IsDiagonalLeftThreeInARow;
import Attributes.IsHorizontalUnboundedThreeInARowWithOpponent;
import Attributes.IsHorizontalUnboundedTwoInARowWithOpponent;
import Attributes.IsPlayerOneMoveFirst;
import Attributes.IsVerticalUnboundedThreeInARow;

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
 * This is the class that implements the making of a decision tree based on the
 * set of training data
 * 
 * @author bli tnarayan
 * 
 */
public class DecisionTree {
	// a map of the board and the result
	private List<Board> boardCollection;
	private List<Result> resultCollection;
	private List<Example> exampleCollection;
	private List<Example> submissionExamples;
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
		submissionExamples = new ArrayList<Example>();

		List<Result> sbumissionResults = new ArrayList<Result>();
		List<Board> submissionBoards = new ArrayList<Board>();

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

		filePath = "C:/AIproj3/test.csv";
		try {
			bufferList = fr.readCSVFile(filePath);
			for (String resultString : bufferList) {
				sbumissionResults.add(new Result(resultString));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 81; i < 101; i++) {
			filePath = "C:/AIproj3/" + i + ".csv";
			try {
				bufferList = fr.readCSVFile(filePath);
				submissionBoards.add(new Board(bufferList));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// map the boards to their results
		for (int i = 0; i < 80; i++) {
			exampleCollection.add(new Example(boardCollection.get(i),
					resultCollection.get(i)));
		}

		for (int i = 0; i < 20; i++) {
			submissionExamples.add(new Example(submissionBoards.get(i),
					sbumissionResults.get(i)));
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
			double maxAttributeValue = 0.0;
			for (Attribute a : attributes) {
				double importanceValue = a.getImportance(examples);
				if (importanceValue >= maxAttributeValue) {
					mostImportantAttribute = a;
					maxAttributeValue = importanceValue;
				}
			}

			if (maxAttributeValue == 0) {
				return new DecisionTreeNode(pluralityValue(examples));
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

	private List<Example> getTrainingSet(int slot) {
		List<Example> trainingSet = new ArrayList<Example>();
		int begin = slot * 20;
		int end = begin + 20;
		for (int i = 0; i < begin; i++) {
			trainingSet.add(exampleCollection.get(i));
		}

		for (int i = end; i < 80; i++) {
			trainingSet.add(exampleCollection.get(i));
		}

		return trainingSet;
	}

	private List<Example> getValidationSet(int slot) {
		List<Example> validationSet = new ArrayList<Example>();
		int begin = slot * 20;
		int end = begin + 20;
		for (int i = begin; i < end; i++) {
			validationSet.add(exampleCollection.get(i));
		}
		return validationSet;
	}

	private int[] getOutComes(DecisionTreeNode root, List<Example> validationSet) {
		int[] outComes = new int[20];
		for (int i = 0; i < 20; i++) {
			outComes[i] = root.predictOutcome(validationSet.get(i));
		}
		return outComes;
	}

	private double getPercentage(int[] outComes, List<Example> validationSet) {
		double correctCount = 0;
		double incorrectCount = 0;

		for (int i = 0; i < 20; i++) {
			if (outComes[i] == validationSet.get(i).getWinner()) {
				correctCount++;
			} else {
				incorrectCount++;
			}
		}
		return correctCount / (incorrectCount + correctCount) * 100;
	}

	private double getCrossValidationPercentage(List<Attribute> attributeList) {
		List<Example> trainingSet;
		List<Example> validationSet;
		DecisionTreeNode root;
		int[] outcomes;
		double percentage;
		double totalPercentage = 0;

		for (int i = 0; i < 4; i++) {
			trainingSet = getTrainingSet(i);
			validationSet = getValidationSet(i);
			root = decisionTreeLearning(trainingSet, attributeList, trainingSet);
			outcomes = getOutComes(root, validationSet);
			percentage = getPercentage(outcomes, validationSet);
			totalPercentage += percentage;
		}
		return totalPercentage / 4;
	}

	private void printResultToCSV(int[] outcomes) {
		FileReader fr = FileReader.getInstance();
		try {
			fr.writeCSVFile("C:/AIproj3/bli_tnarayan_project3.csv", outcomes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Main function to run the program and to generate predictions
	 * 
	 */
	public static void main(String[] args) {
		DecisionTree dTree = new DecisionTree();
		dTree.setup();

		List<Attribute> attributeList = new ArrayList<Attribute>();

		attributeList.add(new IsHorizontalUnboundedThreeInARowWithOpponent());
		attributeList.add(new IsVerticalUnboundedThreeInARow());
		attributeList.add(new IsHorizontalUnboundedTwoInARowWithOpponent());
		// attributeList.add(new IsDominatingCenterOfBoard());
		attributeList.add(new IsDiagonalLeftThreeInARow());
		// attributeList.add(new IsDiagonalRightThreeInARow());
		attributeList.add(new IsPlayerOneMoveFirst());

		DecisionTreeNode root = dTree
				.decisionTreeLearning(dTree.exampleCollection, attributeList,
						dTree.exampleCollection);

		int[] outcomes = dTree.getOutComes(root, dTree.submissionExamples);

		dTree.printResultToCSV(outcomes);

		dTree.getCrossValidationPercentage(attributeList);
	}
}
