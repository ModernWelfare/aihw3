package Attributes;

import java.util.ArrayList;
import java.util.List;

import util.Example;
import AttributeValues.Affirmative;
import AttributeValues.AttributeValue;
import AttributeValues.Negative;

/**
 * Attribute for checking the center of mass of the board
 * 
 * @author bli tnarayan
 * 
 */
public class IsDominatingCenterOfBoard extends AttributeImpl {

	private final AttributeValue affirm = new Affirmative();
	private final AttributeValue nega = new Negative();
	
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
		int opponentToken = 3 - playerToken;
		
		int height = e.getBoard().height; // 6
		int width = e.getBoard().width; // 7
		int center = width / 2;
		
		int playerScore = 0;
		int opponentScore = 0;

		// bottom index is (5, 0)
		for (int i = height - 1; i > -1; i--) {
			for (int j = 0; j < width; j++) {
				if (e.getBoard().boardArray[i][j] == playerToken) { // current
																	// player's
																	// tokens
					playerScore += center - Math.abs(j - center);
				} else if(e.getBoard().boardArray[i][j] == opponentToken){
					opponentScore += center - Math.abs(j - center);
				}
			}
		}

		if (playerScore >= opponentScore) {
			return affirm;
		} else {
			return nega;
		}
	}
}
