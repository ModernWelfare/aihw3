package Attributes;

/*******************************************************************************
 * This files was developed for CS4341: Artificial Intelligence. The course was
 * taken at Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

import java.util.List;

import util.Example;
import AttributeValues.AttributeValue;

/**
 * Interface for the attributes used to make decision trees
 * 
 * @author bli tnarayan
 * 
 */
public interface Attribute {

	/**
	 * get the attribute values of this attribute
	 * 
	 * @return
	 */
	public List<AttributeValue> getAttributeValues();

	/**
	 * get the importance of this attribute in the example collection
	 * 
	 * Calculation of the importance is done using the binary entropy method
	 * 
	 * @param exampleCollection
	 * @return double value of the entropy deduction
	 */
	public double getImportance(List<Example> exampleCollection);

	/**
	 * get the value of the attribute in this example
	 * 
	 * @return
	 */
	public AttributeValue getExampleAttributeValue(Example e);

}
