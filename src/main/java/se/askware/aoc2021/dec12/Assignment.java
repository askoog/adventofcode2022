package se.askware.aoc2021.dec12;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		Queue<Path> paths = parseInput(input);
		solve(paths, false);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		Queue<Path> paths = parseInput(input);
		solve(paths, true);
	}

	private void solve(Queue<Path> paths, boolean allowSingleTwice) {
		List<Path> completePaths = new ArrayList<>();
		while (!paths.isEmpty()) {
			Path current = paths.poll();
			if (current.top().id.equals("end")) {
				completePaths.add(current);
			} else {
				for (Cave n : current.top().neighbors) {
					if (current.canAddCave(n, allowSingleTwice)) {
						paths.add(current.withCave(n));
					}
				}
			}
		}
		System.out.println(completePaths.size());
	}

	private Queue<Path> parseInput(List<String> input) {
		Map<String, Cave> caves = new HashMap<>();
		for (String string : input) {
			String[] split = string.split("-");
			Cave c1 = caves.computeIfAbsent(split[0], s -> new Cave(s));
			Cave c2 = caves.computeIfAbsent(split[1], s -> new Cave(s));
			c1.addNeighbor(c2);
		}
		Path p = new Path().withCave(caves.get("start"));
		Queue<Path> paths = new ArrayDeque<>();
		paths.add(p);
		return paths;
	}

	private static class Path {
		List<Cave> paths = new ArrayList<>();
		boolean singleCellVisitedTwice = false;

		boolean canAddCave(Cave c, boolean allowSingleTwice) {
			if (c.isLarge() || !paths.contains(c)) {
				return true;
			}
			if (!c.id.equals("start") && !c.id.equals("end") && allowSingleTwice && !singleCellVisitedTwice) {
				return true;
			}
			return false;
		}

		Cave top() {
			return paths.get(paths.size() - 1);
		}

		Path withCave(Cave c) {
			Path p = new Path();
			p.paths.addAll(paths);
			p.singleCellVisitedTwice = singleCellVisitedTwice;
			if (!c.isLarge() && p.paths.contains(c)) {
				p.singleCellVisitedTwice = true;
			}
			p.paths.add(c);

			return p;
		}

		void print() {
			System.out.println(toString());
		}

		@Override
		public String toString() {
			return paths.stream().map(c -> c.id).collect(joining(","));
		}
	}

	private static class Cave {
		String id;
		Set<Cave> neighbors = new HashSet<>();

		public Cave(String id) {
			super();
			this.id = id;
		}

		void addNeighbor(Cave c) {
			neighbors.add(c);
			c.neighbors.add(this);
		}

		public boolean isLarge() {
			return Character.isUpperCase(id.charAt(0));
		}

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cave other = (Cave) obj;
			return Objects.equals(id, other.id);
		}

		@Override
		public String toString() {
			return id;
		}
	}
}
