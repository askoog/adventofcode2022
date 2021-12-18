package se.askware.aoc2021.dec13;

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
		List<int[]> coordinates = new ArrayList<>();
		int xmax = 0;
		int ymax = 0;
		List<String> folds = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			String line = input.get(i);
			if (line.contains(",")) {
				String[] split = line.split(",");
				int[] ints = new int[] { Integer.parseInt(split[0]), Integer.parseInt(split[1]) };
				xmax = Math.max(xmax, ints[0]);
				ymax = Math.max(ymax, ints[1]);
				coordinates.add(ints);
			}
			if (line.contains("fold")) {
				folds.add(line);
			}
		}
		boolean[][] grid = new boolean[ymax + 1][xmax + 1];
		for (int[] is : coordinates) {
			grid[is[1]][is[0]] = true;
		}

		printGrid(grid);

		for (String string : folds) {
			int foldLIne = Integer.parseInt(string.substring(string.indexOf("=") + 1));
			if (string.contains("x=")) {
				grid = foldX(foldLIne, grid);
			} else {
				grid = foldY(foldLIne, grid);
			}
			System.out.println();
			printGrid(grid);
			System.out.println(overlapping(grid));
		}

		System.out.println(overlapping(grid));
	}

	private int overlapping(boolean[][] grid) {
		int sum = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j]) {
					sum++;
				}
			}
		}
		return sum;
	}

	private void printGrid(boolean[][] grid) {
		if (grid.length < 100) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					System.out.print(grid[i][j] ? "#" : ".");
				}
				System.out.println();
			}
		}
	}

	private boolean[][] foldY(int foldLIne, boolean[][] grid) {
		boolean[][] result = new boolean[foldLIne][grid[0].length];
		for (int y = 0; y < result.length; y++) {
			int y1 = y;
			int y2 = grid.length - y - 1;
			if (grid.length < 100) {
				System.out.println();
				System.out.print("1: " + (y1) + " ");
				for (int j = 0; j < grid[y].length; j++) {
					System.out.print(grid[y1][j] ? "#" : ".");
				}
				System.out.println();
				System.out.print("2: " + (y2) + " ");
				for (int j = 0; j < grid[y].length; j++) {
					System.out.print(grid[y2][j] ? "#" : ".");
				}
				System.out.println();
			}
			for (int x = 0; x < result[y].length; x++) {

				result[y][x] = grid[y1][x] || grid[y2][x];
			}
		}
		return result;
	}

	private boolean[][] foldX(int foldLIne, boolean[][] grid) {
		boolean[][] result = new boolean[grid.length][foldLIne];

		for (int x = 0; x < result[0].length; x++) {
			int x1 = x;
			int x2 = grid[0].length - x - 1;
			for (int y = 0; y < result.length; y++) {
				result[y][foldLIne - x - 1] = grid[y][x1] || grid[y][x2];
			}
			if (grid.length < 100) {
				System.out.println();
				System.out.print("1: " + (x1) + " ");
				for (int j = 0; j < grid.length; j++) {
					System.out.print(grid[j][x1] ? "#" : ".");
				}
				System.out.println();
				System.out.print("2: " + (x2) + " ");
				for (int j = 0; j < grid.length; j++) {
					System.out.print(grid[j][x2] ? "#" : ".");
				}
				System.out.println();

				System.out.print("r: " + (foldLIne - x - 1) + " ");
				for (int j = 0; j < grid.length; j++) {
					System.out.print(result[j][foldLIne - x - 1] ? "#" : ".");
				}
				System.out.println();
			}
		}
		return result;
	}

	@Override
	public void solvePartTwo(List<String> input) {
	}

}
