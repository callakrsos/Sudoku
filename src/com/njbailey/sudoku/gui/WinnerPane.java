/*
 * Copyright (C) 2015 Nicholas Bailey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.njbailey.sudoku.gui;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Nicholas Bailey
 */
public class WinnerPane extends Group {
    /**
     * 1000 milliseconds in a second
     */
    private static final int SECOND = 1000;

    /**
     * 60 seconds in a minute
     */
    private static final int MINUTE = 60 * SECOND;
    
    /**
     * 60 minutes in an hour
     */
    private static final int HOUR = 60 * MINUTE;
    
    /**
     * 24 hours in a day
     */
    private static final int DAY = 24 * HOUR;
    
    private final Text text = new Text("Congratulations!");
    private final Button btnReplay = new Button("Play Again");
    private final Button btnExit = new Button("Exit");
    private final Text lblElapsedTime = new Text("You solved the puzzle in ");
    
    private LongProperty elapsedTime = new SimpleLongProperty();
    
    /**
     * The primary component that holds the rest of the components.
     */
    private final VBox vbox = new VBox();

    private static String formatPlural(int n) {
        return n > 1 ? "s" : "";
    }
    
    public WinnerPane() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.getChildren().addAll(btnReplay, btnExit);

        text.setFont(new Font(33));
        
        // Properties are pretty cool. It's like a value-change-listener.
        elapsedTime.addListener(t -> {
            long elapsed = elapsedTime.longValue();
            int seconds = (int) (elapsed / SECOND) % 60;
            int minutes = (int) (elapsed / MINUTE) % 60;
            int hours = (int) (elapsed / HOUR) % 24;
            int days = (int) (elapsed / DAY); // Seriously... days?
            
            String timeString = "";
            
            if(days > 0) {
                timeString += days + " day" + formatPlural(days) + ", ";
            }
            
            if(hours > 0) {
                timeString += hours + " hour" + formatPlural(hours) + ", ";
            }
            
            if(minutes > 0) {
                timeString += minutes + " minute" + formatPlural(minutes) + ", ";
            }
            
            if(seconds > 0) {
                if(days > 0 || hours > 0 || minutes > 0) {
                    timeString += "and, ";
                }
                timeString += seconds + " second" + formatPlural(seconds);
            }
            
            lblElapsedTime.setText("You solved the puzzle in " + timeString);
        });

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.getChildren().addAll(text, lblElapsedTime, hbox);

        getChildren().add(vbox);
    }
    
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime.set(elapsedTime);
    }
   
    public void setOnPlayAgain(EventHandler<ActionEvent> handler) {
        btnReplay.setOnAction(handler);
    }
    
    public void setOnExitProperty(EventHandler<ActionEvent> handler) {
        btnExit.setOnAction(handler);
    }
}
