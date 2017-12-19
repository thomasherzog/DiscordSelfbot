package ch.batthomas.discordselfbot.command;

import ch.batthomas.discordselfbot.command.searches.UrbanDictionaryCommand;
import ch.batthomas.discordselfbot.command.utilities.MeCommand;
import ch.batthomas.discordselfbot.command.utilities.EmbedCommand;
import ch.batthomas.discordselfbot.command.core.HelpCommand;
import ch.batthomas.discordselfbot.command.core.PingCommand;
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
    private final Map<CommandCategory, Boolean> categories;
    private final I18NManager i18n;

    public CommandManager(String prefix, String language) throws IOException {
        this.prefix = prefix;
        i18n = new I18NManager("commands");
        setLanguage(language);
        categories = new HashMap<>();
        commands = new HashMap<>();
        initCommands();
        initCategories();
    }

    public Command getCommand(String command) {
        Command cmd = commands.get(command);
        if (cmd != null && getCategoryStatus(cmd.getCategory())) {
            return commands.get(command);
        } else {
            return null;
        }
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

    private void initCommands() {
        commands.put("help", new HelpCommand(this));
        commands.put("embed", new EmbedCommand(this));
        commands.put("me", new MeCommand(this));
        commands.put("ping", new PingCommand(this));

        commands.put("urbandictionary", new UrbanDictionaryCommand(this));
    }

    private void initCategories() {
        categories.put(CommandCategory.UTILITIES, true);
        categories.put(CommandCategory.SEARCHES, true);
        categories.put(CommandCategory.CORE, true);
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

    public boolean getCategoryStatus(CommandCategory category) {
        return categories.get(category);
    }

    public void setCategoryStatus(CommandCategory category, boolean status) {
        categories.put(category, status);
    }

}
