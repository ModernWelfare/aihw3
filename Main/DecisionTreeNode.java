package Main;

/*******************************************************************************
 * This files was developed for CS4341: Artificial Intelligence. The course was
 * taken at Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

import java.util.ArrayList;
import java.util.List;

import util.Example;
import AttributeValues.AttributeValue;
import Attributes.Attribute;

/**
 * Class for the nodes inside the decision tree
 * 
 * @author bli tnarayan
 * 
 */
public class DecisionTreeNode {
	public AttributeValue aValue;
	public Attribute rootAttribute;
	public int outcome;
	public List<DecisionTreeNode> children = new ArrayList<DecisionTreeNode>();

	public DecisionTreeNode(AttributeValue aValue, int outCome) {
		this.aValue = aValue;
		this.outcome = outCome;
	}

	public DecisionTreeNode(int pluralityValue) {
		this.outcome = pluralityValue;
		this.aValue = null;
	}

	public DecisionTreeNode(Attribute mostImportantAttribute) {
		this.rootAttribute = mostImportantAttribute;
		this.outcome = 0;
		this.aValue = null;
	}

	public void addChild(DecisionTreeNode child) {
		this.children.add(child);
	}

	public void setAttributeValue(AttributeValue aValue) {
		this.aValue = aValue;
	}

	public int predictOutcome(Example e) {
		if (outcome != 0) {
			return outcome;
		} else {
			for (DecisionTreeNode child : children) {
				if (child.aValue.equals(rootAttribute
						.getExampleAttributeValue(e))) {
					return child.predictOutcome(e);
				}
			}
		}
		// return -1 if the process failed
		return -1;
	}

	public void printTree() {
		if (rootAttribute != null) {
			System.out.println(rootAttribute);
		} else {
			System.out.println(outcome);
		}
		if (!children.isEmpty()) {
			for (DecisionTreeNode child : children) {
				child.printTree();
			}
		}
	}
}
