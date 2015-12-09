/*
 * Nicholas Bailey
 * CS202
 * I have followed the Academic Dishonesty Policy.
 */
package com.njbailey.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Nicholas Bailey
 */
public class SudokuGenerator {

    /**
     * The pseudo-random number generator used for this generator.
     */
    private static final Random RANDOM = new Random();

    /**
     * The number of Sudoku cells, horizontally.
     */
    public static final int WIDTH = 9;

    /**
     * The number of Sudoku cells, vertically.
     */
    public static final int HEIGHT = 9;

    /**
     * The {@code Board} that was generated.
     */
    private Board board = new Board();

    /**
     * @return the game board that was generated
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Generate a pseudo-random valid board.
     */
    public void generate() {
        board.clear();
        
        // The possible numbers that can go in each slot. 9 numbers for each
        // of the 81 squares.
        List<List<Integer>> available = new ArrayList<>();

        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            List<Integer> list = new ArrayList<>();

            for (int j = 0; j < 9; j++) {
                list.add(j + 1);
            }

            available.add(list);
        }

        // The current square that's being processed.
        int current = 0;

        while (current < WIDTH * HEIGHT) {
            // If we're not out of possible numbers for the current square
            // generate a random one and add it to the board
            if (!available.get(current).isEmpty()) {
                int index = getRandomIndex(available.get(current));
                int value = available.get(current).get(index);

                Item item = new Item(current, value);

                if (!board.conflicting(item)) {
                    board.set(current, item);
                    available.get(current).remove(index);
                    current++;
                } else {
                    available.get(current).remove(index);
                }
            } else {
                // We're out of useable moves, rewind a square and try again,
                // resetting the available numbers for the current square
                for (int i = 0; i < 9; i++) {
                    available.get(current).add(i + 1);
                }

                board.set(current - 1, null);
                current--;
            }
        }

        // This really doesn't have anything to do with the generator. Maybe 
        // move it elsewhere?
        mask();
    }

    /**
     * @param available the {@code List<Integer>} to return a pseudo-random
     * valid index into.
     * @return a pseudo-random valid into for the specified
     * {@code List<Integer>}.
     */
    private int getRandomIndex(List<Integer> available) {
        return RANDOM.nextInt(available.size());
    }

    /**
     * Mask out random values in the Sudoku board.
     */
    private void mask() {
        for (int x = 0; x < 9; x++) {
            List<Integer> available = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                available.add(i);
            }

            int numToRemove = RANDOM.nextInt(3);
            
            for (int i = 0; i < numToRemove; i++) {
                int r = getRandomIndex(available);
                int value = available.get(r);

                for (Item item : board.getItems()) {
                    if (item.getRegion() == x + 1) {
                        if (item.getValue() == value) {
                            item.setHidden(true);
                        }
                    }
                }
            }
        }
    }
}
