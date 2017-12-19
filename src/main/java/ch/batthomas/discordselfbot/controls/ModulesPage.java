package ch.batthomas.discordselfbot.controls;

import ch.batthomas.discordselfbot.command.CommandCategory;
import ch.batthomas.discordselfbot.command.CommandManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.controlsfx.control.ToggleSwitch;

/**
 *
 * @author batthomas
 */
public class ModulesPage extends VBox {

    private Label title;
    private PasswordField tokenField;

    private final CommandManager manager;

    public ModulesPage(CommandManager manager) {
        this.manager = manager;
        initComponents();
    }

    private void initComponents() {
        this.setAlignment(Pos.TOP_LEFT);
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 30.0);
        AnchorPane.setRightAnchor(this, 30.0);
        AnchorPane.setTopAnchor(this, 10.0);

        title = new Label("Modules");
        title.getStyleClass().add("pagetitle");
        VBox.setMargin(title, new Insets(0, 0, 15, 0));

        this.getChildren().add(title);

        for (CommandCategory category : CommandCategory.values()) {
            if (category != CommandCategory.CORE) {
                AnchorPane cell = new AnchorPane();
                cell.prefWidthProperty().bind(this.widthProperty());
                cell.setPadding(new Insets(0, 0, 15, 0));

                Label label = new Label(category.name().substring(0, 1) + category.name().substring(1, category.name().length()).toLowerCase());
                label.getStyleClass().add("normaltext");
                VBox.setMargin(label, new Insets(0, 0, 3, 0));

                ToggleSwitch toggle = new ToggleSwitch();
                toggle.setSelected(manager.getCategoryStatus(category));
                toggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    manager.setCategoryStatus(category, newValue);
                });

                AnchorPane.setRightAnchor(toggle, 0.0);
                
                AnchorPane separatorPane = new AnchorPane();
                separatorPane.setPadding(new Insets(0, 0, 15, 0));
                Rectangle separator = new Rectangle();
                separator.setHeight(1);
                separator.widthProperty().bind(cell.widthProperty());
                separator.getStyleClass().add("separator");
                separatorPane.getChildren().add(separator);
                
                cell.getChildren().addAll(label, toggle);
                this.getChildren().addAll(cell, separatorPane);
            }
        }

    }

}
