package ch.batthomas.discordselfbot.command.utilities;

import ch.batthomas.discordselfbot.command.Command;
import ch.batthomas.discordselfbot.command.CommandCategory;
import ch.batthomas.discordselfbot.command.CommandManager;
import java.time.LocalDate;
import java.util.Arrays;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author batthomas
 */
public class EmbedCommand extends Command {

    public EmbedCommand(CommandManager cmd) {
        super(cmd, "Embed", CommandCategory.UTILITIES,
                "-title", "-titleurl", "-color", "-author", "-authoricon", "-authorurl",
                "-image", "-description", "-footer", "-footericon", "-thumbnail", "-timestamp");
    }

    @Override
    public void execute(MessageReceivedEvent event, String... args) {
        boolean valid = args.length != 0;
        if (valid) {
            this.processCommand(args);
            for (String arg : arguments.keySet()) {
                if (!Arrays.asList(availableArgs).contains(arg)) {
                    valid = false;
                }
            }
        }

        if (valid) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(arguments.getOrDefault("-title", " "), arguments.getOrDefault("-titleurl", null));
            embed.setColor(getColor(arguments.getOrDefault("-color", "#ffffff")));
            embed.setAuthor(arguments.get("-author"), arguments.get("-authorurl"), arguments.get("-authoricon"));
            embed.setImage(arguments.get("-image"));
            embed.setDescription(arguments.get("-description"));
            embed.setFooter(arguments.get("-footer"), arguments.get("-footericon"));
            embed.setThumbnail(arguments.get("-thumbnail"));
            embed.setTimestamp(arguments.containsKey("-timestamp") ? LocalDate.now().atStartOfDay() : null);
            event.getTextChannel().sendMessage(embed.build()).queue();
        } else {
            sendHelp(event);
        }
        event.getMessage().delete().queue();
    }

}
