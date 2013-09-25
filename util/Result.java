package util;

/*******************************************************************************
 * This files was developed for CS4341: Artificial Intelligence. The course was
 * taken at Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

public class Result {
	public int gameNumber;
	public int currentTurn;
	public int winner;

	public Result(int gameNumber, int currentTurn, int winner) {
		this.gameNumber = gameNumber;
		this.currentTurn = currentTurn;
		this.winner = winner;
	}

	public Result(String resultString) {
		String[] resultArrs = resultString.split(",");
		this.gameNumber = Integer.parseInt(resultArrs[0]);
		this.currentTurn = Integer.parseInt(resultArrs[1]);
		if (resultArrs.length > 2) {
			this.winner = Integer.parseInt(resultArrs[2]);
		} else {
			this.winner = 0;
		}
	}

	public void printResult() {
		System.out.println(gameNumber + ", " + currentTurn + ", " + winner);
	}
}
