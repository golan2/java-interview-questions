package interview.leetcode.strings;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/longest-valid-parentheses/
 *
 * Given a string containing just the characters '(' and ')', return the length of the longest valid (well-formed) parentheses substring
 */
@SuppressWarnings("JavadocLinkAsPlainText")
public class LongestValidParentheses {
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Map<Integer, Integer> sequence = new HashMap<>();

    public static void main(String[] args) {
        final LongestValidParentheses a = new LongestValidParentheses();
        System.out.println(a.longestValidParentheses("( (()) ())))))) ((()))((()))((()))  "));
        System.out.println(a.longestValidParentheses("()(()"));
        System.out.println(a.longestValidParentheses("(()"));
    }
// (()()(()())
    public int longestValidParentheses(String s) {
        int max = 0;

        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            }
            else if (c == ')') {
                if (stack.isEmpty()) {
                    throw new RuntimeException("A");
                }
                else {
                    final Integer index = stack.pop();
                    int current = (i - index + 1);
                    final Integer seq = sequence.get(index - 1);
                    if (seq != null) {
                        current += seq;
                    }
                    if (current > max) {
                        max = current;
                    }
                    sequence.put(i, current);
                }
            }
        }

        return -1 ;//Math.max(length, max) * 2;
    }
}
