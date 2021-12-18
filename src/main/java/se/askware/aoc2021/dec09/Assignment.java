package se.askware.aoc2021.dec09;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		Grid g = new Grid();
		g.grid = new int[input.size()][input.get(0).length()];
		g.lowParts = new boolean[input.size()][input.get(0).length()];
		for (int i = 0; i < input.size(); i++) {
			g.grid[i] = input.get(i).chars().map(c -> c - '0').toArray();

		}
		g.findLowPArts();
		g.print();
		System.out.println(g.getRiskLEvelSum());
	}

	@Override
	public void solvePartTwo(List<String> input) {
		Grid g = new Grid();
		g.grid = new int[input.size()][input.get(0).length()];
		g.lowParts = new boolean[input.size()][input.get(0).length()];
		for (int i = 0; i < input.size(); i++) {
			g.grid[i] = input.get(i).chars().map(c -> c - '0').toArray();

		}
		g.findLowPArts();
		//g.print();
		g.calculateBasin();
		System.out.println(g.getRiskLEvelSum());
	}

	public static class Grid {
		int[][] grid;
		boolean[][] lowParts;

		void findLowPArts() {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					boolean low = true;
					if (i > 0 && grid[i - 1][j] <= grid[i][j]) {
						low = false;
					}
					if (i < grid.length - 1 && grid[i + 1][j] <= grid[i][j]) {
						low = false;
					}
					if (j > 0 && grid[i][j - 1] <= grid[i][j]) {
						low = false;
					}
					if (j < grid[i].length - 1 && grid[i][j + 1] <= grid[i][j]) {
						low = false;
					}
					lowParts[i][j] = low;
					if (low) {
						//System.out.println(i + " " + j);
					}
				}
			}
		}

		long getRiskLEvelSum() {
			long sum = 0;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (lowParts[i][j]) {
						sum += grid[i][j] + 1;
					}
				}
			}
			return sum;
		}

		void print() {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					System.out.print(grid[i][j]);
					if (lowParts[i][j]) {
						System.out.print("*");
					} else {
						System.out.print(" ");
					}
				}
				System.out.println();
			}
		}

		boolean[][] basinCounted;
		int[][] basinSize;

		void calculateBasin() {
			basinCounted = new boolean[grid.length][grid[0].length];
			basinSize = new int[grid.length][grid[0].length];
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (!basinCounted[i][j]) {
						basinSize[i][j] = calculateBasin(i, j);
					}
				}
			}
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					System.out.printf("%-3d", basinSize[i][j]);
				}
				System.out.println();
			}

			List<Integer> basins = new ArrayList<>();
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (basinSize[i][j] > 0) {
						basins.add(basinSize[i][j]);
					}
				}
			}
			int sum = basins.stream().sorted(Comparator.reverseOrder()).mapToInt(i -> i.intValue()).limit(3).reduce(1,
					(i1, i2) -> i1 * i2);
			System.out.println(sum);
		}

		int calculateBasin(int i, int j) {
			if (!basinCounted[i][j]) {
				basinCounted[i][j] = true;
			} else {
				return 0;
			}
			if (grid[i][j] == 9) {
				return 0;
			}
			int sum = 1;
			if (i > 0) {
				sum += calculateBasin(i - 1, j);
			}
			if (i < grid.length - 1) {
				sum += calculateBasin(i + 1, j);
			}
			if (j > 0) {
				sum += calculateBasin(i, j - 1);
			}
			if (j < grid[i].length - 1) {
				sum += calculateBasin(i, j + 1);
			}
			return sum;
		}

	}
}
