package ch.batthomas.discordselfbot.command.searches;

import ch.batthomas.discordselfbot.command.Command;
import ch.batthomas.discordselfbot.command.CommandCategory;
import ch.batthomas.discordselfbot.command.CommandManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

/**
 *
 * @author batthomas
 */
public class UrbanDictionaryCommand extends Command {

    public UrbanDictionaryCommand(CommandManager cmd) {
        super(cmd, "UrbanDictionary", CommandCategory.SEARCHES,
                "-word");
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
            if (arguments.get("-word") == null) {
                valid = false;
            }
        }

        if (valid) {
            try {
                EmbedBuilder embed = new EmbedBuilder();
                StringBuilder db = embed.getDescriptionBuilder();
                String reponse = getGetResponse("http://api.urbandictionary.com/v0/define?term=" + arguments.get("-word"));
                JSONObject json = new JSONObject(reponse);
                if (!json.getString("result_type").equalsIgnoreCase("no_results")) {
                    embed.setAuthor("UrbanDictionary Command", "https://www.urbandictionary.com/define.php?term=" + arguments.get("-word"), "https://i.imgur.com/jwGtc6U.png");
                    JSONObject obj = json.getJSONArray("list").getJSONObject(0);
                    embed.addField("Author", obj.getString("author"), false);
                    embed.addField("Definition for " + obj.getString("word"), obj.getString("definition"), false);
                    embed.addField("Example", obj.getString("example"), false);
                } else {
                    db.append("Not results found.");
                    embed.setAuthor("UrbanDictionary Command", null, "https://i.imgur.com/jwGtc6U.png");
                }

                embed.setColor(getColor("#ffffff"));
                embed.setFooter(event.getMessage().getJDA().getSelfUser().getName() + " bot", null);
                event.getTextChannel().sendMessage(embed.build()).queue();
                event.getMessage().delete().queue();
            } catch (IOException ex) {
                Logger.getLogger(UrbanDictionaryCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            sendHelp(event);
        }
        event.getMessage().delete().queue();
    }

    private String getGetResponse(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response.toString();
    }

}
