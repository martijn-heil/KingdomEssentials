package tk.martijn_heil.kingdomessentials.illegalactions;


import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permissible;
import tk.martijn_heil.kingdomessentials.illegalactions.listeners.EntityListener;
import tk.martijn_heil.kingdomessentials.illegalactions.listeners.InventoryListener;
import tk.martijn_heil.kingdomessentials.illegalactions.listeners.PlayerListener;
import tk.martijn_heil.nincore.api.Core;

import java.util.Locale;
import java.util.ResourceBundle;

public class ModIllegalActions extends Core
{
    @Getter
    private static ModIllegalActions instance;


    public ModIllegalActions()
    {
        instance = this;
    }


    @Override
    public void onEnableInner()
    {
        this.saveDefaultConfig();

        this.getNinLogger().info("Registering listeners..");
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }


    public static ResourceBundle getMessages(Locale inLocale)
    {
        return ResourceBundle.getBundle("tk.martijn_heil.kingdomessentials.illegalactions.res.messages", inLocale);
    }

    public enum Permission
    {
        BYPASS_POTION_DISABLE("kingdomess.illegalactions.bypass.potion.disable"),
        BYPASS_ENCHANT_BLACKLIST("kingdomess.illegalactions.bypass.enchant.blacklist"),
        BYPASS_CONSUME_BLACKLIST("kingdomess.illegalactions.bypass.consume.blacklist"),
        BYPASS_USE_BLACKLIST("kingdomess.illegalactions.bypass.use.blacklist"),
        BYPASS_CRAFT_BLACKLIST("kingdomess.illegalactions.bypass.craft.blacklist");


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
