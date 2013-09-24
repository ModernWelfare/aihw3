package Attributes;

import java.util.ArrayList;
import java.util.List;

import util.Example;
import AttributeValues.Affirmative;
import AttributeValues.AttributeValue;
import AttributeValues.Negative;

/**
 * Attribute for checking the right diagonal
 * 
 * @author bli tnarayan
 * 
 */
public class IsDiagonalRightThreeInARow extends AttributeImpl {

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
		//int opponentToken = 3 - playerToken;

		int numTokensCountingFor = 3; // checking for 3 in a row

		int playerCounts = countRightDiagonal(e, playerToken, numTokensCountingFor);
		//int opponentCounts = countRightDiagonal(e, opponentToken, numTokensCountingFor);
		
		if(playerCounts >= numTokensCountingFor){
			return affirm;
		} else {
			return nega;
		}
	}
	
	public int countRightDiagonal(Example e, int currentToken, int numTokensCountingFor){
		int height = e.getBoard().height; // 6
		int width = e.getBoard().width; // 7
		
		int countOfTokensEncounteredDiagonallyRight = 0; // counter
		int countOfCurrentPlayersNInARow = 0;

		// bottom index is (5, 0)
		for (int i = height - 1; i > -1; i--) {
			for (int j = 0; j < width; j++) {
				if (e.getBoard().boardArray[i][j] == currentToken) { // current
																	// player's
																	// tokens
					for (int k = i, l = j; k > -1; k--, l++) {
						if(l == width){ // check to make sure not out of bounds
							break;
						}
						else if (e.getBoard().boardArray[i][j] == e.getBoard().boardArray[k][l]) {
							countOfTokensEncounteredDiagonallyRight++;
						} else { // since the continuity between tokens is
								// broken, stop
							break;
						}
					}
				}
				if(countOfTokensEncounteredDiagonallyRight >= numTokensCountingFor){
					countOfCurrentPlayersNInARow++;
					countOfTokensEncounteredDiagonallyRight = 0;
				}
			}
		}

		return countOfCurrentPlayersNInARow;
	}
}
