package Attributes;

import java.util.List;

import AttributeValues.AttributeValue;

import util.Example;

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
	 * @param exampleCollection
	 * @return
	 */
	public int getImportance(List<Example> exampleCollection);

	/**
	 * get the value of the attribute in this example
	 * 
	 * @return
	 */
	public AttributeValue getExampleAttributeValue(Example e);

}
