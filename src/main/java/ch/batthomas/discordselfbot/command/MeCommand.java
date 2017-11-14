package ch.batthomas.discordselfbot.command;

import java.util.Arrays;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author batthomas
 */
public class MeCommand extends Command {

    public MeCommand(CommandManager cmd) {
        super(cmd, "Me", "-all");
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
            embed.setColor(getColor("#ffffff"));
            embed.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
            embed.setFooter("batthomas bot", null);
            embed.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
            embed.addField("Discord ID", event.getJDA().getSelfUser().getId(), true);
            event.getTextChannel().sendMessage(embed.build()).queue();
        } else {
            sendHelp(event);
        }
        event.getMessage().delete().queue();
    }

}
