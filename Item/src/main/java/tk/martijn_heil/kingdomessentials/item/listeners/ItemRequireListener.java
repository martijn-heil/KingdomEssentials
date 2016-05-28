package tk.martijn_heil.kingdomessentials.item.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import tk.martijn_heil.kingdomessentials.item.ItemCategory;
import tk.martijn_heil.kingdomessentials.item.ModItem;
import tk.martijn_heil.kingdomessentials.item.util.ItemCategories;
import tk.martijn_heil.kingdomessentials.item.util.ItemStacks;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;
import tk.martijn_heil.nincore.api.events.ArmorEquipEvent;


public class ItemRequireListener implements Listener
{
    @EventHandler
    public void onUse(PlayerInteractEvent e)
    {
        if (!e.hasItem()) return;

        ItemCategory cat = ItemCategories.getCategory(e.getMaterial());

        if (cat != null && cat.isUseAllowedRequired() && !ItemStacks.isUseAllowed(e.getItem()))
        {
            e.setCancelled(true);

            // send the error.
            NinOnlinePlayer np = NinOnlinePlayer.fromPlayer(e.getPlayer());
            np.sendError(ModItem.getMessages(np.getLocale()).getString("error.event.cancelled.item.use"));
        }
    }


    @EventHandler
    public void onCombat(EntityDamageByEntityEvent e)
    {
        if (!(e.getDamager() instanceof Player)) return;
        if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) return;

        ItemCategory cat = ItemCategories.getCategory(((Player) e.getDamager()).getInventory().getItemInMainHand().getType());

        if (cat != null && cat.isCombatAllowedRequired() &&
                !ItemStacks.isCombatAllowed(((Player) e.getDamager()).getInventory().getItemInMainHand()))
        {
            e.setCancelled(true);

            // send the error.
            NinOnlinePlayer np = NinOnlinePlayer.fromPlayer((Player) e.getDamager());
            np.sendError(ModItem.getMessages(np.getLocale()).getString("error.event.cancelled.item.combat"));
        }
    }


    @EventHandler
    public void onEquip(ArmorEquipEvent e)
    {
        if (e.getNewArmorPiece() == null) return;
        ItemCategory cat = ItemCategories.getCategory(e.getNewArmorPiece().getType());

        if (cat != null && cat.isEquipAllowedRequired() && !ItemStacks.isEquipAllowed(e.getNewArmorPiece()) &&
                !e.getNewArmorPiece().getType().equals(Material.AIR) &&
                (!e.getMethod().equals(ArmorEquipEvent.EquipMethod.DEATH) ||
                        !e.getMethod().equals(ArmorEquipEvent.EquipMethod.BROKE)))
        {
            e.setCancelled(true);

            // send the error.
            NinOnlinePlayer np = NinOnlinePlayer.fromPlayer(e.getPlayer());
            np.sendError(ModItem.getMessages(np.getLocale()).getString("error.event.cancelled.item.equip"));
        }
    }


    @EventHandler
    public void onMine(BlockBreakEvent e)
    {
        ItemCategory cat = ItemCategories.getCategory(e.getPlayer().getInventory().getItemInMainHand().getType());

        if (cat != null && cat.isEquipAllowedRequired() && !ItemStacks.isEquipAllowed(e.getPlayer().getInventory().getItemInMainHand()))
        {
            e.setCancelled(true);

            // send the error.
            NinOnlinePlayer np = NinOnlinePlayer.fromPlayer(e.getPlayer());
            np.sendError(ModItem.getMessages(np.getLocale()).getString("error.event.cancelled.block.mine"));
        }
    }


    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e)
    {
        ItemCategory cat = ItemCategories.getCategory(e.getItem().getType());

        if (cat != null && cat.isRequireConsumeAllowed() && !ItemStacks.isConsumeAllowed(e.getItem()))
        {
            e.setCancelled(true);

            // send the error.
            NinOnlinePlayer np = NinOnlinePlayer.fromPlayer(e.getPlayer());
            np.sendError(ModItem.getMessages(np.getLocale()).getString("error.event.cancelled.item.consume"));
        }
    }
}
