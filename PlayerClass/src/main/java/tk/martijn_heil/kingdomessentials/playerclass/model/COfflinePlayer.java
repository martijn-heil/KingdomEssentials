package tk.martijn_heil.kingdomessentials.playerclass.model;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import tk.martijn_heil.kingdomessentials.playerclass.ModPlayerClass;
import tk.martijn_heil.kingdomessentials.playerclass.Players;
import tk.martijn_heil.kingdomessentials.playerclass.event.player.PlayerClassSwitchEvent;
import tk.martijn_heil.nincore.api.entity.NinOfflinePlayer;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;
import tk.martijn_heil.nincore.api.util.Events;
import tk.martijn_heil.nincore.api.util.TranslationUtils;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public class COfflinePlayer
{
    private OfflinePlayer offlinePlayer;


    /**
     * @param uuid The UUID for the related {@link OfflinePlayer}
     * @throws NullPointerException if uuid is null.
     * @throws IllegalArgumentException if no {@link OfflinePlayer} with the given uuid can be found.
     */
    public COfflinePlayer(@NotNull UUID uuid)
    {
        checkNotNull(uuid, "uuid can not be null.");

        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer == null) throw new IllegalArgumentException(String.format("No OfflinePlayer found with UUID %s", uuid));

        if(!ModPlayerClass.getInstance().getRawData().getKeys(false).contains(uuid.toString()))
            Players.populateData(this.offlinePlayer);
    }


    /**
     * @param p The related {@link OfflinePlayer}
     * @throws NullPointerException if p is null.
     */
    public COfflinePlayer(@NotNull OfflinePlayer p)
    {
        checkNotNull(p, "p can not be null.");

        this.offlinePlayer = p;

        if(!ModPlayerClass.getInstance().getRawData().getKeys(false).contains(offlinePlayer.getUniqueId().toString()))
            Players.populateData(this.offlinePlayer);
    }


    public NinOfflinePlayer toNinOfflinePlayer()
    {
        return NinOfflinePlayer.fromOfflinePlayer(this.offlinePlayer);
    }


    public OfflinePlayer toOfflinePlayer()
    {
        return this.offlinePlayer;
    }


    /**
     * Get a player's class
     *
     * @return The player's class object
     */
    public PlayerClass getPlayerClass()
    {
        return new PlayerClass(ModPlayerClass.getInstance().getRawData().getString(offlinePlayer.getUniqueId() + ".class"));
    }


    /**
     * Set the player's class.
     *
     * @param className The class name.
     * @throws NullPointerException if className is null.
     */
    public void setPlayerClass(@NotNull String className)
    {
        checkNotNull(className, "className can not be null.");
        this.setPlayerClass(new PlayerClass(className), true);
    }


    /**
     * Set the player's class.
     *
     * @param className    The class name.
     * @param withCoolDown Add cooldown value to player section in data file?
     */
    public void setPlayerClass(String className, boolean withCoolDown)
    {
        this.setPlayerClass(new PlayerClass(className), withCoolDown);
    }


    @Deprecated
    public void setPlayerClass(PlayerClass playerClass)
    {
        this.setPlayerClass(playerClass, true);
    }


    public void setPlayerClass(PlayerClass playerClass, boolean withCoolDown)
    {
        this.setPlayerClass(playerClass, withCoolDown, false);
    }


    public void setPlayerClass(PlayerClass playerClass, boolean withCoolDown, boolean silent)
    {
        if(Events.attempt(new PlayerClassSwitchEvent(this, playerClass))) return;


        if (this.toOfflinePlayer().isOnline())
        {
            new COnlinePlayer(this.toOfflinePlayer().getPlayer()).removePlayerClassKit();
        }

        ModPlayerClass.getInstance().getRawData().set(offlinePlayer.getUniqueId() + ".class", playerClass.getName());

        if (withCoolDown)
        {
            DateTime currentDateTime = new DateTime();
            DateTime nextPossibleClassSwitchTime = currentDateTime.plusMinutes(
                    ModPlayerClass.getInstance().getConfig().getInt("classes.coolDownInMinutes"));
            ModPlayerClass.getInstance().getRawData().set(offlinePlayer.getUniqueId() + ".nextPossibleClassSwitchTime",
                    nextPossibleClassSwitchTime.toString());
        }

        if (this.toOfflinePlayer().isOnline())
        {
            new COnlinePlayer(this.offlinePlayer.getUniqueId()).givePlayerClassKit();
            NinOnlinePlayer np = NinOnlinePlayer.fromPlayer(this.toOfflinePlayer().getPlayer());

            if (!silent)
            {
                this.toOfflinePlayer().getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        TranslationUtils.transWithArgs(ModPlayerClass.getMessages(np.getLocale()),
                                new Object[]{playerClass.getName()}, "playerclass.switchedPlayerClass")));
            }
        }
    }


    /**
     * Move the player to the default player class.
     */
    public void moveToDefaultPlayerClass()
    {
        this.setPlayerClass(PlayerClass.getDefault(), false);
    }


    /**
     * Move the player to the default player class.
     */
    public void moveToDefaultPlayerClass(boolean withCoolDown)
    {
        this.setPlayerClass(PlayerClass.getDefault(), withCoolDown);
    }


    /**
     * Check if the cooldown has expired.
     *
     * @return True if the coolDown has expired
     */
    public boolean hasPlayerClassSwitchCoolDownExpired()
    {
        String nextPossibleClassSwitchTime = ModPlayerClass.getInstance().getRawData()
                .getString(offlinePlayer.getUniqueId() + ".nextPossibleClassSwitchTime");

        return nextPossibleClassSwitchTime != null && new DateTime(nextPossibleClassSwitchTime).isBeforeNow();
    }


    /**
     * Get the next possible time to switch player class for this player.
     *
     * @return Next possible time to switch player class.
     */
    @Nullable
    public DateTime getNextPossibleClassSwitchTime()
    {
        return DateTime.parse(ModPlayerClass.getInstance().getRawData().getString(offlinePlayer.getUniqueId() + ".nextPossibleClassSwitchTime"));
    }


    public boolean canBecomeClass(String className)
    {
        checkArgument(PlayerClass.PlayerClassExists(className), String.format("Player class with name '%s' does not exist.", className));
        return this.canBecomeClass(new PlayerClass(className));
    }


    public boolean canBecomeClass(PlayerClass playerClass)
    {
        return ModPlayerClass.getInstance().getFactionsHook().canBecomeClass(this.toOfflinePlayer(), playerClass);
    }
}
