package ch.batthomas.discordselfbot.command;

import ch.batthomas.discordselfbot.i18n.I18NManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author batthomas
 */
public class CommandManager {

    private String prefix;
    private final Map<String, Command> commands;
    private final I18NManager i18n;

    public CommandManager(String prefix, String language) throws IOException {
        this.prefix = prefix;
        i18n = new I18NManager("commands");
        setLanguage(language);
        commands = new HashMap<>();
        commands.put("help", new HelpCommand(this));
        commands.put("embed", new EmbedCommand(this));
        commands.put("me", new MeCommand(this));
    }

    public Command getCommand(String command) {
        return commands.get(command);
    }

    public void setLanguage(String language) {
        switch (language) {
            case "Deutsch":
                i18n.changeLocale(Locale.GERMAN);
                break;
            case "English":
            default:
                i18n.changeLocale(Locale.ENGLISH);
                break;
        }
    }

    public I18NManager getI18N() {
        return i18n;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
