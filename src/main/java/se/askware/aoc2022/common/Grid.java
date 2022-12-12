package se.askware.aoc2022.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class Grid<T extends Cell> {

	Cell[][] cells;

	public Grid(int rows, int columns) {
		cells = new Cell[rows][columns];
	}

	public void init(BiFunction<Integer, Integer, T> initiator) {
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[row].length; column++) {
				setCell(initiator.apply(row, column), row, column);
			}
		}
	}

	/**
	 * Initiate a grid from input lines. Assumes all lines in input has the same length. For each character in the input, a cell will be created
	 * @param input input data
	 * @param initiator callback for creating the cell at a given pos with the character at row,col
	 * @return an initiated grid
	 * @param <T>
	 */
	public static <T extends Cell> Grid<T> init(List<String> input, BiFunction<Pair<Integer>, Character, T> initiator) {
		Grid<T> grid = new Grid<>(input.size(), input.get(0).length());
		grid.init((row,col) -> initiator.apply(new Pair<>(row,col), input.get(row).charAt(col)));
		return grid;
	}



	public List<T> getRow(int row) {
		return (List<T>) Arrays.asList(cells[row]);
	}

	public List<T> getColumn(int column) {
		List<T> result = new ArrayList<>();
		for (int i = 0; i < cells.length; i++) {
			result.add((T) cells[i][column]);
		}
		return result;
	}

	public void setRow(int row, List<T> elements) {
		for (int i = 0; i < elements.size(); i++) {
			setCell(elements.get(i), row, i);
		}
	}

	public void setColumn(int column, List<T> elements) {
		for (int i = 0; i < elements.size(); i++) {
			setCell(elements.get(i), i, column);
		}
	}

	public T getCell(int x, int y) {
		return (T) cells[x][y];
	}

	public void setCell(Cell cell, int row, int columns) {
		cells[row][columns] = cell;
		cell.row = row;
		cell.col = columns;
	}

	public List<T> getAllNeighbors(Cell cell) {
		List<T> neighbors = new ArrayList<>();
		for (int i = Math.max(cell.row - 1, 0); i < Math.min(cell.row + 2, cells.length); i++) {
			for (int j = Math.max(cell.col - 1, 0); j < Math.min(cell.col + 2, cells[i].length); j++) {
				if (cells[i][j] != cell) {
					neighbors.add((T) cells[i][j]);
				}
			}

		}
		//System.out.println(cell.x + "," + cell.y + " : " + cell.print() + " " + neighbors.size());
		return neighbors;
	}

	public List<T> getXYNeighbors(Cell cell) {
		List<T> neighbors = new ArrayList<>();
		for (int i = Math.max(cell.row - 1, 0); i < Math.min(cell.row + 2, cells.length); i++) {
			if (cells[i][cell.col] != cell) {
				neighbors.add((T) cells[i][cell.col]);
			}
		}
		for (int j = Math.max(cell.col - 1, 0); j < Math.min(cell.col + 2, cells[0].length); j++) {
			if (cells[cell.row][j] != cell) {
				neighbors.add((T) cells[cell.row][j]);
			}
		}
		//System.out.println(cell.x + "," + cell.y + " : " + cell.print() + " " + neighbors.size());
		return neighbors;
	}

	public Stream<T> getAll() {
		return IntStream.range(0, cells.length).boxed()
				.flatMap(x -> IntStream.range(0, cells[x].length).mapToObj(y -> getCell(x, y)));

	}

	public void print() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				System.out.print(getCell(i, j).print());
			}
			System.out.println();
		}
		System.out.println();

	}

	public List<T> findPathXY(T start, T end, BiPredicate<T,T> allowedToEnterPredicate){
		boolean[][] seen = new boolean[cells.length][cells[0].length];
		Queue<List<T>> queue = new LinkedList<>();
		queue.add(List.of(start));
		while (!queue.isEmpty()) {
			final List<T> path = queue.poll();
			final T last = path.get(path.size() - 1);
			if (last == end) {
				return path;
			} else {
				getXYNeighbors(last).forEach(c -> {
					if (!seen[c.row][c.col] && allowedToEnterPredicate.test(last, c)) {
						seen[c.row][c.col] = true;
						List<T> copy = new ArrayList<>(path);
						copy.add(c);
						queue.add(copy);

					}
				});
			}
		}
		return List.of();
	}
}
