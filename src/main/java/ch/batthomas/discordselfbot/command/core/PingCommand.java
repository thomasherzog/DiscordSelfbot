package ch.batthomas.discordselfbot.command.core;

import ch.batthomas.discordselfbot.command.Command;
import ch.batthomas.discordselfbot.command.CommandCategory;
import ch.batthomas.discordselfbot.command.CommandManager;
import java.time.temporal.ChronoUnit;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author batthomas
 */
public class PingCommand extends Command {

    public PingCommand(CommandManager cmd) {
        super(cmd, "Ping", CommandCategory.CORE);
    }

    @Override
    public void execute(MessageReceivedEvent event, String... args) {
        event.getMessage().editMessage(cmd.getPrefix() + this.name.toLowerCase()).queue((msg) -> {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(getColor("#ffffff"));
            embed.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
            embed.setTitle("Ping Command");
            embed.setFooter(msg.getJDA().getSelfUser().getName() + " bot", null);
            embed.appendDescription("**" + event.getJDA().getSelfUser().getName() + " :ping_pong: " + msg.getCreationTime().until(msg.getEditedTime(), ChronoUnit.MICROS) + "μs**");
            event.getTextChannel().sendMessage(embed.build()).queue();
            msg.delete().queue();
        });
    }

}
