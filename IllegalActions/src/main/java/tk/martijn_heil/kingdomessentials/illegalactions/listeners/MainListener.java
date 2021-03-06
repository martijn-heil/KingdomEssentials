package tk.martijn_heil.kingdomessentials.illegalactions.listeners;


import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import tk.martijn_heil.kingdomessentials.illegalactions.ModIllegalActions;
import tk.martijn_heil.kingdomessentials.illegalactions.ModIllegalActions.Permission;
import tk.martijn_heil.kingdomessentials.item.util.ItemStacks;
import tk.martijn_heil.nincore.api.NinCore;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;

import java.util.Arrays;
import java.util.List;

public class MainListener implements Listener
{
    private Configuration config;

    public MainListener(Configuration config)
    {
        this.config = config;
    }


    @EventHandler // If player tries to shoot with a non-soulbound bow.
    public void onEntityShootBow(EntityShootBowEvent e)
    {
        if (ModIllegalActions.getInstance().getConfig().getBoolean("soulbound.preventNonSoulboundWeaponUsage") &&
                e.getEntity() instanceof Player &&
                !ItemStacks.isSoulBound(e.getBow()))
        {
            Player player = (Player) e.getEntity();

            e.setCancelled(true);

            // Update player inventory so that the client doesn't think the arrow was used.
            player.updateInventory();

            NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer(player);
            np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.generic.shootBow"));
        }
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player)
        {
            Player player = (Player) e.getDamager();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

            if(itemInMainHand != null)
            {
                Material itemInMainHandType = itemInMainHand.getType();

                if(ModIllegalActions.getInstance().getConfig().getBoolean("soulbound.preventNonSoulboundWeaponUsage") ||
                        ModIllegalActions.getInstance().getConfig().getBoolean("soulbound.preventNonSoulboundAxeUsage"))
                {
                    // List of weapons
                    Material[] weapons = {
                            Material.WOOD_SWORD,
                            Material.STONE_SWORD,
                            Material.GOLD_SWORD,
                            Material.IRON_SWORD,
                            Material.DIAMOND_SWORD
                    };

                    // List of axes
                    Material[] axes = {
                            Material.WOOD_AXE,
                            Material.STONE_AXE,
                            Material.GOLD_AXE,
                            Material.IRON_AXE,
                            Material.DIAMOND_AXE
                    };



                    if (Arrays.asList(weapons).contains(itemInMainHandType) &&
                            !ItemStacks.isSoulBound(itemInMainHand))
                    {
                        e.setCancelled(true);

                        NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer(player);
                        np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.item.combat"));
                    }
                    else if (Arrays.asList(axes).contains(itemInMainHandType) && !ItemStacks.isSoulBound(itemInMainHand))
                    {
                        e.setCancelled(true);

                        NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer(player);
                        np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.item.combat"));
                    }
                }
            }
        }
    }


    @EventHandler
    public void onEntityToggleGlide(EntityToggleGlideEvent e)
    {
        Entity entity = e.getEntity();

        if (e.isGliding() && entity instanceof Player && ModIllegalActions.getInstance().getConfig().getBoolean("movement.preventElytra") &&
                !Permission.hasPermission(entity, Permission.BYPASS_ELYTRA_DISABLE))
        {
            e.setCancelled(true);

            NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer((Player) e.getEntity());
            np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.movement.glide"));
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (e.getCurrentItem() != null && e.getInventory() != null)
        {
            InventoryType invType = e.getInventory().getType();

            // If clicked inventory is a WORKBENCH or CRAFTING inventory
            if (invType == InventoryType.WORKBENCH || invType == InventoryType.CRAFTING &&
                    !Permission.hasPermission(e.getWhoClicked(), Permission.BYPASS_CRAFT_BLACKLIST))
            {
                List<String> items = config.getStringList("crafting.blacklistedItems");

                if (e.getSlotType().equals(InventoryType.SlotType.RESULT) &&
                        items.contains(e.getCurrentItem().getType().toString()))
                {
                    e.setCancelled(true);
                    NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer((Player) e.getWhoClicked());
                    np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.item.craft"));
                }
            }
            else if (invType == InventoryType.ANVIL && !Permission.hasPermission(e.getWhoClicked(), Permission.BYPASS_ENCHANT_BLACKLIST))
            {
                List<String> items2 = ModIllegalActions.getInstance().getConfig().getStringList("enchanting.blacklistedItems");

                // Foreach blacklisted item list
                for (String item : items2)
                {
                    // Cast material string to material object.
                    Material itemMaterial = Material.getMaterial(item);

                    if (itemMaterial != null && e.getInventory().contains(itemMaterial) &&
                            e.getSlotType().equals(InventoryType.SlotType.RESULT))
                    {
                        // If clicked inventory is an anvil && anvil contains enchanted book + a blacklisted item, cancel the event..
                        e.setCancelled(true);

                        NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer((Player) e.getWhoClicked());
                        np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.block.use.anvil"));
                    }
                }
            }
        }
    }


    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent e)
    {
        if(!Permission.hasPermission(e.getPlayer(), Permission.BYPASS_USE_BLACKLIST))
        {
            if (e.getItem() != null)
            {
                if (config.getList("usage.blacklistedItems").contains(e.getItem().getType().toString()) &&
                        (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
                {
                    e.setCancelled(true);
                    e.getPlayer().updateInventory();

                    NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer(e.getPlayer());
                    np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.item.use"));
                }
            }
        }
    }


    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e)
    {
        if(!Permission.hasPermission(e.getPlayer(), Permission.BYPASS_CONSUME_BLACKLIST))
        {
            if (config.getList("consume.blacklistedItems").contains(e.getItem().getType().toString())
                    && !ItemStacks.isConsumeAllowed(e.getItem()))
            {
                e.setCancelled(true);

                NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer(e.getPlayer());
                np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.item.consume"));
            }

            if (config.getBoolean("potions.disablePotions"))
            {
                if (e.getItem().getType().equals(Material.POTION))
                {
                    PotionMeta potion = (PotionMeta) e.getItem().getItemMeta();

                    if (potion.getBasePotionData().getType() != PotionType.WATER)
                    {
                        e.setCancelled(true);

                        NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer(e.getPlayer());
                        np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.potion.drink"));
                    }
                }
            }
        }
    }


    @EventHandler // Prevent player enchanting soulbound items.
    public void onEnchantItem(EnchantItemEvent e)
    {
        if(!Permission.hasPermission(e.getEnchanter(), Permission.BYPASS_ENCHANT_BLACKLIST))
        {
            List<String> items = config.getStringList("enchanting.blacklistedItems");
            ItemStack enchantedItem = e.getItem();
            Material enchantedItemMaterial = enchantedItem.getType();

            if (items.contains(enchantedItemMaterial.name()))
            {
                // Cancel enchant event if the item is blacklisted
                e.setCancelled(true);

                NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer(e.getEnchanter());
                np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.item.enchant"));
            }
        }
    }


    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent e)
    {
        if (config.getBoolean("potions.disablePotions"))
        {
            Material type = e.getItem().getType();

            if (type == Material.POTION || type == Material.SPLASH_POTION)
            {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        ItemStack item = e.getItem();

        if (config.getBoolean("potions.disablePotions"))
        {
            if (!Permission.hasPermission(e.getPlayer(), Permission.BYPASS_POTION_DISABLE) &&
                    item != null && (item.getType().equals(Material.POTION) || item.getType() == Material.SPLASH_POTION))
            {
                Potion potion = Potion.fromItemStack(item);

                if (potion.getLevel() != 0 && potion.isSplash())
                {
                    e.setCancelled(true);
                    e.getPlayer().updateInventory();

                    NinOnlinePlayer np = NinCore.get().getEntityManager().getNinOnlinePlayer(e.getPlayer());
                    np.sendError(ModIllegalActions.getMessages(np.getLocale()).getString("error.event.cancelled.potion.throw"));
                }
            }
        }
    }


    public void setConfig(Configuration config)
    {
        this.config = config;
    }
}
