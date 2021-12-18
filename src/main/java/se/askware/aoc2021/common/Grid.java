package se.askware.aoc2021.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
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
		cell.x = row;
		cell.y = columns;
	}

	public List<T> getAllNeighbors(Cell cell) {
		List<T> neighbors = new ArrayList<>();
		for (int i = Math.max(cell.x - 1, 0); i < Math.min(cell.x + 2, cells.length); i++) {
			for (int j = Math.max(cell.y - 1, 0); j < Math.min(cell.y + 2, cells[i].length); j++) {
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
		for (int i = Math.max(cell.x - 1, 0); i < Math.min(cell.x + 2, cells.length); i++) {
			if (cells[i][cell.y] != cell) {
				neighbors.add((T) cells[i][cell.y]);
			}
		}
		for (int j = Math.max(cell.y - 1, 0); j < Math.min(cell.y + 2, cells[cell.y].length); j++) {
			if (cells[cell.x][j] != cell) {
				neighbors.add((T) cells[cell.x][j]);
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
}
