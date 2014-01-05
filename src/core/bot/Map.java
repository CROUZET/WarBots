package core.bot;

import java.io.Serializable;

public class Map implements Serializable {

	// -----------------------------------------------------------------------------------------------------------
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -1951237690409882203L;

	// -----------------------------------------------------------------------------------------------------------
	
	private String name;
	
	private int length;
	
	private int width;
	
	private int[][] gameboard;

	// -----------------------------------------------------------------------------------------------------------
	
	public Map(String name, int length, int width, int[][] field) {

	}

	// -----------------------------------------------------------------------------------------------------------
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int[][] getGameboard() {
		return gameboard;
	}

	public void setGameboard(int[][] gameboard) {
		this.gameboard = gameboard;
	}

}
