package se.askware.aoc2021.dec04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		int[] numbers = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
		List<Panel> panels = buildPanels(input);

		for (int i = 0; i < numbers.length; i++) {
			int num = numbers[i];
			for (Panel panel : panels) {
				panel.mark(num);
				if (panel.hasBingo()) {
					System.out.println(i);
					System.out.println(panel.getScore(num));
					return;
				}
			}
		}
	}

	private List<Panel> buildPanels(List<String> input) {
		List<Panel> panels = new ArrayList<>();
		Panel p = null;
		int rowNum = 0;
		for (int i = 1; i < input.size(); i++) {
			String row = input.get(i);
			if (row.trim().isEmpty()) {
				p = new Panel();
				panels.add(p);
				rowNum = 0;
			} else {
				//System.out.println(row);
				int[] rowNumbers = Arrays.stream(row.trim().replace("  ", " ").split(" "))
						.mapToInt(Integer::parseInt)
						.toArray();
				p.field[rowNum] = rowNumbers;
				rowNum++;
			}
		}
		return panels;
	}

	@Override
	public void solvePartTwo(List<String> input) {
		int[] numbers = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
		List<Panel> panels = buildPanels(input);

		Panel p = null;
		for (int i = 0; i < numbers.length; i++) {
			int num = numbers[i];
			for (Panel panel : new ArrayList<>(panels)) {
				panel.mark(num);
				if (panel.hasBingo()) {
					p = panel;
					panels.remove(p);
					if (panels.isEmpty()) {
						System.out.println(p.getScore(num));

					}
				}
			}
		}
	}

	private class Panel {
		int[][] field = new int[5][5];
		boolean[][] marked = new boolean[5][5];

		void mark(int number) {
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					if (field[i][j] == number) {
						marked[i][j] = true;
					}
				}
			}
		}

		boolean hasBingo() {
			for (int i = 0; i < field.length; i++) {
				boolean bingo = true;
				for (int j = 0; j < field[i].length; j++) {
					if (!marked[i][j]) {
						bingo = false;
					}
				}
				if (bingo) {
					return true;
				}
			}

			for (int i = 0; i < field.length; i++) {
				boolean bingo = true;
				for (int j = 0; j < field[i].length; j++) {
					if (!marked[j][i]) {
						bingo = false;
					}
				}
				if (bingo) {
					return true;
				}
			}

			return false;
		}

		int getScore(int calledNumber) {
			int score = 0;
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					if (!marked[i][j]) {
						score += field[i][j];
					}
				}
			}
			System.out.println(score);
			System.out.println(calledNumber);
			return score * calledNumber;
		}
	}

}
