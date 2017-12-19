package ch.batthomas.discordselfbot.command.core;

import ch.batthomas.discordselfbot.command.Command;
import ch.batthomas.discordselfbot.command.CommandCategory;
import ch.batthomas.discordselfbot.command.CommandManager;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author batthomas
 */
public class HelpCommand extends Command {

    public HelpCommand(CommandManager cmd) {
        super(cmd, "Help", CommandCategory.CORE,
                "-module", "-page");
    }

    @Override
    public void execute(MessageReceivedEvent event, String... args) {
        boolean valid = true;
        this.processCommand(args);
        if (args.length != 0) {
            for (String arg : arguments.keySet()) {
                if (!Arrays.asList(availableArgs).contains(arg)) {
                    valid = false;
                }
            }
        }

        int page = 1;
        if (arguments.get("-page") != null && !arguments.get("-page").isEmpty() && arguments.get("-page").matches("^-?\\d+$")
                && Integer.parseInt(arguments.get("-page")) * 10 - 10 < cmd.getCommands().size()
                && Integer.parseInt((arguments.get("-page"))) * 10 - 10 > -1) {
            page = Integer.parseInt((arguments.get("-page")));
        }

        List<Command> commands;
        if (arguments.get("-module") != null
                && Arrays.asList(CommandCategory.values()).stream().anyMatch(p -> p.name().equalsIgnoreCase(arguments.get("-module")))
                && cmd.getCategoryStatus(CommandCategory.valueOf(arguments.get("-module")))) {
            commands = cmd.getCommands().values().stream().filter(p -> p.getCategory().equals(CommandCategory.valueOf(arguments.get("-module")))).collect(Collectors.toList());
        } else {
            commands = cmd.getCommands().values().stream().collect(Collectors.toList());
        }

        if (valid) {
            EmbedBuilder embed = new EmbedBuilder();
            StringBuilder db = embed.getDescriptionBuilder();
            embed.setColor(getColor("#ffffff"));
            embed.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
            embed.setTitle("Help Command - Page " + page + "/" + getPages());
            embed.setFooter(event.getMessage().getJDA().getSelfUser().getName() + " bot", null);
            for (int index = page * 10 - 10; index < (cmd.getCommands().size() > page * 10 ? page * 10 : cmd.getCommands().size()); index++) {
                db.append("`").append(commands.get(index).getName().toLowerCase()).append("` ").append(commands.get(index).getDescription()).append("\n");
            }
            event.getTextChannel().sendMessage(embed.build()).queue();
            event.getMessage().delete().queue();
        } else {
            sendHelp(event);
        }
    }

    private int getPages() {
        return (int) Math.ceil((cmd.getCommands().size() * 1.0 / 10) != 0 ? Math.ceil((cmd.getCommands().size() * 1.0 / 10)) : 1);
    }
}
