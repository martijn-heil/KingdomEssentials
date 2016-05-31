package tk.martijn_heil.kingdomessentials.item.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import tk.martijn_heil.kingdomessentials.item.util.ItemStacks;

public class UnbreakableItemListener implements Listener
{
    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent e)
    {
        if(ItemStacks.isUnbreakable(e.getItem()))
        {
            e.setCancelled(true);
            e.getPlayer().updateInventory();
        }
    }
}
