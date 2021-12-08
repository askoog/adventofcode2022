package se.askware.aoc2021.dec01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		List<Integer> numbers = convertInput(input, i -> Integer.parseInt(i));
		solveForNumbers(numbers);
	}

	private void solveForNumbers(List<Integer> numbers) {
		int last = Integer.MAX_VALUE;
		int numIncreases = 0;
		for (Integer current : numbers) {
			if (last < current) {
				numIncreases++;
			}
			last = current;
		}
		System.out.println(numIncreases);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		List<Integer> ints = convertInput(input, i -> Integer.parseInt(i));
		List<Integer> sums = new ArrayList<>();
		for (int i = 1; i < ints.size() -1; i++) {
			sums.add(ints.get(i-1) + ints.get(i) + ints.get(i+1));
		}
		solvePartOne(sums.stream().map(i -> i.toString()).collect(Collectors.toList()));
	}

}
