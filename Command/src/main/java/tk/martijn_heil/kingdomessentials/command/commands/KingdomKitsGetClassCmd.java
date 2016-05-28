package tk.martijn_heil.kingdomessentials.command.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tk.martijn_heil.kingdomessentials.command.ModCommand;
import tk.martijn_heil.kingdomessentials.playerclass.model.COfflinePlayer;
import tk.martijn_heil.kingdomessentials.playerclass.model.COnlinePlayer;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.AccessDeniedException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.NotEnoughArgumentsException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.PlayerNotFoundException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.TooManyArgumentsException;
import tk.martijn_heil.nincore.api.util.TranslationUtils;

public class KingdomKitsGetClassCmd extends NinSubCommandExecutor
{
    @Override
    public void execute(CommandSender sender, String[] args) throws ValidationException, TechnicalException
    {
        if (!sender.hasPermission("kingdomess.playerclass.getclass"))
        {
            throw new AccessDeniedException(sender);
        }

        // If no target player has been given.
        if (args.length == 0)
        {
            if (sender instanceof Player)
            {
                COnlinePlayer cp = new COnlinePlayer(((Player) sender).getUniqueId());
                NinOnlinePlayer np = cp.toNinOnlinePlayer();

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        TranslationUtils.transWithArgs(ModCommand.getMessages(), new Object[]{cp.getPlayerClass().getName()},
                                "command.getclass.youHaveThe.self")));
            }
            else if (sender instanceof ConsoleCommandSender)
            {
                throw new NotEnoughArgumentsException(sender);
            }


        } // Target player has been given
        else if (args.length == 1)
        {
            if (!sender.hasPermission("kingdomess.playerclass.getclass.others"))
                throw new AccessDeniedException(sender);


            String targetPlayer = args[0];
            OfflinePlayer op = Bukkit.getOfflinePlayer(targetPlayer);


            if (op == null) throw new PlayerNotFoundException(sender);

            COfflinePlayer target = new COfflinePlayer(op);


            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    TranslationUtils.transWithArgs(ModCommand.getMessages(), new Object[]{target.toOfflinePlayer().getName(), target.getPlayerClass().getName()},
                            "command.getclass.youHaveThe.other")));

            // Send the player the message..
            sender.sendMessage(ChatColor.DARK_GRAY + targetPlayer + ChatColor.YELLOW + " has the " +
                    ChatColor.DARK_GRAY + target.getPlayerClass().getName() + ChatColor.YELLOW + " class");

        }
        else
        {
            throw new TooManyArgumentsException(sender);
        }
    }
}
