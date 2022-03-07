package bot.translation;

import bot.Bot;
import bot.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateListener extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String messageSent = e.getMessage().getContentRaw();
        String name = e.getAuthor().getName().toLowerCase();
        String id = e.getAuthor().getDiscriminator();
        if (e.getMessage().getChannel().getName().equalsIgnoreCase(Config.get("CHANNEL"))){
            if (name.equalsIgnoreCase(Config.get("BOT_NAME")) || messageSent.startsWith(Config.get("PREFIX")) || !TranslateWhitelist.isWhiteListed(name + id)
                    || e.getMessage().getAttachments().size() > 0 || messageSent.toLowerCase().contains("http"))
                return;
            try {
                e.getMessage().reply(translate(messageSent, e)).queue();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean isEnglish(String text) {
        for (char c : text.toCharArray()) {
            if (c > 127)
                return false;
        }
        return true;
    }

    private static String translate(String text, GuildMessageReceivedEvent e) throws IOException {
        String target = "";
        String original = "";
        if (isEnglish(text)) {
            target = "ru";
            original = "en";
        } else {
            target = "en";
            original = "ru";
        }
        String urlStr = "https://script.google.com/macros/s/AKfycby-HQVDd1B1bIc7hWdLlrT8adWQzGxJ-ITBBS_Q-9rweq57eije/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + target +
                "&source=" + original;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.replaceAll("&#39;", "'"));
        }
        in.close();
        return response.toString();
    }

}