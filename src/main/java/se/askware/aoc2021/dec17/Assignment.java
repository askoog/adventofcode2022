package se.askware.aoc2021.dec17;

import java.io.IOException;
import java.util.ArrayList;
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
		for (String string : input) {
			Pattern p = Pattern.compile("x=(-?[0-9]+)\\.\\.(-?[0-9]+),.*y=(-?[0-9]+)\\.\\.(-?[0-9]+)");
			Matcher matcher = p.matcher(string);
			matcher.find();
			int txmin = Integer.parseInt(matcher.group(1));
			int txmax = Integer.parseInt(matcher.group(2));
			int tymin = Integer.parseInt(matcher.group(3));
			int tymax = Integer.parseInt(matcher.group(4));
			/*
			 * 
			 * The probe's x position increases by its x velocity.
			The probe's y position increases by its y velocity.
			
			Due to drag, the probe's x velocity changes by 1 toward the value 0; that is, 
			it decreases by 1 if it is greater than 0, increases by 1 if it is less than 0, 
			or does not change if it is already 0.
			
			Due to gravity, the probe's y velocity decreases by 1.
			 */

			int[] best = new int[] { 0, 0, 0 };
			List<int[]> good = new ArrayList<>();

			for (int xinitialvelocity = -1000; xinitialvelocity <= txmax; xinitialvelocity++) {
				for (int yinitialvelocity = tymin; yinitialvelocity < 1000; yinitialvelocity++) {
					//System.out.println("begin " + xinitialvelocity + "," + yinitialvelocity);
					int x = 0;
					int y = 0;
					int xvelocity = xinitialvelocity;
					int yvelocity = yinitialvelocity;
					int curymax = y;
					boolean oob = false;
					while (!oob) {

						x += xvelocity;
						y += yvelocity;
						xvelocity = Math.max(0, xvelocity - 1);
						yvelocity--;
						curymax = Math.max(curymax, y);
						//System.out.println(x + "," + y);
						if (txmin <= x && txmax >= x && tymin <= y && tymax >= y) {
							//							System.out.println("IN target area " + x + "," + y + " : " + xinitialvelocity + ","
							//									+ yinitialvelocity);
							if (curymax > best[2]) {
								best[0] = xinitialvelocity;
								best[1] = yinitialvelocity;
								best[2] = curymax;
							}
							good.add(new int[] { xinitialvelocity, yinitialvelocity, curymax });
							oob = true;

						}
						if (x > txmax || y < tymin) {
							//System.out.println("Out of bounds");
							oob = true;
						}
					}
				}
			}
			System.out.println(best[0] + "," + best[1] + " -> " + best[2]);
			System.out.println(good.size());
		}
	}

	@Override
	public void solvePartTwo(List<String> input) {
	}

}
