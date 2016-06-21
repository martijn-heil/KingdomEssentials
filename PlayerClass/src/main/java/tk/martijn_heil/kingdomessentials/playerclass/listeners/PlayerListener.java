package tk.martijn_heil.kingdomessentials.playerclass.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import tk.martijn_heil.kingdomessentials.playerclass.ModPlayerClass;
import tk.martijn_heil.kingdomessentials.playerclass.event.player.PlayerClassSwitchEvent;
import tk.martijn_heil.kingdomessentials.playerclass.model.COnlinePlayer;
import tk.martijn_heil.nincore.api.util.ServerUtils;

public class PlayerListener implements Listener
{
    @EventHandler // Give the player his class kit if the joins for the first time.
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        // Player is new.
        if (!ModPlayerClass.getInstance().getRawData().isSet(e.getPlayer().getUniqueId().toString()))
        {
            COnlinePlayer cp = new COnlinePlayer(e.getPlayer().getUniqueId());

            for (String cmd : cp.getPlayerClass().getCmdsExecutedOnPlayerRespawn())
            {
                cmd = ModPlayerClass.getInstance().getPlaceHolderApiHook().parse(e.getPlayer(), cmd);
                ServerUtils.dispatchCommand(cmd);
            }

            // Give player default class kit
            if (ModPlayerClass.getInstance().getConfig().getBoolean("classes.giveKitOnRespawn"))
            {
                cp.givePlayerClassKit();
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST) // Give the player his class kit on respawn..
    public void onPlayerRespawn(PlayerRespawnEvent e)
    {
        COnlinePlayer cOnlinePlayer = new COnlinePlayer(e.getPlayer().getUniqueId());


        if (ModPlayerClass.getInstance().getConfig().getBoolean("classes.giveKitOnRespawn"))
        {
            cOnlinePlayer.givePlayerClassKit();
        }

        for (String cmd : cOnlinePlayer.getPlayerClass().getCmdsExecutedOnPlayerRespawn())
        {
            cmd = ModPlayerClass.getInstance().getPlaceHolderApiHook().parse(e.getPlayer(), cmd);
            ServerUtils.dispatchCommand(cmd);
        }
    }


    @EventHandler
    public void onPlayerClassSwitch(PlayerClassSwitchEvent e)
    {
        ModPlayerClass.getInstance().getNinLogger().info(String.format("%s's player class has been set to '%s'",
                e.getCOfflinePlayer().toOfflinePlayer().getName(), e.getPlayerClass().getId()));
    }
}
