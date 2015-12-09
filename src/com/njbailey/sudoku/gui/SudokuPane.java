/*
 * Nicholas Bailey
 * CS202
 * I have followed the Academic Dishonesty Policy.
 */
package com.njbailey.sudoku.gui;

import com.njbailey.sudoku.Board;
import com.njbailey.sudoku.Item;
import com.njbailey.sudoku.SudokuGenerator;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Nicholas Bailey
 */
public class SudokuPane extends Pane {

    /**
     * The width of the pane.
     */
    private double width;

    /**
     * The height of the pane.
     */
    private double height;

    /**
     * The {@code Board} the pane is showing.
     */
    private Board board;

    /**
     * The cell that will receive any keyboard input.
     */
    private Point2D activeCell;

    /**
     * The {@code Rectangle} that is draw into when a cell is highlighted.
     */
    private Rectangle activeCellRect;

    /**
     * The {@code Components} that show when the board is solved.
     */
    private WinnerPane winnerPane = new WinnerPane();

    /**
     * The {@code SudokuGenerator} generator used to create a board.
     */
    private final SudokuGenerator generator;

    /**
     * When the game was started.
     */
    private long startTime;

    /**
     * Construct a new Sudoku board to display the specified {@code Board}.
     *
     * @param generator the {@code SudokuGenerator} used to create the Sudoku
     * board
     */
    public SudokuPane(SudokuGenerator generator) {
        this.generator = generator;

        setOnMouseClicked(e -> {
            double cellWidth = width / 9;
            double cellHeight = height / 9;

            int cellX = (int) Math.floor(e.getSceneX() / cellWidth);
            int cellY = (int) Math.floor(e.getSceneY() / cellHeight);

            handleClick(cellX, cellY);
        });

        setOnKeyTyped(e -> {
            char c = e.getCharacter().charAt(0);

            if (c < '1' || c > '9') {
                return;
            }

            handleKeyTyped(c);
        });

        winnerPane.setOnPlayAgain(e -> {
            create();
            SudokuPane.this.requestFocus();
        });

        winnerPane.setOnExitProperty(e -> SudokuPane.super.setVisible(false));

        create();
    }

    public final void create() {
        generator.generate();
        this.board = generator.getBoard();

        paint();

        startTime = System.currentTimeMillis();
    }

    /**
     * Set the width of this pane.
     *
     * This function causes the pane to be redrawn.
     *
     * @param width the new width of the pane
     */
    public void setW(double width) {
        this.width = width;
        paint();
    }

    /**
     * Set the height of this pane.
     *
     * This function causes the pane to be redrawn.
     *
     * @param height the new height of the pane
     */
    public void setH(double height) {
        this.height = height;
        paint();
    }

    /**
     * @return the width of the pane
     */
    public double getW() {
        return width;
    }

    /**
     * @return the height of the pane
     */
    public double getH() {
        return height;
    }

    /**
     * Handle a click at the specified coordinate.
     *
     * The coordinate is passed to this function in translated cell-values. The
     * {@code cellX} and {@code cellY} are the actual cell-coordinate in the
     * Sudoku board. 0,0 is considered the first cell, and 8,8 is considered the
     * last cell.
     *
     * @param cellX the x-coordinate of the cell, between 0 and 8
     * @param cellY the y-coordinate of the cell, between 0 and 8
     */
    private void handleClick(int cellX, int cellY) {
        if(board.isSolved()) {
           return; 
        }
        
        if (board.get(cellX, cellY).isHidden()) {
            activeCell = new Point2D(cellX, cellY);

            double cellWidth = width / 9;
            double cellHeight = height / 9;

            getChildren().remove(activeCellRect);
            activeCellRect = new Rectangle(cellX * cellWidth,
                    cellY * cellHeight, cellWidth, cellHeight);
            activeCellRect.setFill(new Color(Color.SKYBLUE.getRed(),
                    Color.SKYBLUE.getGreen(), Color.SKYBLUE.getBlue(), .5));

            getChildren().add(activeCellRect);
        }
    }

    /**
     * Handle a key being typed.
     *
     * If the character doesn't lie between '1' and '9' this function is never
     * called.
     *
     * @param c the character that was typed.
     */
    private void handleKeyTyped(char c) {
        if (activeCell != null) {
            int linearCoordinate = (int) activeCell.getY() * 9 + (int) activeCell.getX();

            board.get(linearCoordinate).setValue(c - '0');
            paint();
        }
    }

    /**
     * Paint the Sudoku board.
     */
    private void paint() {
        double columnSpacing = (width / 9.0);
        double rowSpacing = (height / 9.0);

        getChildren().clear();

        for (int i = 0; i < 9; i++) {
            Line line = new Line(i * columnSpacing, 0, i * columnSpacing, height);

            if (i % 3 == 0) {
                line.setStrokeWidth(2.0);
            }

            getChildren().add(line);
        }

        for (int i = 0; i < 9; i++) {
            Line line = new Line(0, i * rowSpacing, width, i * rowSpacing);

            if (i % 3 == 0) {
                line.setStrokeWidth(2.0);
            }

            getChildren().add(line);
        }

        for (Item item : board.getItems()) {
            if (item.isHidden() && item.getValue() == -1) {
                continue;
            }

            double x = item.getColumn() * columnSpacing + (columnSpacing / 2);
            double y = item.getRow() * rowSpacing + (rowSpacing / 2);

            Text t = new Text(String.valueOf(item.getValue()));

            Font f = t.getFont();

            if (!item.isHidden()) {
                f = Font.font(f.getFamily(), FontWeight.BOLD, f.getSize());
            }

            double letterWidth = t.getLayoutBounds().getWidth();
            double letterHeight = t.getLayoutBounds().getHeight();

            x -= letterWidth / 2;
            y += letterHeight / 2;

            t.setX(x);
            t.setY(y);

            if (item.isHidden()) {
                if (Board.conflicting(board, item)) {
                    t.setStroke(Color.RED);
                } else {
                    t.setStroke(Color.GREEN);
                }
            }

            t.setFont(f);

            getChildren().add(t);
        }

        if (board.isSolved()) { // WE HAVE A WINNER!
            activeCell = null;
            displayWinnerPane(rowSpacing);
        }
    }

    private void displayWinnerPane(double rowSpacing) {
        Color color = new Color(1, 1, 1, .75);
        Rectangle rectangle = new Rectangle(0, 0, getW(), getH());
        rectangle.setFill(color);

        winnerPane.setElapsedTime(System.currentTimeMillis() - startTime);
        
        Bounds bounds = winnerPane.getBoundsInParent();
        int x = (int) getW() / 2 - (int) bounds.getWidth() / 2;
        int y = (int) getH() / 2 - (int) bounds.getHeight() / 2;

        // This is so ugly, and if you see this I am so sorry. 
        Pane.positionInArea(winnerPane, x, y, bounds.getWidth(), bounds.getHeight(),
                rowSpacing, Insets.EMPTY, HPos.LEFT, VPos.TOP, true);
        
        getChildren().addAll(rectangle, winnerPane);
    }
}
