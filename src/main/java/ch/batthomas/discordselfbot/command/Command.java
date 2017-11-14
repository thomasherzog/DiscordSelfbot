package ch.batthomas.discordselfbot.command;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author batthomas
 */
public abstract class Command {

    protected final String name;
    protected final String[] availableArgs;
    protected Map<String, String> arguments;

    protected final CommandManager cmd;

    public Command(CommandManager cmd, String name, String... availableArgs) {
        this.name = name;
        this.availableArgs = availableArgs;
        this.cmd = cmd;
    }

    public abstract void execute(MessageReceivedEvent event, String... args);

    public void processCommand(String... args) {
        arguments = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        String key = null;
        for (String str : args) {
            if (str.startsWith("-")) {
                if (key != null) {
                    arguments.put(key, sb.toString().length() != 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                    sb = new StringBuilder();
                }
                key = str;
            } else {
                if (key != null && !key.isEmpty()) {
                    sb.append(str).append(" ");
                }
            }
        }
        if (!sb.toString().isEmpty()) {
            arguments.put(key, sb.toString().substring(0, sb.toString().length() - 1));
        } else if (key != null && !key.isEmpty()) {
            arguments.put(key, null);
        }
    }

    protected void sendHelp(MessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        StringBuilder db = embed.getDescriptionBuilder();
        embed.setTitle(name + " Command");
        embed.setColor(getColor("#ff0000"));
        embed.setFooter(event.getJDA().getSelfUser().getName() + " bot", null);
        embed.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        for (String arg : availableArgs) {
            db.append("`").append(arg).append("` ").append(cmd.getI18N().getMessage(name.toLowerCase() + arg.replaceFirst("-", ""))).append("\n");
        }
        event.getTextChannel().sendMessage(embed.build()).queue();
    }

    protected Color getColor(String color) {
        if (color.startsWith("#")) {
            return Color.decode(color);
        }
        return new Color(0, 0, 0);
    }

    public String getName() {
        return cmd.getPrefix() + name;
    }

    public String getDescription() {
        return cmd.getI18N().getMessage(name.toLowerCase() + "desc");
    }

}
