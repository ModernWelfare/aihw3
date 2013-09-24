package util;

public class Example {
	private final Board board;
	private final Result result;

	public Example(Board board, Result result) {
		this.board = board;
		this.result = result;
	}

	public Board getBoard() {
		return board;
	}

	public Result getResult() {
		return result;
	}

	public int getWinner() {
		return result.winner;
	}

	public int getFirstMover() {
		return result.currentTurn;
	}
}
