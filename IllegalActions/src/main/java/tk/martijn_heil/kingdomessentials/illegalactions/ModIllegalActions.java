package tk.martijn_heil.kingdomessentials.illegalactions;


import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permissible;
import tk.martijn_heil.kingdomessentials.illegalactions.listeners.MainListener;
import tk.martijn_heil.nincore.api.Core;

import java.util.Locale;
import java.util.ResourceBundle;

public class ModIllegalActions extends Core
{
    @Getter
    private static ModIllegalActions instance;
    private MainListener listener;


    public ModIllegalActions()
    {
        instance = this;
    }


    @Override
    public void onEnableInner()
    {
        this.saveDefaultConfig();

        this.listener = new MainListener(this.getConfig());

        this.getNinLogger().info("Registering listeners..");
        Bukkit.getPluginManager().registerEvents(listener, this);
    }


    public static ResourceBundle getMessages(Locale inLocale)
    {
        return ResourceBundle.getBundle("tk.martijn_heil.kingdomessentials.illegalactions.res.messages", inLocale);
    }


    @Override
    public void reloadConfig()
    {
        super.reloadConfig();
        listener.setConfig(this.getConfig());
    }


    public enum Permission
    {
        BYPASS_POTION_DISABLE("kingdomess.illegalactions.bypass.potion.disable"),
        BYPASS_ENCHANT_BLACKLIST("kingdomess.illegalactions.bypass.enchant.blacklist"),
        BYPASS_CONSUME_BLACKLIST("kingdomess.illegalactions.bypass.consume.blacklist"),
        BYPASS_USE_BLACKLIST("kingdomess.illegalactions.bypass.use.blacklist"),
        BYPASS_CRAFT_BLACKLIST("kingdomess.illegalactions.bypass.craft.blacklist"),
        BYPASS_ELYTRA_DISABLE("kingdomess.illegalactions.bypass.elytra.disable");


        private String permission;

        Permission(String permission)
        {
            this.permission = permission;
        }

        public String getPermission()
        {
            return permission;
        }


        @Override
        public String toString()
        {
            return permission;
        }


        public static boolean hasPermission(Permissible permissible, Permission permission)
        {
            return permissible.hasPermission(permission.toString());
        }
    }
}
