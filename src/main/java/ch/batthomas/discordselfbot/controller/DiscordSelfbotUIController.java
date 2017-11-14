package ch.batthomas.discordselfbot.controller;

import ch.batthomas.discordselfbot.controls.OverviewPage;
import ch.batthomas.discordselfbot.controls.SettingsPage;
import ch.batthomas.discordselfbot.controls.Sidebar;
import ch.batthomas.discordselfbot.listener.MessageReceivedListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/**
 *
 * @author batthomas
 */
public class DiscordSelfbotUIController implements Initializable {

    @FXML
    private AnchorPane mainPane, sidebarContainer, leftAppBar, rightAppBar;

    private Map<String, Node> pages;

    private JDA jda;

    private OverviewPage overview;
    private SettingsPage settings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pages = new HashMap<>();
        initSidebar();
        initTitle();
        initPages();
        changePage("Overview");

    }

    public void startBot() {
        try {
            jda = new JDABuilder(AccountType.CLIENT).setToken(settings.getToken()).buildAsync();
            MessageReceivedListener msg = new MessageReceivedListener(settings.getPrefix().get(), settings.getLanguage().get());
            jda.addEventListener(msg);
            settings.getLanguage().addListener((observable, oldValue, newValue) -> {
                msg.setLanguage(newValue);
            });
            settings.getPrefix().addListener((observable, oldValue, newValue) -> {
                msg.setPrefix(newValue);
            });
            overview.setStatus(OverviewPage.Status.RUNNING);
        } catch (IOException ex) {
            Logger.getLogger(DiscordSelfbotUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LoginException ex) {
            overview.setStatus(OverviewPage.Status.LOGINEXCEPTION);
        } catch (IllegalArgumentException ex) {
            overview.setStatus(OverviewPage.Status.INVALIDTOKEN);
        } catch (RateLimitedException ex) {
            overview.setStatus(OverviewPage.Status.RATELIMITEXCEPTION);
        }
        mainPane.getScene().getWindow().setOnCloseRequest((event) -> {
            if (jda != null) {
                jda.shutdown();
            }
            Platform.exit();
        });
    }

    public void stopBot() {
        if (jda != null) {
            jda.shutdown();
        }
        overview.setStatus(OverviewPage.Status.OFFLINE);
    }

    private void changePage(String page) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pages.get(page));
    }

    private void initPages() {
        overview = new OverviewPage(this);
        pages.put("Overview", overview);
        settings = new SettingsPage();
        pages.put("Settings", settings);
    }

    private void initTitle() {
        StackPane sp = new StackPane();
        AnchorPane.setBottomAnchor(sp, 0.0);
        AnchorPane.setLeftAnchor(sp, 0.0);
        AnchorPane.setRightAnchor(sp, 0.0);
        AnchorPane.setTopAnchor(sp, 0.0);
        Label title = new Label("Discord Selfbot");
        title.getStyleClass().add("title");
        sp.setAlignment(Pos.CENTER);
        sp.getChildren().add(title);
        leftAppBar.getChildren().add(sp);
    }

    private void initSidebar() {
        Sidebar sidebar = new Sidebar();
        sidebarContainer.getChildren().add(sidebar);

        sidebar.addEntry("Overview", (event) -> {
            sidebar.focusButton("Overview");
            changePage("Overview");
        });

        sidebar.addEntry("Settings", (event) -> {
            sidebar.focusButton("Settings");
            changePage("Settings");
        });

        sidebar.focusButton("Overview");
    }

}
