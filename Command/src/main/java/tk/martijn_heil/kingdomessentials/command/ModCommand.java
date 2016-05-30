package tk.martijn_heil.kingdomessentials.command;


import lombok.Getter;
import tk.martijn_heil.nincore.api.Core;
import tk.martijn_heil.nincore.api.command.NinCommand;
import tk.martijn_heil.nincore.api.command.builders.CommandBuilder;

import java.util.Locale;
import java.util.ResourceBundle;

public class ModCommand extends Core
{
    @Getter private static NinCommand mainCommand;


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
        mainCommand = kk;
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
