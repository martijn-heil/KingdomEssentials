package tk.martijn_heil.kingdomessentials.playerclass.hooks;


import org.bukkit.Bukkit;
import tk.martijn_heil.kingdomessentials.command.ModCommand;
import tk.martijn_heil.kingdomessentials.playerclass.hooks.commands.KingdomKitsGetClassCmd;
import tk.martijn_heil.kingdomessentials.playerclass.hooks.commands.KingdomKitsListCmd;
import tk.martijn_heil.kingdomessentials.playerclass.hooks.commands.KingdomKitsSetClassCmd;
import tk.martijn_heil.nincore.api.command.NinCommand;
import tk.martijn_heil.nincore.api.command.builders.SubCommandBuilder;
import tk.martijn_heil.nincore.api.localization.LocalizedString;
import tk.martijn_heil.nincore.api.logging.LogColor;

import java.util.logging.Logger;

public class ModCommandHook
{
    public ModCommandHook(Logger logger)
    {
        if(Bukkit.getPluginManager().isPluginEnabled("KE-ModCommand"))
        {
            logger.info("ModCommand detected, hooking in..");
            new Hook(logger);
        }
    }

    private class Hook
    {
        public Hook(Logger logger)
        {
            NinCommand kk = ModCommand.getMainCommand();

            logger.info("Creating sub commands..");

            logger.fine("Creating" + LogColor.HIGHLIGHT + "/kingdomessentials getclass" + LogColor.RESET + " sub command.");
            new SubCommandBuilder()
                    .setName("getclass")
                    .setUsage("<player=you>")
                    .setRequiredPermission("kingdomess.playerclass.getclass")
                    .setUseStaticDescription(false)
                    .setLocalizedDescription(new LocalizedString(this.getClass().getClassLoader(), "tk.martijn_heil.kingdomessentials.playerclass.res.messages", "command.getclass.description"))
                    .setParentCommand(kk)
                    .setExecutor(new KingdomKitsGetClassCmd())
                    .construct();

            logger.fine("Creating" + LogColor.HIGHLIGHT + "/kingdomessentials list" + LogColor.RESET + " sub command.");
            new SubCommandBuilder()
                    .setName("list")
                    .setRequiredPermission("kingdomess.playerclass.list")
                    .setUseStaticDescription(false)
                    .setLocalizedDescription(new LocalizedString(this.getClass().getClassLoader(), "tk.martijn_heil.kingdomessentials.playerclass.res.messages", "command.list.description"))
                    .setParentCommand(kk)
                    .setExecutor(new KingdomKitsListCmd())
                    .construct();

            logger.fine("Creating" + LogColor.HIGHLIGHT + "/kingdomessentials setclass" + LogColor.RESET + " sub command.");
            new SubCommandBuilder()
                    .setName("setclass")
                    .setUsage("<class> <player=you>")
                    .setRequiredPermission("kingdomess.playerclass.setclass")
                    .setUseStaticDescription(false)
                    .setLocalizedDescription(new LocalizedString(this.getClass().getClassLoader(), "tk.martijn_heil.kingdomessentials.playerclass.res.messages", "command.setclass.description"))
                    .setParentCommand(kk)
                    .setExecutor(new KingdomKitsSetClassCmd())
                    .construct();
        }
    }
}
