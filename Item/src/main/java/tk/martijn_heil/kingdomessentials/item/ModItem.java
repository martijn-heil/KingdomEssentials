package tk.martijn_heil.kingdomessentials.item;


import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import tk.martijn_heil.kingdomessentials.item.listeners.ItemRequireListener;
import tk.martijn_heil.kingdomessentials.item.listeners.SoulboundItemListener;
import tk.martijn_heil.nincore.api.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ModItem extends Core
{
    @Getter
    private static ModItem instance;


    public ModItem()
    {
        instance = this;
    }


    @Override
    public void onEnableInner()
    {
        this.saveDefaultConfig();

        this.getNinLogger().info("Registering event listeners..");
        Bukkit.getPluginManager().registerEvents(new SoulboundItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemRequireListener(), this);
    }


    public List<ItemCategory> getItemCategories()
    {
        List<ItemCategory> categories = new ArrayList<>();
        ConfigurationSection section = this.getConfig().getConfigurationSection("rules");

        for (String key : section.getKeys(false))
        {
            List<Material> items = new ArrayList<>();

            // Add all items belonging to this category to the staging list.
            items.addAll(section.getStringList(key + ".items").stream().map(Material::valueOf).collect(Collectors.toList()));

            // Add this category to the staging list of categories.
            categories.add(
                    new ItemCategory
                            (
                                    items,
                                    section.getBoolean(key + ".require.use-allowed"),
                                    section.getBoolean(key + ".require.combat-allowed"),
                                    section.getBoolean(key + ".require.equip-allowed"),
                                    section.getBoolean(key + ".require.mine-allowed"),
                                    section.getBoolean(key + ".require.consume-allowed"),
                                    section.getBoolean(key + ".require.fire-allowed")
                            )
            );
        }

        return categories;
    }


    public static ResourceBundle getMessages(Locale inLocale)
    {
        return ResourceBundle.getBundle("tk.martijn_heil.kingdomessentials.item.res.messages", inLocale);
    }
}
