package ch.batthomas.discordselfbot.controller;

import ch.batthomas.discordselfbot.command.CommandManager;
import ch.batthomas.discordselfbot.controls.LogPage;
import ch.batthomas.discordselfbot.controls.ModulesPage;
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
    private CommandManager cmd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            pages = new HashMap<>();
            initSidebar();
            initTitle();
            initPages();
            changePage("Overview");
        } catch (IOException ex) {
            Logger.getLogger(DiscordSelfbotUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startBot() {
        try {
            jda = new JDABuilder(AccountType.CLIENT).setToken(((SettingsPage) pages.get("Settings")).getToken()).buildAsync();
            MessageReceivedListener msg = new MessageReceivedListener(cmd);
            jda.addEventListener(msg);
            ((SettingsPage) pages.get("Settings")).getLanguage().addListener((observable, oldValue, newValue) -> {
                msg.setLanguage(newValue);
            });
            ((SettingsPage) pages.get("Settings")).getPrefix().addListener((observable, oldValue, newValue) -> {
                msg.setPrefix(newValue);
            });
            ((OverviewPage) pages.get("Overview")).setStatus(OverviewPage.Status.RUNNING);
        } catch (IOException ex) {
            Logger.getLogger(DiscordSelfbotUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LoginException ex) {
            ((OverviewPage) pages.get("Overview")).setStatus(OverviewPage.Status.LOGINEXCEPTION);
        } catch (IllegalArgumentException ex) {
            ((OverviewPage) pages.get("Overview")).setStatus(OverviewPage.Status.INVALIDTOKEN);
        } catch (RateLimitedException ex) {
            ((OverviewPage) pages.get("Overview")).setStatus(OverviewPage.Status.RATELIMITEXCEPTION);
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
        ((OverviewPage) pages.get("Overview")).setStatus(OverviewPage.Status.OFFLINE);
    }

    private void changePage(String page) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pages.get(page));
    }

    private void initPages() throws IOException {
        pages.put("Overview", new OverviewPage(this));
        pages.put("Settings", new SettingsPage());
        pages.put("Log", new LogPage());
        cmd = new CommandManager(((SettingsPage) pages.get("Settings")).getPrefix().get(), ((SettingsPage) pages.get("Settings")).getLanguage().get());
        pages.put("Modules", new ModulesPage(cmd));
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
        
        sidebar.addEntry("Modules", (event) -> {
            sidebar.focusButton("Modules");
            changePage("Modules");
        });
        
        sidebar.addEntry("Log", (event) -> {
            sidebar.focusButton("Log");
            changePage("Log");
        });

        sidebar.addEntry("Settings", (event) -> {
            sidebar.focusButton("Settings");
            changePage("Settings");
        });

        sidebar.focusButton("Overview");
    }

}
