package tk.martijn_heil.kingdomessentials.signs;


import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;

public class SignListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if(e.getClickedBlock() == null) return;
        if(!(e.getClickedBlock().getState() instanceof Sign)) return;

        Sign sign = (Sign) e.getClickedBlock().getState();

        if(!Signs.isKingdomEssSign(sign)) return;

        ExecutableSign exSign = ModSigns.getInstance().getRegister().get(sign.getLine(1));
        if(exSign == null)
        {
            NinOnlinePlayer np = NinOnlinePlayer.fromPlayer(e.getPlayer());
            NinOnlinePlayer.fromPlayer(e.getPlayer()).sendError(ModSigns.getMessages(np.getLocale()).getString("error.signs.invalidSignActionType"));
            return;
        }

        exSign.execute(e.getPlayer(), sign.getLine(2));
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSignChange(SignChangeEvent e)
    {
        if(e.getPlayer().hasPermission("kingdomkits.signs.create.switchclass") &&
                e.getLine(0).equalsIgnoreCase("[KingdomEss]"))
        {
            ExecutableSign sign = ModSigns.getInstance().getRegister().get(e.getLine(1));

            if(sign == null)
            {
                NinOnlinePlayer np = NinOnlinePlayer.fromPlayer(e.getPlayer());
                NinOnlinePlayer.fromPlayer(e.getPlayer()).sendError(ModSigns.getMessages(np.getLocale()).getString("error.signs.invalidSignActionType"));
                return;
            }


            // Validation passed, make it a kingdomkits sign.
            e.setLine(0, Signs.getKingdomEssPrefix());
        }
    }
}
