package ch.batthomas.discordselfbot.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author batthomas
 */
public class HelpCommand extends Command {

    public HelpCommand(CommandManager cmd) {
        super(cmd, "Help");
    }

    @Override
    public void execute(MessageReceivedEvent event, String... args) {
        EmbedBuilder embed = new EmbedBuilder();
        StringBuilder db = embed.getDescriptionBuilder();
        embed.setColor(getColor("#ffffff"));
        embed.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        embed.setTitle("Help Command");
        embed.setFooter("batthomas bot", null);
        for (Command command : cmd.getCommands().values()) {
            db.append("`").append(command.getName().toLowerCase()).append("` ").append(command.getDescription()).append("\n");
        }
        event.getTextChannel().sendMessage(embed.build()).queue();
        event.getMessage().delete().queue();
    }

}
