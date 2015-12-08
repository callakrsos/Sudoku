/*
 * Nicholas Bailey
 * CS202
 * I have followed the Academic Dishonesty Policy.
 */
package com.njbailey.sudoku;

/**
 *
 * @author Nicholas Bailey
 */
public class Item {
	/**
	 * The column of this item.
	 */
	private final int column;
	
	/**
	 * The row of this item.
	 */
	private final int row;
	
	/**
	 * The region of this item.
	 */
	private final int region;

	/**
	 * The correct value of this item.
	 */
	private final int correctValue;
	
	/**
	 * The value of this item.
	 */
	private int value;
	
	/**
	 * Whether or not this item is drawn.
	 */
	private boolean hidden = false;
	
	/**
	 * Construct a new {@code Item} at the specified linear-index containing the
	 * specified value.
	 * 
	 * @param index the linear-coordinate of this item
	 * @param value the value contained in this item
	 */
	public Item(int index, int value) {
		column = calculateColumn(index);
		row = calculateRow(index);
		region = calculateRegion(index);
		
		this.correctValue = value;
		this.value = value;
	}
	
	/**
	 * @return whether or not this {@code Item} will be drawn to the Sudoku board.
	 */
	public boolean isHidden() {
		return hidden;
	}
	
	/**
	 * Sets whether or not this {@code Item} will be drawn to the Sudoku board.
	 * 
	 * @param hidden whether or not this Item is drawn
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
		
		if(hidden == true) {
			value = -1;
		}
	}
	
	/**
	 * @return the column this item is in
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * @return the row this item is in
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * @return the region this item is in 
	 */
	public int getRegion() {
		return region;
	}
	
	/**
	 * @return the value held by this item
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * @return the correct value for this item
	 */
	public int getCorrectValue() {
		return correctValue;
	}
	
	/**
	 * Sets the value held by this cell to the specified value.
	 * @param value the new value this cell should contain.
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Calculate the column that the specified index falls in.
	 * 
	 * @param index the linear-index
	 * @return the column that the specified index falls in
	 */
	private static int calculateColumn(int index) {
		return index % SudokuGenerator.WIDTH;
	}
	
	/**
	 * Calculate the row that the specified index falls in.
	 * 
	 * @param index the linear-index
	 * @return the row that the specified index falls in
	 */
	private static int calculateRow(int index) {
		return index / SudokuGenerator.HEIGHT;
	}
	
	/**
	 * Calculate the region of the specified index.
	 * 
	 * The regions are number 1 to 9, with the top left region being 1 and the
	 * bottom right region being 9.
	 * 
	 * The Sudoku board split into regions looks like the following: 
	 * 
	 * <code>
	 * 0,0 0,1 0,2    0,3 0,4 0,5   0,6 0,7 0,8
	 * 1,0 1,1 1,2    1,3 1,4 1,5   1,6 1,7 1,8
	 * 2,0 2,1 2,2    2,3 2,4 2,5   2,6 2,7 2,8
     *
	 * 3,0 3,1 3,2    3,3 3,4 3,5   3,6 3,7 3,8
	 * 4,0 4,1 4,2    4,3 4,4 4,5   4,6 4,7 4,8
	 * 5,0 5,1 5,2    5,3 5,4 5,5   5,6 5,7 5,8
	 *
	 * 6,0 6,1 6,2    6,3 6,4 6,5   6,6 6,7 6,8
	 * 7,0 7,1 7,2    7,3 7,4 7,5   7,6 7,7 7,8
	 * 8,0 8,1 8,2    8,3 8,4 8,5   8,6 8,7 8,8
	 * </code>
	 * 
	 * @param index the linear-index of the cell.
	 * @return the region that the specified index falls into.
	 */
	private static int calculateRegion(int index) {
		int x = calculateColumn(index);
		int y = calculateRow(index);
		int region;
		
		if(y < 3) {
			// Region 1, 2, or 3
			if(x < 3) {
				region = 1;
			} else if(x < 6) {
				region = 2;
			} else {
				region = 3;
			}
		} else if(y < 6) {
			// Region 4, 5, or 6
			if(x < 3) {
				region = 4;
			} else if(x < 6) {
				region = 5;
			} else {
				region = 6;
			}
		} else {
			// Region 7, 8, or 9
			if(x < 3) {
				region = 7;
			} else if(x < 6) {
				region = 8;
			} else {
				region = 9;
			}
		}
		
		return region;
	}
	
	@Override
	public String toString() {
		return "[" + region + "](" + column + ", " + row + "): " + value;
	}
}
