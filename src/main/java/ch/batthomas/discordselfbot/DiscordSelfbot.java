package ch.batthomas.discordselfbot;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 *
 * @author batthomas
 */
public class DiscordSelfbot extends Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Region root = new FXMLLoader(getClass().getResource("ui/DiscordSelfbotUI.fxml")).load();
            stage.setTitle("Discord Selfbot");
            stage.setScene(new Scene(root));
            for (int i = 16; i <= 512; i *= 2) {
                stage.getIcons().add(new Image(getClass().getResourceAsStream("image/" + "DiscordSelfbot_" + i + "x" + i + ".png")));
            }
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DiscordSelfbot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
