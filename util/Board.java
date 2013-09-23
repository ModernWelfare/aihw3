package util;

/*******************************************************************************
 * This files was developed for CS4341: Artificial Intelligence.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

import java.util.Arrays;
import java.util.List;

public class Board {
	public final int boardArray[][];
	public final int height;
	public final int width;

	public Board(int height, int width) {
		this.height = height;
		this.width = width;
		this.boardArray = new int[height][width];
	}

	public Board(List<String> boardInfo) {
		this.height = 6;
		this.width = 7;
		this.boardArray = new int[height][width];

		int rowNumber = 0;
		for (String row : boardInfo) {
			String[] digits = row.split(",");
			for (int i = 0; i < digits.length; i++) {
				boardArray[rowNumber][i] = Integer.parseInt(digits[i]);
			}
			rowNumber++;
		}
	}

	public void printBoard() {
		for (int i = 0; i < height; i++) {
			System.out.println(Arrays.toString(boardArray[i]) + "\n");
		}
	}
}
