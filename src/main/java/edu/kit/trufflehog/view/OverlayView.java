package edu.kit.trufflehog.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * <p>
 *     The OverlayView provides GUI functionality for all interactions that a user performs on an overlay
 *     that can be displayed as a floating (slightly transparent) view on top of the main view.
 * </p>
 */
public class OverlayView extends GridPane {

    /**
     * <p>
     *     Creates a new OverlayView with the given fxmlFileName. The fxml file has to be in the same
     *     namespace as the OverlayView.
     * </p>
     *
z    */
    public OverlayView(final String fxmlFileName) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
