package tk.martijn_heil.kingdomessentials.signs;


import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;

public class SignListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if(e.getClickedBlock() == null) return;
        if(!(e.getClickedBlock().getState() instanceof Sign)) return;

        Sign sign = (Sign) e.getClickedBlock().getState();

        if(!Signs.isKingdomEssSign(sign)) return;

        if(ModSigns.getInstance().getRegister().get(sign.getLine(1)) == null) return; // TODO: send error

        ExecutableSign exSign = ModSigns.getInstance().getRegister().get(sign.getLine(1));
        if(exSign == null) return; // TODO: error

        exSign.execute(e.getPlayer(), sign.getLine(2));
    }


    @EventHandler
    public void onSignChange(SignChangeEvent e)
    {
        if(e.getPlayer().hasPermission("kingdomkits.signs.create.switchclass") &&
                e.getLine(0).equalsIgnoreCase("[KingdomKits]"))
        {
            if(!SignActionType.isValidSignActionType(e.getLine(1)))
            {
                NinOnlinePlayer.fromPlayer(e.getPlayer()).sendError("Invalid sign action type.");
                return;
            }


            // Validation passed, make it a kingdomkits sign.
            e.setLine(0, Signs.getKingdomKitsPrefix());
        }
    }
}
