package tk.martijn_heil.kingdomessentials.playerclass.event.player;


import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tk.martijn_heil.kingdomessentials.playerclass.model.COfflinePlayer;
import tk.martijn_heil.kingdomessentials.playerclass.model.PlayerClass;

public class PlayerClassSwitchEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private final PlayerClass playerClass;
    private final COfflinePlayer who;

    private boolean cancelled;


    public PlayerClassSwitchEvent(COfflinePlayer who, PlayerClass playerClass)
    {
        this.who = who;
        this.playerClass = playerClass;
    }


    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }


    public static HandlerList getHandlerList()
    {
        return handlers;
    }


    public PlayerClass getPlayerClass()
    {
        return this.playerClass;
    }


    public COfflinePlayer getCOfflinePlayer()
    {
        return this.who;
    }


    @Override
    public boolean isCancelled()
    {
        return this.cancelled;
    }


    @Override
    public void setCancelled(boolean cancel)
    {
        this.cancelled = cancel;
    }
}
