import java.util.Stack;

class LTC20_ValidParentheses {
    public boolean isValid(String s) {
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();

        for (char next : chars) {
            if (next == '}' || next == ']' || next == ')') {
                if (stack.isEmpty() || !isPair(stack.pop(), next)) {
                    return false;
                }
            } else {
                stack.push(next);
            }
        }
        return stack.isEmpty();
    }

    private boolean isPair(char left, char right) {
        if (left == '{' && '}' == right) {
            return true;
        }
        if (left == '[' && ']' == right) {
            return true;
        }
        if (left == '(' && ')' == right) {
            return true;
        }
        return false;
    }
}