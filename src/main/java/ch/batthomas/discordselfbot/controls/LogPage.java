package ch.batthomas.discordselfbot.controls;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author batthomas
 */
public class LogPage extends VBox {
    
    private Label title;
    
    public LogPage() {
        initComponents();
    }
    
    private void initComponents(){
        this.setAlignment(Pos.TOP_LEFT);
        AnchorPane.setBottomAnchor(this, 30.0);
        AnchorPane.setLeftAnchor(this, 30.0);
        AnchorPane.setRightAnchor(this, 30.0);
        AnchorPane.setTopAnchor(this, 10.0);
        
        title = new Label("Log");
        title.getStyleClass().add("pagetitle");
        
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setPrefHeight(250);
        area.prefHeightProperty().bind(this.heightProperty());
        
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                Platform.runLater(() -> area.appendText(String.valueOf((char)b)));
            }
        };
        System.setOut(new PrintStream(out, true));
        
        VBox.setMargin(title, new Insets(0,0,15,0));
        this.getChildren().addAll(title, area);
    }
}
