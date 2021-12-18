package se.askware.aoc2021.dec11;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import se.askware.aoc2021.common.AocBase;
import se.askware.aoc2021.common.Cell;
import se.askware.aoc2021.common.Grid;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		Grid<Octopus> grid = setupGrid(input);
		int sum = 0;
		for (int i = 0; i < 100; i++) {
			doTurn(grid);
			sum += grid.getAll().mapToInt(o -> o.resetFlash()).sum();
		}
		System.out.println(sum);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		Grid<Octopus> grid = setupGrid(input);
		for (int i = 0; i < Integer.MAX_VALUE; i++) {

			doTurn(grid);

			if (grid.getAll().mapToInt(o -> o.resetFlash()).sum() == 100) {
				System.out.println((i + 1));
				return;
			}

		}
	}

	private void doTurn(Grid<Octopus> grid) {
		grid.getAll().forEach(o -> o.increase());

		Queue<Octopus> toFlash = new ArrayDeque<>(
				grid.getAll().filter(o -> o.value > 9).collect(toList()));

		while (!toFlash.isEmpty()) {
			Octopus o = toFlash.poll();
			if (!o.flashed) {
				o.flashed = true;
				grid.getAllNeighbors(o).stream()
						.peek(n -> n.increase())
						.filter(c -> c.value > 9 && !c.flashed).forEach(toFlash::add);
			}
		}
	}

	private Grid<Octopus> setupGrid(List<String> input) {
		Grid<Octopus> grid = new Grid<>(10, 10);
		int y = 0;
		for (String string : input) {
			for (int i = 0; i < string.toCharArray().length; i++) {
				char value = string.charAt(i);
				grid.setCell(new Octopus(value - '0'), y, i);
			}
			y++;
		}
		return grid;
	}


	private static class Octopus extends Cell {
		int value;
		boolean flashed = false;

		public Octopus(int value) {
			this.value = value;
		}


		public void increase() {
			value++;
		}

		public int resetFlash() {
			if (flashed) {
				value = 0;
				flashed = false;
				return 1;
			}
			return 0;
		}

		@Override
		public String print() {
			return "" + value;
		}
	}
}
