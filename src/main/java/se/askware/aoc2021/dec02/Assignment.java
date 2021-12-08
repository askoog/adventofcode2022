package se.askware.aoc2021.dec02;

import java.io.IOException;
import java.util.List;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		List<String[]> commands = convertInput(input, s -> s.split(" "));
		int x = 0;
		int y = 0;
		for (String[] command : commands) {
			int value = Integer.parseInt(command[1]);
			if (command[0].equals("forward")) {
				x += value;
			} else if (command[0].equals("down")) {
				y += value;
			} else if (command[0].equals("up")) {
				y -= value;
			}
		}
		System.out.println(x * y);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		List<String[]> commands = convertInput(input, s -> s.split(" "));
		int x = 0;
		int y = 0;
		int aim = 0;
		for (String[] command : commands) {
			int value = Integer.parseInt(command[1]);
			if (command[0].equals("forward")) {
				x += value;
				y += (aim * value);
			} else if (command[0].equals("down")) {
				aim += value;
			} else if (command[0].equals("up")) {
				aim -= value;
			}
		}
		System.out.println(x * y);
	}

}
