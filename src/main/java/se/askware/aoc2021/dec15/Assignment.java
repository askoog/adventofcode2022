package se.askware.aoc2021.dec15;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.askware.aoc2021.common.AocBase;
import se.askware.aoc2021.common.Cell;
import se.askware.aoc2021.common.Grid;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		int xmax = input.size();
		int ymax = input.get(0).length();
		Grid<Cave> g = new Grid<>(xmax, ymax);
		g.init((row, col) -> new Cave(input.get(row).charAt(col) - '0'));
		//g.print();

		int[][] bestcell = new int[xmax][ymax];
		for (int i = 0; i < xmax; i++) {
			for (int j = 0; j < ymax; j++) {
				bestcell[i][j] = Integer.MAX_VALUE;
			}
		}
		bestcell[0][0] = 0;
		boolean changed = true;
		while (changed) {
			changed = false;
			for (int i = 0; i < xmax; i++) {
				for (int j = 0; j < ymax; j++) {
					int current = bestcell[i][j];
					if (i < xmax - 1) {
						Cave c = g.getCell(i + 1, j);
						if (bestcell[i + 1][j] > current + c.risk) {
							bestcell[i + 1][j] = current + c.risk;
							changed = true;
						}
					}
					if (i > 0) {
						Cave c = g.getCell(i - 1, j);
						if (bestcell[i - 1][j] > current + c.risk) {
							bestcell[i - 1][j] = current + c.risk;
							changed = true;
						}
					}
					if (j < ymax - 1) {
						Cave c = g.getCell(i, j + 1);
						if (bestcell[i][j + 1] > current + c.risk) {
							bestcell[i][j + 1] = current + c.risk;
							changed = true;
						}
					}
					if (j > 0) {
						Cave c = g.getCell(i, j - 1);
						if (bestcell[i][j - 1] > current + c.risk) {
							bestcell[i][j - 1] = current + c.risk;
							changed = true;
						}
					}
				}

			}
		}

		System.out.println(bestcell[xmax - 1][ymax - 1]);

	}

	@Override
	public void solvePartTwo(List<String> input) {
		int xmax = input.size() * 5;
		int ymax = input.get(0).length() * 5;
		Grid<Cave> g = new Grid<>(xmax, ymax);

		for (int x = 0; x < xmax; x++) {
			for (int y = 0; y < ymax; y++) {
				int xfactor = x / input.size();
				int xindex = x % input.size();
				int yfactor = y / input.size();
				int yindex = y % input.size();
				int val = input.get(xindex).charAt(yindex) - '0';
				int risk = (val + xfactor + yfactor);
				if (risk > 9) {
					risk -= 9;
				}
				g.setCell(new Cave(risk), x, y);
			}
		}
		//g.print();
		int[][] bestcell = new int[xmax][ymax];

		for (int i = 0; i < xmax; i++) {
			for (int j = 0; j < ymax; j++) {
				bestcell[i][j] = Integer.MAX_VALUE;
			}
		}
		bestcell[0][0] = 0;
		boolean changed = true;
		while (changed) {
			changed = false;
			for (int i = 0; i < xmax; i++) {
				for (int j = 0; j < ymax; j++) {
					int current = bestcell[i][j];
					if (i < xmax - 1) {
						Cave c = g.getCell(i + 1, j);
						if (bestcell[i + 1][j] > current + c.risk) {
							bestcell[i + 1][j] = current + c.risk;
							changed = true;
						}
					}
					if (i > 0) {
						Cave c = g.getCell(i - 1, j);
						if (bestcell[i - 1][j] > current + c.risk) {
							bestcell[i - 1][j] = current + c.risk;
							changed = true;
						}
					}
					if (j < ymax - 1) {
						Cave c = g.getCell(i, j + 1);
						if (bestcell[i][j + 1] > current + c.risk) {
							bestcell[i][j + 1] = current + c.risk;
							changed = true;
						}
					}
					if (j > 0) {
						Cave c = g.getCell(i, j - 1);
						if (bestcell[i][j - 1] > current + c.risk) {
							bestcell[i][j - 1] = current + c.risk;
							changed = true;
						}
					}
				}

			}
		}

		System.out.println(bestcell[xmax - 1][ymax - 1]);

	}

	private static class Path {
		List<Cave> caves = new ArrayList<>();
		int risk = 0;

		boolean contains(Cave c) {
			return caves.contains(c);
		}

		public int riskScore() {
			return caves.stream().mapToInt(c -> c.risk).sum() - 1;
		}

		void add(Cave c) {
			caves.add(c);
			risk = riskScore();
		}

		Cave top() {
			return caves.get(caves.size() - 1);
		}

		@Override
		public String toString() {
			return caves.stream().map(cave -> "(" + cave.x + "," + cave.y + "):" + cave.print())
					.collect(joining(" ")) + " " + riskScore();
		}

		@Override
		public int hashCode() {
			return toString().hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			System.out.println(toString().equals(obj.toString()));
			return toString().equals(obj.toString());
		}

	}

	private static class Cave extends Cell {
		int risk = 0;

		public Cave(int risk) {
			super();
			this.risk = risk;
		}

		@Override
		public String print() {
			return "" + risk;
		}

		@Override
		public String toString() {
			return "" + risk;
		}

	}
}
