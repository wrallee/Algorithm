import java.util.Stack;

class LTC155_MinStack {

	private final Stack<Integer> stack;

	public LTC155_MinStack() {
		stack = new Stack<>();
	}

	public void push(int val) {
		stack.push(val);
	}

	public void pop() {
		stack.pop();
	}

	public int top() {
		return stack.peek();
	}

	public int getMin() {
		return stack.stream()
			.min(Integer::compareTo)
			.orElse(-1);
	}
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */