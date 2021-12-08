package se.askware.aoc2021.dec06;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		solve(input, 80);
	}

	private void solve(List<String> input, int numIterations) {
		long[] currentState = new long[9];
		String string = input.get(0);
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) != ',') {
				int val = string.charAt(i) - '0';
				//System.out.println(val);
				currentState[val]++;
			}
		}

		for (int i = 0; i < numIterations; i++) {
			long[] newState = new long[9];
			for (int j = 1; j < newState.length; j++) {
				newState[j - 1] = currentState[j];
			}
			newState[6] += currentState[0];
			newState[8] = currentState[0];
			currentState = newState;
			//System.out.println(Arrays.stream(currentState).sum());
		}

		System.out.println(Arrays.stream(currentState).sum());
	}

	@Override
	public void solvePartTwo(List<String> input) {
		solve(input, 256);
	}

}
