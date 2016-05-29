package tk.martijn_heil.kingdomessentials.playerclass;


import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.martijn_heil.kingdomessentials.playerclass.hooks.FactionsHook;
import tk.martijn_heil.kingdomessentials.playerclass.hooks.ModSignsHook;
import tk.martijn_heil.kingdomessentials.playerclass.hooks.PlaceHolderApiHook;
import tk.martijn_heil.kingdomessentials.playerclass.listeners.PlayerListener;
import tk.martijn_heil.nincore.api.Core;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.google.common.base.Preconditions.checkNotNull;

public class ModPlayerClass extends Core
{
    @Getter private static ModPlayerClass instance;
    @Getter private PlaceHolderApiHook placeHolderApiHook;
    @Getter private FactionsHook factionsHook;
    @Getter private ModSignsHook modSignsHook;


    public ModPlayerClass()
    {
        instance = this;
    }


    @Override
    public void onEnableInner()
    {
        this.saveDefaultConfig();

        if (!this.getDataManager().dataFileExists())
        {
            this.getDataManager().createDataFile();
        }

        this.getDataManager().loadDataFile();
        this.getDataManager().scheduleAutomaticDataFileSave(6000);

        this.placeHolderApiHook = new PlaceHolderApiHook();
        this.factionsHook = new FactionsHook();
        this.modSignsHook = new ModSignsHook();

        this.getNinLogger().info("Registering event listeners..");
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }


    public FileConfiguration getRawData()
    {
        return this.getDataManager().getData();
    }


    /**
     * Check if an item is part of a given kit. The item is known to be part of a certain kit if the lore contains
     * {@literal '§b§oKitNameHere'}
     *
     * @param item    The {@link ItemStack} to check.
     * @param kitName The name of the kit to check if this item is part of it.
     * @return true if the item is part of this kit.
     */
    @Contract("null, _ -> false")
    public static boolean isPartOfKit(@Nullable ItemStack item, @Nullable String kitName)
    {
        return (item != null) && (item.getItemMeta().getLore() != null) && (item.getItemMeta().getLore() != null) &&
                item.getItemMeta().getLore().contains("§b§o" + kitName);
        // NOTE: §b instead of §6
    }


    public static ResourceBundle getMessages(@NotNull Locale inLocale)
    {
        checkNotNull(inLocale, "inLocale can not be null.");
        return ResourceBundle.getBundle("tk.martijn_heil.kingdomessentials.playerclass.res.messages", inLocale);
    }


    public static ResourceBundle getMessages()
    {
        return ResourceBundle.getBundle("tk.martijn_heil.kingdomessentials.playerclass.res.messages");
    }
}
