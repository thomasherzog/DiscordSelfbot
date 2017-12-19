package ch.batthomas.discordselfbot.command.utilities;

import ch.batthomas.discordselfbot.command.Command;
import ch.batthomas.discordselfbot.command.CommandCategory;
import ch.batthomas.discordselfbot.command.CommandManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author batthomas
 */
public class MeCommand extends Command {

    public MeCommand(CommandManager cmd) {
        super(cmd, "Me", CommandCategory.UTILITIES);
    }

    @Override
    public void execute(MessageReceivedEvent event, String... args) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(getColor("#ffffff"));
        embed.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        embed.setFooter(event.getMessage().getJDA().getSelfUser().getName() + " bot", null);
        embed.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        embed.addField("Discord ID", event.getJDA().getSelfUser().getId(), true);
        event.getTextChannel().sendMessage(embed.build()).queue();
        event.getMessage().delete().queue();
    }

}
