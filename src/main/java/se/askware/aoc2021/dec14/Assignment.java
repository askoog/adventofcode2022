package se.askware.aoc2021.dec14;

import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		String template = input.get(0);
		Map<String, String> rules = new HashMap<>();
		for (int i = 2; i < input.size(); i++) {
			String[] split = input.get(i).split(" ");
			rules.put(split[0], split[2]);
		}
		System.out.println(rules);

		String current = template;
		int numIterations = 10;
		for (int i = 0; i < numIterations; i++) {
			StringBuilder next = new StringBuilder();
			for (int j = 1; j < current.length(); j++) {
				String sub = current.substring(j - 1, j + 1);
				String rule = rules.get(sub);
				next.append(current.charAt(j - 1));
				if (rule != null) {
					next.append(rule);
				}
				//System.out.println(sub + " " + rule + " " + next);
			}
			System.out.println(i + " " + current.length());
			next.append(current.charAt(current.length() - 1));
			current = next.toString();
			//System.out.println(current);
		}
		Map<Integer, List<Integer>> collect = current.chars().boxed().collect(groupingBy(i -> i));
		Entry<Integer, List<Integer>> min = collect.entrySet().stream()
				.sorted((e1, e2) -> Integer.compare(e1.getValue().size(), e2.getValue().size())).findFirst()
				.orElse(null);
		Entry<Integer, List<Integer>> max = collect.entrySet().stream()
				.sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())).findFirst()
				.orElse(null);
		System.out.println((char) min.getKey().intValue() + " " + min.getValue().size());
		System.out.println((char) max.getKey().intValue() + " " + max.getValue().size());

		System.out.println(max.getValue().size() - min.getValue().size());
	}

	static Map<String, String> rules = new HashMap<>();

	@Override
	public void solvePartTwo(List<String> input) {
		String template = input.get(0);

		Map<String, Long> pairCount = new HashMap<>();

		for (int i = 0; i < template.length() - 1; i++) {
			final Long currentValue = pairCount.getOrDefault(template.substring(i, i + 2), 0L);
			pairCount.put(template.substring(i, i + 2), currentValue + 1);
		}
		for (int i = 2; i < input.size(); i++) {
			String line = input.get(i);
			String[] rule = line.split(" -> ");
			rules.put(rule[0], rule[1]);
		}

		for (int i = 0; i < 40; i++) {
			pairCount = createPolymere(pairCount);
		}

		Map<Character, Long> charCount = new HashMap<>();
		for (String key : pairCount.keySet()) {
			charCount.merge(key.charAt(0), pairCount.get(key), Long::sum);
			charCount.merge(key.charAt(1), pairCount.get(key), Long::sum);
		}
		final List<Map.Entry<Character, Long>> sortedCharCount = new ArrayList<>(charCount.entrySet());
		sortedCharCount.sort(Map.Entry.comparingByValue());
		long min = sortedCharCount.get(0).getValue();
		long max = sortedCharCount.get(sortedCharCount.size() - 1).getValue();
		System.out.println((max - min) / 2);
	}

	private static Map<String, Long> createPolymere(Map<String, Long> pairCount) {
		Map<String, Long> newPairCount = new HashMap<>();
		for (String key : pairCount.keySet()) {
			newPairCount.merge(key.charAt(0) + rules.get(key), pairCount.get(key), Long::sum);
			newPairCount.merge(rules.get(key) + key.charAt(1), pairCount.get(key), Long::sum);
		}
		System.out.println(pairCount);
		System.out.println(newPairCount);
		return newPairCount;
	}

}
