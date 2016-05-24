package tk.martijn_heil.kingdomessentials.item;


import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import tk.martijn_heil.nincore.api.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KingdomEssItem extends Core
{
    @Getter private static KingdomEssItem instance;


    public KingdomEssItem()
    {
        instance = this;
    }

    @Override
    public void onLoadInner()
    {

    }


    @Override
    public void onEnableInner()
    {

    }


    @Override
    public void onDisableInner()
    {

    }

    public List<ItemCategory> getItemCategories()
    {
        List<ItemCategory> categories = new ArrayList<>();
        ConfigurationSection section = this.getConfig().getConfigurationSection("allowed");

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
}
