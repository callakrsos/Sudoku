/*
 * Nicholas Bailey
 * CS202
 * I have followed the Academic Dishonesty Policy.
 */
package com.njbailey.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nicholas Bailey
 */
public class Board {

    /**
     * The backing array holding all of the values in the cells.
     */
    private final Item[] items = new Item[9 * 9];

    /**
     * Sets the specified linear coordinate on the board to contain the
     * specified value.
     *
     * @param index the linear coordinate of the cell
     * @param value the value to put into the cell
     */
    public void set(int index, Item value) {
        if (index < 0 || index > items.length) {
            throw new IllegalArgumentException("index must be between 0 and " + items.length);
        }

        items[index] = value;
    }

    /**
     * Sets the specified cell at {@code cellX}, {@code cellY} to contain the
     * specified value.
     *
     * @param cellX the x-coordinate of the cell
     * @param cellY the y-coordinate of the cell
     * @param value the value to put into the cell
     */
    public void set(int cellX, int cellY, Item value) {
        if (cellX < 0 || cellX > 8) {
            throw new IllegalArgumentException("cellX must be between 0 and 8");
        }

        if (cellY < 0 || cellY > 8) {
            throw new IllegalArgumentException("cellY must be between 0 and 8");
        }

        set(cellY * 9 + cellY, value);
    }

    /**
     * @param index the linear-coordinate of the cell
     * @return the {@code Item} contained in the specified cell
     */
    public Item get(int index) {
        return items[index];
    }

    /**
     * @param cellX the x-coordinate of the cell
     * @param cellY the y-coordinate of the cell
     * @return the {@code Item} contained in the specified cell.
     */
    public Item get(int cellX, int cellY) {
        return items[cellY * 9 + cellX];
    }

    /**
     * @return a read-only {@code List} of the {@code Item}s that the board
     * consists of.
     */
    public List<Item> getItems() {
        return Collections.unmodifiableList(Arrays.asList(items));
    }
    
    public void clear() {
        for(int i = 0; i < 81; i++) {
            items[i] = null;
        }
    }

    /**
     * Determines if the specified {@code Item} is conflicting with the current
     * board.
     *
     * To determine if the {@code Item} is conflicting, three checks must be
     * made.
     *
     * <ul>
     * <li>Be sure there isn't any of this number in a row</li>
     * <li>Be sure there isn't any of this number in a column</li>
     * <li>Be sure there isn't any of this number in the selected region</li>
     * </ul>
     *
     * @param value the {@code Item} to check
     * @return true if the {@code Item} is invalid, false otherwise.
     */
    public boolean conflicting(Item value) {
        for (Item item : items) {
            if (item == null) {
                continue;
            }

            if (item.getColumn() == value.getColumn() || item.getRow() == value.getRow()
                    || item.getRegion() == value.getRegion()) {
                if (item.getValue() == value.getValue()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isSolved() {
        for (Item i : items) {
            if (i.getValue() == -1) {
                return false;
            }

            if (Board.conflicting(this, i)) {
                return false;
            }
        }

        return true;
    }

    public static boolean conflicting(Board board, Item item) {
        int count = 0;

        for (Item i : board.getItems()) {
            if (i == null) {
                continue; // Shouldn't happen.
            }

            if (item.getColumn() == i.getColumn()
                    || item.getRow() == i.getRow()
                    || item.getRegion() == i.getRegion()) {

                if (item.getValue() == i.getValue()) {
                    count++;
                }
            }
        }

        return count > 1;
    }
}
