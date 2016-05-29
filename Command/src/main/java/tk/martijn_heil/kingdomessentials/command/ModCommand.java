package tk.martijn_heil.kingdomessentials.command;


import tk.martijn_heil.kingdomessentials.command.commands.KingdomKitsGetClassCmd;
import tk.martijn_heil.kingdomessentials.command.commands.KingdomKitsListCmd;
import tk.martijn_heil.kingdomessentials.command.commands.KingdomKitsSetClassCmd;
import tk.martijn_heil.nincore.api.Core;
import tk.martijn_heil.nincore.api.command.NinCommand;
import tk.martijn_heil.nincore.api.command.builders.CommandBuilder;
import tk.martijn_heil.nincore.api.command.builders.SubCommandBuilder;
import tk.martijn_heil.nincore.api.localization.LocalizedString;
import tk.martijn_heil.nincore.api.logging.LogColor;

import java.util.Locale;
import java.util.ResourceBundle;

public class ModCommand extends Core
{

    @Override
    public void onEnableInner()
    {
        this.getLogger().info("Creating kingdomkits command..");
        CommandBuilder kkBuider = new CommandBuilder(this);
        kkBuider.setName("kingdomessentials");
        kkBuider.setUseStaticDescription(true);
        NinCommand kk = kkBuider.construct();
        kk.addDefaultInfoSubCmd();
        kk.addDefaultHelpSubCmd();

        this.getLogger().info("Creating sub commands..");

        this.getLogger().fine("Creating" + LogColor.HIGHLIGHT + "/kingdomessentials getclass" + LogColor.RESET + " sub command.");
        new SubCommandBuilder()
                .setName("getclass")
                .setUsage("<player=you>")
                .setRequiredPermission("kingdomess.playerclass.getclass")
                .setUseStaticDescription(false)
                .setLocalizedDescription(new LocalizedString(this.getClassLoader(), "tk.martijn_heil.kingdomessentials.command.res.messages", "command.getclass.description"))
                .setParentCommand(kk)
                .setExecutor(new KingdomKitsGetClassCmd())
                .construct();

        this.getLogger().fine("Creating" + LogColor.HIGHLIGHT + "/kingdomessentials list" + LogColor.RESET + " sub command.");
        new SubCommandBuilder()
                .setName("list")
                .setRequiredPermission("kingdomess.playerclass.list")
                .setUseStaticDescription(false)
                .setLocalizedDescription(new LocalizedString(this.getClassLoader(), "tk.martijn_heil.kingdomessentials.command.res.messages", "command.list.description"))
                .setParentCommand(kk)
                .setExecutor(new KingdomKitsListCmd())
                .construct();

        this.getLogger().fine("Creating" + LogColor.HIGHLIGHT + "/kingdomessentials setclass" + LogColor.RESET + " sub command.");
        new SubCommandBuilder()
                .setName("setclass")
                .setUsage("<class> <player=you>")
                .setRequiredPermission("kingdomess.playerclass.setclass")
                .setUseStaticDescription(false)
                .setLocalizedDescription(new LocalizedString(this.getClassLoader(), "tk.martijn_heil.kingdomessentials.command.res.messages", "command.setclass.description"))
                .setParentCommand(kk)
                .setExecutor(new KingdomKitsSetClassCmd())
                .construct();
    }


    public static ResourceBundle getMessages()
    {
        return ResourceBundle.getBundle("tk.martijn_heil.kingdomessentials.command.res.messages");
    }


    public static ResourceBundle getMessages(Locale inLocale)
    {
        return ResourceBundle.getBundle("tk.martijn_heil.kingdomessentials.command.res.messages", inLocale);
    }
}
