package tk.martijn_heil.kingdomessentials.playerclass;


import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import tk.martijn_heil.kingdomessentials.playerclass.hooks.FactionsHook;
import tk.martijn_heil.kingdomessentials.playerclass.hooks.PlaceHolderApiHook;
import tk.martijn_heil.nincore.api.Core;

import static com.google.common.base.Preconditions.checkNotNull;

public class KingdomEssPlayerClass extends Core
{
    @Getter
    private static KingdomEssPlayerClass instance;


    @Getter private PlaceHolderApiHook placeHolderApiHook;
    @Getter private FactionsHook factionsHook;


    public KingdomEssPlayerClass()
    {
        instance = this;
    }


    @Override
    public void onEnableInner()
    {
        this.placeHolderApiHook = new PlaceHolderApiHook();
        this.factionsHook = new FactionsHook();

        this.saveDefaultConfig();

        if(!this.getDataManager().dataFileExists())
        {
            this.getDataManager().createDataFile();
        }

        this.getDataManager().loadDataFile();
        this.getDataManager().scheduleAutomaticDataFileSave(6000);
    }


    public FileConfiguration getRawData()
    {
        return this.getDataManager().getData();
    }


    /**
     * Check if an item is part of a given kit. The item is known to be part of a certain kit if the lore contains
     * {@literal '§b§oKitNameHere'}
     *
     * @param item The {@link ItemStack} to check.
     * @param kitName The name of the kit to check if this item is part of it.
     * @return true if the item is part of this kit.
     * @throws NullPointerException if kitName is null.
     */
    public static boolean isPartOfKit(ItemStack item, String kitName)
    {
        checkNotNull(kitName, "kitName can not be null.");
        return (item != null) && (item.getItemMeta().getLore() != null) && (item.getItemMeta().getLore() != null) &&
                item.getItemMeta().getLore().contains("§b§o" + kitName);
                                                // NOTE: §b instead of §6
    }
}
