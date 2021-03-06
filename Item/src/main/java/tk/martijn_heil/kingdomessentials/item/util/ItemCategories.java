package tk.martijn_heil.kingdomessentials.item.util;


import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import tk.martijn_heil.kingdomessentials.item.ItemCategory;
import tk.martijn_heil.kingdomessentials.item.ModItem;

public class ItemCategories
{

    @Nullable
    public static ItemCategory getCategory(Material material)
    {
        for (ItemCategory category : ModItem.getInstance().getItemCategories())
        {
            if (category.getItems().contains(material)) return category;
        }

        return null;
    }
}
