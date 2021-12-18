package se.askware.aoc2021.dec08;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		List<String[]> lines = convertInput(input, s -> s.split("\\|"));
		AtomicInteger count = new AtomicInteger();
		for (String[] line : lines) {
			String[] outputValue = line[1].trim().split(" ");
			Arrays.stream(outputValue).forEach(s -> {
				if (s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7) {
					count.incrementAndGet();
				}
			});
		}
		System.out.println(count.get());

	}

	@Override
	public void solvePartTwo(List<String> input) {
		List<String[]> lines = convertInput(input, s -> s.split("\\|"));
		long total = 0;
		for (String[] line : lines) {
			String[] patterns = line[0].trim().split(" ");
			String[] outputValue = line[1].trim().split(" ");
			String[] segments = new String[7];
			for (int i = 0; i < segments.length; i++) {
				segments[i] = "abcdefg";
			}
			//  aa
			// b  c
			// b  c
			//  dd
			// e  f
			// e  f
			//  gg

			for (int i = 0; i < patterns.length; i++) {
				String digit = patterns[i];
				//System.out.println("*** " + digit);
				if (digit.length() == 2) {
					segments = removeAll(segments, digit, 0, 1, 3, 4, 6);
					segments = retain(segments, digit, 2, 5);
				}
				if (digit.length() == 3) {
					segments = removeAll(segments, digit, 1, 3, 4, 6);
					segments = retain(segments, digit, 0, 2, 5);
				}
				if (digit.length() == 4) {
					segments = removeAll(segments, digit, 0, 4, 6);
					segments = retain(segments, digit, 1, 2, 3, 5);
				}
				if (digit.length() == 5) {
					segments = retain(segments, digit, 0, 3, 6);
				}
				if (digit.length() == 6) {
					segments = retain(segments, digit, 0, 5, 6);
				}
			}
			for (int i = 0; i < segments.length; i++) {
				String s = segments[i];
				if (s.length() == 1) {
					for (int j = 0; j < segments.length; j++) {
						if (i != j) {
							segments = removeAll(segments, s, j);
						}
					}
				}
			}
			for (String string : segments) {
				if (string.length() > 1) {
					throw new RuntimeException();
				}
			}

			String result = "";
			for (int i = 0; i < outputValue.length; i++) {
				int value = toValue(outputValue[i], segments);
				result = result + value;
			}
			//			System.out.println("RESULT");
			//			System.out.println(result);
			total += Integer.parseInt(result);
		}
		System.out.println(total);
	}

	private int toValue(String string, String[] segments) {
		if (string.length() == 7) {
			return 8;
		} else if (string.length() == 2) {
			return 1;
		} else if (string.length() == 3) {
			return 7;
		} else if (string.length() == 4) {
			return 4;
		} else if (string.length() == 5) {
			if (string.contains("" + segments[2]) && string.contains("" + segments[5])) {
				return 3;
			}
			if (string.contains("" + segments[2])) {
				return 2;
			} else {
				return 5;
			}
		} else if (string.length() == 6) {
			if (string.contains("" + segments[2]) && string.contains("" + segments[4])) {
				return 0;
			} else if (string.contains("" + segments[2])) {
				return 9;
			} else {
				return 6;
			}
		}
		return -1;
	}

	private static String[] removeAll(String[] input, String toRemove, int... indices) {
		for (int i = 0; i < indices.length; i++) {
			String s = input[indices[i]];
			for (int j = 0; j < toRemove.length(); j++) {
				s = s.replaceAll("" + toRemove.charAt(j), "");
			}
			//System.out.println(indices[i] + ": " + input[indices[i]] + " - " + toRemove + " -> " + s);
			input[indices[i]] = s;
		}
		return input;
	}

	private static String[] retain(String[] input, String toContain, int... indices) {
		for (int i = 0; i < indices.length; i++) {
			String s = "";
			for (int j = 0; j < toContain.length(); j++) {
				if (input[indices[i]].contains("" + toContain.charAt(j))) {
					s = s + toContain.charAt(j);
				}
			}
			//System.out.println(indices[i] + ": " + input[indices[i]] + " + " + toContain + " -> " + s);
			input[indices[i]] = s;
		}
		return input;
	}
}
