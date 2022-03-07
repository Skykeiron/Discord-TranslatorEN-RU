package bot.commands;

import bot.Bot;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PingCommand extends Command {
    public PingCommand() {
        super.name = "ping";
        super.category = Bot.Command;
        super.help = "Checks the bots current ping to the discord api";
    }

    protected void execute(CommandEvent e) {
        e.reply(e.getMember().getAsMention() + ", My current ping is: " + e.getJDA().getGatewayPing() + " ms!");
    }
}
