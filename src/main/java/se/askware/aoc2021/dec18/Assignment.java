package se.askware.aoc2021.dec18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		Pair previous = null;
		for (String line : input) {
			Pair top = parse(line);
			reduce(top);
			if (previous != null) {
				top = add(previous, top);
			}
			previous = top;
		}
		System.out.println(magnitude(previous));
	}

	private Pair add(Pair left, Pair right) {
		Pair p = new Pair(null, left, right);
		left.parent = p;
		right.parent = p;
		reduce(p);
		return p;
	}

	private Pair parse(String line) {
		Stack<Pair> stack = new Stack<>();
		Pair top = null;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '[') {
				Pair p = new Pair();
				if (stack.size() > 0) {
					p.parent = stack.peek();
					if (p.parent.left == null) {
						p.parent.left = p;
					} else {
						p.parent.right = p;
					}
				}
				stack.add(p);
			} else if (line.charAt(i) == ']') {
				top = stack.pop();
			} else if (line.charAt(i) == ',') {

			} else {
				Number n = new Number(line.charAt(i) - '0');
				Pair p = stack.peek();
				if (p.left == null) {
					p.left = n;
				} else {
					p.right = n;
				}

			}
		}
		return top;
	}

	private int magnitude(Element e) {
		if (e instanceof Number) {
			return ((Number) e).value;
		} else {
			Pair p = (Pair) e;
			return 3 * magnitude(p.left) + 2 * magnitude(p.right);
		}
	}

	private void reduce(Pair top) {
		//System.out.println("**** " + top);
		boolean changed = true;
		while (changed) {
			//System.out.println(top);
			changed = explodeRecursive(top, 1) || splitRecursive(top);
		}
	}

	private boolean splitRecursive(Pair p) {
		if (p.left instanceof Number && ((Number) p.left).value > 9) {
			int value = ((Number) p.left).value;
			Number left = new Number((int) Math.floor(value / 2.0));
			Number right = new Number((int) Math.ceil(value / 2.0));
			p.left = new Pair(p, left, right);
			return true;
		}
		if (p.left instanceof Pair) {
			if (splitRecursive((Pair) p.left)) {
				return true;
			}
		}
		if (p.right instanceof Number && ((Number) p.right).value > 9) {
			int value = ((Number) p.right).value;
			Number left = new Number((int) Math.floor(value / 2.0));
			Number right = new Number((int) Math.ceil(value / 2.0));
			p.right = new Pair(p, left, right);
			return true;
		}
		if (p.right instanceof Pair) {
			if (splitRecursive((Pair) p.right)) {
				return true;
			}
		}
		return false;
	}

	private boolean explodeRecursive(Pair p, int level) {
		if (level > 4) {
			explode(p);
			return true;
		}
		if (p.left instanceof Pair) {
			if (explodeRecursive((Pair) p.left, level + 1)) {
				return true;
			}
		}
		if (p.right instanceof Pair) {
			if (explodeRecursive((Pair) p.right, level + 1)) {
				return true;
			}
		}
		return false;
	}

	private void explode(Pair p) {
		Pair tmp = p;
		Element toUpdate1 = null;
		while (tmp != null && toUpdate1 == null) {
			if (tmp.parent != null) {
				if (tmp.parent.right == tmp) {
					toUpdate1 = tmp.parent.left;
					while (!(toUpdate1 instanceof Number)) {
						toUpdate1 = ((Pair) toUpdate1).right;
					}
				}
			}
			tmp = tmp.parent;
		}
		tmp = p;
		Element toUpdate2 = null;
		while (tmp != null && toUpdate2 == null) {
			if (tmp.parent != null) {
				if (tmp.parent.left == tmp) {
					toUpdate2 = tmp.parent.right;
					while (!(toUpdate2 instanceof Number)) {
						toUpdate2 = ((Pair) toUpdate2).left;
					}
				}
			}
			tmp = tmp.parent;
		}
		if (toUpdate1 != null) {
			((Number) toUpdate1).value += ((Number) p.left).value;
		}
		if (toUpdate2 != null) {
			((Number) toUpdate2).value += ((Number) p.right).value;
		}
		if (p.parent.left == p) {
			p.parent.left = new Number(0);
		} else {
			p.parent.right = new Number(0);
		}
	}

	@Override
	public void solvePartTwo(List<String> input) {
		List<Pair> allPairs = new ArrayList<>();
		for (String line : input) {
			Pair top = parse(line);
			reduce(top);
			allPairs.add(top);
		}
		int max = 0;
		for (int i = 0; i < allPairs.size(); i++) {
			for (int j = 0; j < allPairs.size(); j++) {
				if (i != j) {
					Pair p1 = allPairs.get(i);
					Pair p2 = allPairs.get(j);
					max = Math.max(magnitude(add(copy(p1), copy(p2))), max);
					max = Math.max(magnitude(add(copy(p2), copy(p1))), max);
				}
			}
		}
		System.out.println(max);
	}

	private Pair copy(Pair p) {
		Pair copy = new Pair();
		if (p.left instanceof Pair) {
			Pair copy2 = copy((Pair) p.left);
			copy.left = copy2;
			copy2.parent = copy;
		} else {
			copy.left = new Number(((Number) p.left).value);
		}
		if (p.right instanceof Pair) {
			Pair copy2 = copy((Pair) p.right);
			copy.right = copy2;
			copy2.parent = copy;
		} else {
			copy.right = new Number(((Number) p.right).value);
		}
		return copy;
	}

	private static class Element {
	}

	private static class Number extends Element {
		int value;

		public Number(int value) {
			super();
			this.value = value;
		}

		@Override
		public String toString() {
			return "" + value;
		}
	}

	private static class Pair extends Element {
		Pair parent;
		Element left;
		Element right;

		public Pair() {

		}

		public Pair(Pair parent, Element left, Element right) {
			super();
			this.parent = parent;
			this.left = left;
			this.right = right;
		}

		@Override
		public String toString() {
			return "[" + left.toString() + "," + right.toString() + "]";
		}
	}
}
