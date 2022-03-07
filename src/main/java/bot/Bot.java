package bot;

import bot.commands.translation.whiteListCommand;
import bot.translation.TranslateListener;
import bot.translation.TranslateWhitelist;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import bot.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Bot {


    public static final Command.Category Command = new Command.Category("Translator Commands");


    private Bot() throws LoginException {
        final JDA jda = new JDABuilder().setToken(Config.get("TOKEN")).build();

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setPrefix(Config.get("PREFIX"));
        builder.setOwnerId(Config.get("OWNER_ID"));
        builder.setHelpWord("help");

        //Translation Commands
        builder.addCommand(new whiteListCommand());

        //Load translation list
        try {
            TranslateWhitelist.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Other Commands
        builder.addCommand(new PingCommand());

        CommandClient client = builder.build();
        jda.addEventListener(client);

        //Russian + Eng translation
        jda.addEventListener(new TranslateListener());
    }


    public static void main(String[] ags) throws LoginException {
        long enable = System.currentTimeMillis();
        new Bot();
        System.out.println("Keiron Bot has started up in " + (System.currentTimeMillis() - enable) + " milliseconds.");
    }
}
