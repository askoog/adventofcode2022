package se.askware.aoc2021.dec07;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		int[] vals = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

		int max = Arrays.stream(vals).max().orElse(0);
		int minCost = Integer.MAX_VALUE;
		for (int i = 0; i < max; i++) {
			int curIndex = i;
			int cost = Arrays.stream(vals).map(v -> Math.abs(v - curIndex)).sum();
			minCost = Math.min(minCost, cost);
		}
		System.out.println(minCost);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		int[] vals = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

		int max = Arrays.stream(vals).max().orElse(0);
		int minCost = Integer.MAX_VALUE;
		for (int i = 0; i < max; i++) {
			int curIndex = i;
			int cost = Arrays.stream(vals)
					.map(v -> Math.abs(v - curIndex))
					.map(v -> IntStream.range(1, v + 1).sum())
					.sum();
			minCost = Math.min(minCost, cost);
		}
		System.out.println(minCost);
	}

}
