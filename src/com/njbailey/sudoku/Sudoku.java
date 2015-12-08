/*
 * Nicholas Bailey
 * CS202
 * I have followed the Academic Dishonesty Policy.
 */
package com.njbailey.sudoku;

import com.njbailey.sudoku.gui.SudokuPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Nicholas Bailey
 */
public class Sudoku extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SudokuPane pane = new SudokuPane(new SudokuGenerator());

        Scene scene = new Scene(pane, 500, 500);

        primaryStage.widthProperty().addListener(e -> {
            pane.setMinWidth(primaryStage.getWidth());
            pane.setW(scene.getWidth());
        });

        primaryStage.heightProperty().addListener(e -> {
            pane.setMinHeight(primaryStage.getHeight());
            pane.setH(scene.getHeight());
        });
        
        // This is weird. I don't like it. I don't think I thoroughly understand
        // the correct way to actually handle games with JavaFX. So yea, when 
        // the user wants to exit the SudokuPane is set to 'hidden' which 
        // manipulates the 'visible' property... Then it notifies this, and 
        // the window is closed.
        pane.visibleProperty().addListener(e -> primaryStage.close());

        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        pane.requestFocus();
    }
}
