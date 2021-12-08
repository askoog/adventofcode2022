package se.askware.aoc2021.dec03;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		StringBuilder gammaString = new StringBuilder();
		StringBuilder epsilonString = new StringBuilder();
		int max = input.get(0).length();
		for (int i = 0; i < max; i++) {
			int[] gamma = new int[2];
			int[] epsilon = new int[2];
			for (String string : input) {
				int c = string.charAt(i) - '0';
				gamma[c]++;
				epsilon[c]++;
			}
			gammaString.append(gamma[0] > gamma[1] ? '0' : '1');
			epsilonString.append(epsilon[0] < epsilon[1] ? '0' : '1');
		}
		System.out.println(gammaString);
		System.out.println(epsilonString);
		int g = Integer.parseInt(gammaString.toString(), 2);
		int e = Integer.parseInt(epsilonString.toString(), 2);
		System.out.println(g);
		System.out.println(e);
		System.out.println(g * e);
	}

	private int mostCommonBit(List<String> input, int bit) {
		int bits[] = new int[2];
		for (String string : input) {
			int c = string.charAt(bit) - '0';
			bits[c]++;
		}
		return bits[0] > bits[1] ? 0 : 1;
	}

	@Override
	public void solvePartTwo(List<String> input) {

		List<String> oxygen = new ArrayList<>(input);
		int max = input.get(0).length();
		for (int i = 0; i < max; i++) {
			int mostCommon = mostCommonBit(oxygen, i);
			int currentIndex = i;
			oxygen = oxygen.stream().filter(c -> c.charAt(currentIndex) - '0' == mostCommon).collect(toList());
		}
		System.out.println(oxygen);

		List<String> co2rating = new ArrayList<>(input);
		for (int i = 0; i < max && co2rating.size() > 1; i++) {
			System.out.println(co2rating);
			int mostCommon = mostCommonBit(co2rating, i);
			int currentIndex = i;
			co2rating = co2rating.stream().filter(c -> (c.charAt(currentIndex) - '0') != mostCommon).collect(toList());
		}
		System.out.println(co2rating);

		int g = Integer.parseInt(oxygen.get(0), 2);
		int e = Integer.parseInt(co2rating.get(0), 2);
		System.out.println(g);
		System.out.println(e);
		System.out.println(g * e);

	}

}
