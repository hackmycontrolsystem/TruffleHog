/*
 * This file is part of TruffleHog.
 *
 * TruffleHog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TruffleHog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with TruffleHog.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.kit.trufflehog.view.jung.visualization;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * \brief
 * \details
 * \date 22.03.16
 * \copyright GNU Public License
 *
 * @author Jan Hermes
 * @version 0.0.1
 */
public class PannableCanvas extends Pane {

    //DoubleProperty myScale = new SimpleDoubleProperty(1.0);

    private double myScale = 1.0;

    private Pane ghost;

    public PannableCanvas(Pane ghost) {
        setPrefSize(600, 600);

        this.ghost = ghost;
        this.ghost.setPrefSize(600, 600);
        this.ghost.setMouseTransparent(true);

        this.setBackground(new Background(new BackgroundFill(null, null, null)));
        //this.setVisible(false);
        //setStyle("-fx-background-color: lightgrey; -fx-border-color: blue;");

        // add scale transform
        //scaleXProperty().bind(myScale);
        //scaleYProperty().bind(myScale);

        //paneTest.scaleXProperty().bind(myScale);
      //  paneTest.scaleYProperty().bind(myScale);

    }


    public Pane getGhost() {
        return ghost;
    }


    /**
     * Add a grid to the canvas, send it to back
     */
    public void addGrid() {

        double w = getBoundsInLocal().getWidth();
        double h = getBoundsInLocal().getHeight();

        // add grid
        Canvas grid = new Canvas(w, h);

        // don't catch mouse events
        grid.setMouseTransparent(true);

        GraphicsContext gc = grid.getGraphicsContext2D();

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);

        // draw grid lines
        double offset = 50;
        for( double i=offset; i < w; i+=offset) {
            gc.strokeLine( i, 0, i, h);
            gc.strokeLine( 0, i, w, i);
        }

        getChildren().add( grid);

        grid.toBack();
    }

    public double getScale() {

        return myScale;
    }

    public void setScale( double scale) {

        myScale = scale;
        setScaleX(myScale);
        setScaleY(myScale);
        ghost.setScaleX(myScale);
        ghost.setScaleY(myScale);
    }

    public void setPivot( double x, double y) {

        setTranslateX(getTranslateX() - x);
        setTranslateY(getTranslateY() - y);

        ghost.setTranslateX(ghost.getTranslateX() - x);
        ghost.setTranslateY(ghost.getTranslateY() - y);
    }
}