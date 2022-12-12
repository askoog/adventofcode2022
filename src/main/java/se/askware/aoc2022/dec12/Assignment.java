package se.askware.aoc2022.dec12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

import se.askware.aoc2022.common.AocBase;
import se.askware.aoc2022.common.Cell;
import se.askware.aoc2022.common.Grid;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	private static class Pos extends Cell {
		char value;
		boolean seen;

		public Pos(char value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return row + "," + col + " : " + value;
		}

	}

	@Override
	public void solvePartOne(List<String> input) {
		final Grid<Pos> grid = Grid.init(input, (p, c) -> new Pos(c));

		final Pos start = grid.getAll().filter(c -> c.value == 'S').findFirst().get();
		start.value = 'a';
		final Pos end = grid.getAll().filter(c -> c.value == 'E').findFirst().get();
		end.value = 'z';

		final List<Pos> best = grid.findPathXY(start, end, (last, next) -> next.value - last.value <= 1);

		System.out.println(best.size() - 1);

	}

	@Override
	public void solvePartTwo(List<String> input) {
		final Grid<Pos> grid = Grid.init(input, (p, c) -> new Pos(c));

		final Pos start = grid.getAll().filter(c -> c.value == 'S').findFirst().get();
		start.value = 'a';
		final Pos end = grid.getAll().filter(c -> c.value == 'E').findFirst().get();
		end.value = 'z';

		List<Pos> allA = grid.getAll().filter(c -> c.value == 'a').collect(Collectors.toList());

		int minPath = allA.stream()
				.map(a -> grid.findPathXY(a, end, (last, next) -> next.value - last.value <= 1))
				.filter(l -> !l.isEmpty())
				.mapToInt(l -> l.size() - 1)
				.sorted()
				.findFirst().getAsInt();

		System.out.println(minPath);
	}

}
