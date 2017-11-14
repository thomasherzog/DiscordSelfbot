package ch.batthomas.discordselfbot.controls;

import java.util.HashMap;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author batthomas
 */
public class Sidebar extends VBox {

    private final Map<String, Button> entries;

    public Sidebar() {
        entries = new HashMap<>();
        initSidebar();
    }

    private void initSidebar() {
        this.setAlignment(Pos.BASELINE_CENTER);
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
    }

    public void addEntry(String text, EventHandler<? super MouseEvent> event) {
        Button button = new Button(text);
        button.setPrefWidth(190);
        button.setPrefHeight(30);
        button.setOnMouseClicked(event);
        button.getStyleClass().add("sidebarbutton");
        this.getChildren().add(button);
        entries.put(button.getText(), button);
    }

    public void focusButton(String title) {
        for (Button b : entries.values()) {
            b.getStyleClass().remove("sidebarbuttonactive");
            b.getStyleClass().add("sidebarbutton");
        }
        entries.get(title).getStyleClass().add("sidebarbuttonactive");
    }

}
