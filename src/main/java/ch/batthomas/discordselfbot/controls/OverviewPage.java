package ch.batthomas.discordselfbot.controls;

import ch.batthomas.discordselfbot.DiscordSelfbot;
import ch.batthomas.discordselfbot.controller.DiscordSelfbotUIController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author batthomas
 */
public class OverviewPage extends VBox {

    private final DiscordSelfbotUIController controller;

    private Label title;
    private Label statusLabel;
    private ImageView statusImage;
    private Button toggleStatus;

    private Status status;

    public OverviewPage(DiscordSelfbotUIController controller) {
        this.controller = controller;
        status = Status.OFFLINE;
        initComponents();
    }

    private void initComponents() {
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setSpacing(20.0);
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);

        title = new Label("Overview");
        title.getStyleClass().add("pagetitle");

        statusImage = new ImageView(DiscordSelfbot.class.getResource("image/bot_off.png").toExternalForm());
        statusImage.setFitWidth(90);
        statusImage.setFitHeight(130);

        statusLabel = new Label("The Selfbot is offline");
        statusLabel.getStyleClass().add("statusoffline");

        toggleStatus = new Button("Start the Selfbot");
        toggleStatus.setPrefWidth(150);
        toggleStatus.getStyleClass().add("materialbutton");
        toggleStatus.setOnMouseClicked((event) -> {
            toggleStatus.setVisible(false);
            toggleStatus.setDisable(true);
            if (status == Status.RUNNING) {
                controller.stopBot();
            } else {
                controller.startBot();
            }
        });

        this.getChildren().addAll(title, statusImage, statusLabel, toggleStatus);
    }

    public void setStatus(Status status) {
        this.status = status;
        toggleStatus.setVisible(true);
        toggleStatus.setDisable(false);
        if (status == Status.RUNNING) {
            statusLabel.setText("The Selfbot is running");
            statusImage.setImage(new Image(DiscordSelfbot.class.getResourceAsStream("image/bot_on.png")));
            toggleStatus.setText("Stop the Selfbot");
        } else {
            switch (status) {
                case LOGINEXCEPTION:
                    statusLabel.setText("LoginException : Please provide a valid token");
                    break;
                case RATELIMITEXCEPTION:
                    statusLabel.setText("RateLimitException : You've exceeded the rate limit");
                    break;
                case INVALIDTOKEN:
                    statusLabel.setText("Invalid token : Please provide a valid token");
                    break;
                default:
                    statusLabel.setText("The Selfbot is offline");
                    break;
            }
            toggleStatus.setText("Start the Selfbot");
            statusImage.setImage(new Image(DiscordSelfbot.class.getResourceAsStream("image/bot_off.png")));
        }
    }

    public enum Status {
        RUNNING, OFFLINE, LOGINEXCEPTION, RATELIMITEXCEPTION, INVALIDTOKEN;
    }

}
