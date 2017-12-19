package ch.batthomas.discordselfbot.controls;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author batthomas
 */
public class SettingsPage extends VBox {
    
    private Label title;
    private PasswordField tokenField;
    private ComboBox<String> languageChooser;
    private ComboBox<String> prefixChooser;
    
    public SettingsPage() {
        initComponents();
    }
    
    private void initComponents(){
        this.setAlignment(Pos.TOP_LEFT);
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 30.0);
        AnchorPane.setRightAnchor(this, 30.0);
        AnchorPane.setTopAnchor(this, 10.0);
        
        title = new Label("Settings");
        title.getStyleClass().add("pagetitle");
        VBox.setMargin(title, new Insets(0,0,15,0));
        
        Label tokenLabel = new Label("Token");
        tokenLabel.getStyleClass().add("normaltext");
        VBox.setMargin(tokenLabel, new Insets(0,0,3,0));
        
        tokenField = new PasswordField();
        VBox.setMargin(tokenField, new Insets(0,0,15,0));
        tokenField.setPrefHeight(30);
        tokenField.setMaxWidth(400);
        
        Label languageLabel = new Label("Language");
        languageLabel.getStyleClass().add("normaltext");
        VBox.setMargin(languageLabel, new Insets(0,0,3,0));
        
        ObservableList<String> languages = FXCollections.observableArrayList();
        languages.addAll("English", "Deutsch");
        languageChooser = new ComboBox(languages);
        VBox.setMargin(languageChooser, new Insets(0,0,15,0));
        languageChooser.getSelectionModel().select("English");
        languageChooser.setPrefHeight(30);
        languageChooser.setMaxWidth(400);
        
        Label prefixLabel = new Label("Prefix");
        prefixLabel.getStyleClass().add("normaltext");
        VBox.setMargin(prefixLabel, new Insets(0,0,3,0));
        
        ObservableList<String> prefixes = FXCollections.observableArrayList();
        prefixes.addAll(">", ".", "/", ":", "?", "!");
        prefixChooser = new ComboBox(prefixes);
        prefixChooser.getSelectionModel().select(">");
        prefixChooser.setPrefHeight(30);
        prefixChooser.setMaxWidth(400);
        
        this.getChildren().addAll(title, tokenLabel, tokenField, languageLabel, languageChooser, prefixLabel, prefixChooser);
    }
    
    public String getToken(){
        return tokenField.getText();
    }
    
    public ReadOnlyObjectProperty<String> getLanguage(){
        return languageChooser.getSelectionModel().selectedItemProperty();
    }
    
    public ReadOnlyObjectProperty<String> getPrefix(){
        return prefixChooser.getSelectionModel().selectedItemProperty();
    }
    
}
