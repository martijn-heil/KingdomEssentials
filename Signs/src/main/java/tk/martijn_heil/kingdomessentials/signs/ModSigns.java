package tk.martijn_heil.kingdomessentials.signs;


import lombok.Getter;
import org.bukkit.Bukkit;
import tk.martijn_heil.nincore.api.Core;

import java.util.Locale;
import java.util.ResourceBundle;

public class ModSigns extends Core
{
    @Getter private final ExecutableSignRegister register = new ExecutableSignRegister();
    @Getter private static ModSigns instance;


    public ModSigns()
    {
        instance = this;
    }


    @Override
    public void onEnableInner()
    {
        this.getNinLogger().info("Registering listeners..");
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);
    }


    public static ResourceBundle getMessages(Locale inLocale)
    {
        return ResourceBundle.getBundle("tk.martijn_heil.kingdomessentials.signs", inLocale);
    }
}
