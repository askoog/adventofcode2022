package se.askware.aoc2021.dec10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		int sum = 0;
		for (String string : input) {
			Stack<Character> stack = new Stack<>();
			char[] charArray = string.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if (c == '(' || c == '[' || c == '{' || c == '<') {
					stack.push(Character.valueOf(c));
				} else {
					char top = stack.pop();

					if ((c == ')' && top == '(') ||
							(c == ']' && top == '[') ||
							(c == '}' && top == '{') ||
							(c == '>' && top == '<')) {

					} else {
						System.out.println(string);
						System.out.println(c);
						if (c == ')') {
							sum += 3;
						} else if (c == ']') {
							sum += 57;
						} else if (c == '}') {
							sum += 1197;
						} else if (c == '>') {
							sum += 25137;
						}
						break;
					}
				}

			}
		}
		System.out.println(sum);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		List<Long> sum = new ArrayList<>();
		for (String string : input) {
			Stack<Character> stack = new Stack<>();
			char[] charArray = string.toCharArray();
			boolean illegal = false;
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if (c == '(' || c == '[' || c == '{' || c == '<') {
					stack.push(Character.valueOf(c));
				} else {
					char top = stack.pop();
					if ((c == ')' && top == '(') ||
							(c == ']' && top == '[') ||
							(c == '}' && top == '{') ||
							(c == '>' && top == '<')) {

					} else {
						illegal = true;
						break;
					}
				}
			}
			if (!illegal) {
				String s = "";
				long score = 0;
				while (!stack.isEmpty()) {
					char c = stack.pop();
					score *= 5;
					if (c == '(') {
						s += ")";
						score += 1;
					} else if (c == '[') {
						s += "]";
						score += 2;
					} else if (c == '{') {
						s += "}";
						score += 3;
					} else if (c == '<') {
						s += ">";
						score += 4;
					}
				}
				System.out.println(s);
				System.out.println(score);
				sum.add(score);
			}
		}
		Collections.sort(sum);
		System.out.println(sum.get(sum.size() / 2));
	}

}
