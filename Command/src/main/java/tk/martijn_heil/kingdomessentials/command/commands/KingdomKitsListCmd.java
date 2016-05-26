package tk.martijn_heil.kingdomessentials.command.commands;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import tk.martijn_heil.kingdomessentials.playerclass.ModPlayerClass;
import tk.martijn_heil.kingdomessentials.playerclass.model.PlayerClass;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;
import tk.martijn_heil.nincore.api.entity.NinCommandSender;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;

import java.util.Locale;

public class KingdomKitsListCmd extends NinSubCommandExecutor
{
    @Override
    public void execute(CommandSender sender, String[] strings) throws ValidationException, TechnicalException
    {
        Locale locale = NinCommandSender.fromCommandSender(sender).getLocale();

        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6" + ModPlayerClass.getMessages(locale).getString("listOfPlayerClasses") + " §8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");

        int count = 1;

        // List all player classes
        for (PlayerClass playerClass : PlayerClass.getAll())
        {
            sender.sendMessage(ChatColor.GRAY + "" + count + ". " + ChatColor.YELLOW + playerClass.getName());
            count++;
        }
    }
}
