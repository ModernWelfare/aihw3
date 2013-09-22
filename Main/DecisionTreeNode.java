package Main;

import java.util.ArrayList;
import java.util.List;

import AttributeValues.AttributeValue;
import Attributes.Attribute;

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

	public void printTree() {
		System.out.println(outcome);

		if (!children.isEmpty()) {
			for (DecisionTreeNode child : children) {
				child.printTree();
			}
		}
	}
}
