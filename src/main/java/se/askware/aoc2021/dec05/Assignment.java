package se.askware.aoc2021.dec05;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		solve(input, false);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		solve(input, true);
	}

	private void solve(List<String> input, boolean useDiagonal) {
		Pattern p = Pattern.compile("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)");
		int[][] grid = new int[1000][1000];

		List<int[]> convertInput = convertInput(input, s -> {
			Matcher matcher = p.matcher(s);
			matcher.find();
			return new int[] {
					Integer.parseInt(matcher.group(1)),
					Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(3)),
					Integer.parseInt(matcher.group(4))
			};
		});
		for (int[] is : convertInput) {
			if (is[0] == is[2]) {
				//System.out.println(is[0] + " " + is[1] + " " + is[2] + " " + is[3]);
				for (int i = Math.min(is[1], is[3]); i <= Math.max(is[1], is[3]); i++) {
					grid[is[0]][i]++;
				}
			} else if (is[1] == is[3]) {
				//	System.out.println(is[0] + " " + is[1] + " " + is[2] + " " + is[3]);
				for (int i = Math.min(is[0], is[2]); i <= Math.max(is[0], is[2]); i++) {
					grid[i][is[1]]++;
				}

			} else if (useDiagonal) {
				//	System.out.println(is[0] + " " + is[1] + " " + is[2] + " " + is[3]);
				int factor = 1;
				if (is[1] > is[3]) {
					factor = -1;
				}
				int y = is[1];
				if (is[0] < is[2]) {
					for (int i = is[0]; i <= is[2]; i++) {
						grid[i][y]++;
						y += factor;
					}
				} else {
					for (int i = is[0]; i >= is[2]; i--) {
						grid[i][y]++;
						y += factor;
					}
				}

			}
		}
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] > 1) {
					count++;
				}
			}
		}
		System.out.println(count);
	}


}
