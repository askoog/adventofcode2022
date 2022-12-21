package se.askware.aoc2022.common;

public class CharCell extends Cell {
	public char value;

	public CharCell(char value) {
		this.value = value;
	}

	@Override
	public String print() {
		return String.valueOf(value);
	}

	public boolean matches(char ... c){
		for (char c1 : c) {
			if (c1 == value){
				return true;
			}
		}
		return false;
	}
}