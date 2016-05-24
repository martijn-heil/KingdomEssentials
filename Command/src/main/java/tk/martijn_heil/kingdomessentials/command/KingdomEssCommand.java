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

public class KingdomEssCommand extends Core
{

    @Override
    public void onLoadInner()
    {

    }


    @Override
    public void onEnableInner()
    {
        this.getLogger().info("Creating kingdomkits command..");
        CommandBuilder kkBuider = new CommandBuilder(this);
        kkBuider.setName("kingdomkits");
        kkBuider.setUseStaticDescription(true);
        NinCommand kk = kkBuider.construct();
        kk.addDefaultInfoSubCmd();
        kk.addDefaultHelpSubCmd();

        this.getLogger().info("Creating sub commands..");

//        this.getLogger().fine("Creating" + LogColor.HIGHLIGHT + "/kingdomkits bind" + LogColor.RESET + " sub command.");
//        new SubCommandBuilder()
//                .setName("bind")
//                .setRequiredPermission("kingdomkits.bind")
//                .setUseStaticDescription(false)
//                .setLocalizedDescription(new LocalizedString(KingdomKits.class.getClassLoader(), "lang.subCmdMessages", "bind.desc"))
//                .setParentCommand(kk)
//                .setExecutor(new KingdomKitsBindCmd())
//                .construct();

        this.getLogger().fine("Creating" + LogColor.HIGHLIGHT + "/kingdomkits getclass" + LogColor.RESET + " sub command.");
        new SubCommandBuilder()
                .setName("getclass")
                .setUsage("<player=you>")
                .setRequiredPermission("kingdomkits.getclass")
                .setUseStaticDescription(false)
                .setLocalizedDescription(new LocalizedString(this.getClassLoader(), "lang.subCmdMessages", "getclass.desc"))
                .setParentCommand(kk)
                .setExecutor(new KingdomKitsGetClassCmd())
                .construct();

        this.getLogger().fine("Creating" + LogColor.HIGHLIGHT + "/kingdomkits list" + LogColor.RESET + " sub command.");
        new SubCommandBuilder()
                .setName("list")
                .setRequiredPermission("kingdomkits.list")
                .setUseStaticDescription(false)
                .setLocalizedDescription(new LocalizedString(this.getClassLoader(), "lang.subCmdMessages", "list.desc"))
                .setParentCommand(kk)
                .setExecutor(new KingdomKitsListCmd())
                .construct();

        this.getLogger().fine("Creating" + LogColor.HIGHLIGHT + "/kingdomkits setclass" + LogColor.RESET + " sub command.");
        new SubCommandBuilder()
                .setName("setclass")
                .setUsage("<class> <player=you>")
                .setRequiredPermission("kingdomkits.setclass")
                .setUseStaticDescription(false)
                .setLocalizedDescription(new LocalizedString(this.getClassLoader(), "lang.subCmdMessages", "setclass.desc"))
                .setParentCommand(kk)
                .setExecutor(new KingdomKitsSetClassCmd())
                .construct();
    }


    @Override
    public void onDisableInner()
    {

    }
}
