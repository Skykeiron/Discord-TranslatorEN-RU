package bot.commands.translation;

import bot.Bot;
import bot.translation.TranslateWhitelist;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class whiteListCommand extends Command {

    public whiteListCommand() {
        super.name = "whitelist";
        super.category = Bot.Command;
        super.help = "Adds or removes you from a Whitelist to translate your messages to english or russian";
        this.aliases = new String[] {"unwhitelist"};
    }

    @Override
    protected void execute(CommandEvent e) {
        String name = e.getAuthor().getName();
        String id = e.getAuthor().getDiscriminator();
        if (TranslateWhitelist.isWhiteListed(name.toLowerCase() + id))
            TranslateWhitelist.remove(name.toLowerCase() + id);
        else
            TranslateWhitelist.add(name.toLowerCase() + "" + id);
        e.reply("User **" + name + "#" + id + "** has been " + (TranslateWhitelist.isWhiteListed(name.toLowerCase() + "" + id) ? "**Added** to" : "**Removed** from") + " the Translation Whitelist!");
    }
}
