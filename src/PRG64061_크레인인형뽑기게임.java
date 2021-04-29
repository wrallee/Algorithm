import java.util.Stack;

class PRG64061_크레인인형뽑기게임 {
	public int solution(int[][] board, int[] moves) {
		Stack<Integer> stack = new Stack<>();
		int popped = 0;

		for (int column : moves) {
			int doll = pickDoll(board, column - 1);
			popped += stackDoll(stack, doll);
		}

		return popped;
	}

	private int stackDoll(Stack<Integer> stack, int doll) {
		if (doll == -1) {
			return 0;
		}

		if (stack.isEmpty() || stack.peek() != doll) {
			stack.push(doll);
			return 0;
		}

		stack.pop();
		return 2;
	}

	private int pickDoll(int[][] board, int column) {
		for (int[] row : board) {
			int doll = row[column];
			if (doll != 0) {
				row[column] = 0;
				return doll;
			}
		}

		return -1;
	}
}