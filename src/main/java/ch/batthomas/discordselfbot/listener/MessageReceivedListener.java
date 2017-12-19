package ch.batthomas.discordselfbot.listener;

import ch.batthomas.discordselfbot.command.Command;
import ch.batthomas.discordselfbot.command.CommandManager;
import java.io.IOException;
import java.util.Arrays;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 *
 * @author batthomas
 */
public class MessageReceivedListener extends ListenerAdapter {

    private final CommandManager cmd;

    public MessageReceivedListener(CommandManager cmd) throws IOException {
        this.cmd = cmd;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        Message message = event.getMessage();

        if (user.equals(event.getJDA().getSelfUser())) {
            if (message.getContentRaw().startsWith(cmd.getPrefix())) {
                String[] parts = message.getContentRaw().split(" ");
                Command command = cmd.getCommand(parts[0].replaceFirst(cmd.getPrefix(), ""));
                if (command != null) {
                    command.execute(event, Arrays.copyOfRange(parts, 1, parts.length));
                }
            }
        }
    }

    public void setLanguage(String language) {
        cmd.setLanguage(language);
    }

    public void setPrefix(String prefix) {
        cmd.setPrefix(prefix);
    }

}
