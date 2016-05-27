package tk.martijn_heil.kingdomessentials.playerclass.model;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import tk.martijn_heil.kingdomessentials.playerclass.ModPlayerClass;
import tk.martijn_heil.kingdomessentials.playerclass.Players;
import tk.martijn_heil.nincore.api.entity.NinOfflinePlayer;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;
import tk.martijn_heil.nincore.api.util.TranslationUtils;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public class COfflinePlayer
{
    private OfflinePlayer offlinePlayer;

    private PlayerClass playerClass;

    @Nullable
    private DateTime nextPossibleClassSwitchTime;


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

        this.playerClass = new PlayerClass(ModPlayerClass.getInstance().getRawData().getString(offlinePlayer.getUniqueId() + ".class"));

        String nextPossibleClassSwitchTime = ModPlayerClass.getInstance().getRawData()
                .getString(offlinePlayer.getUniqueId() + ".nextPossibleClassSwitchTime");

        if (nextPossibleClassSwitchTime != null)
            this.nextPossibleClassSwitchTime = DateTime.parse(nextPossibleClassSwitchTime);
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


        this.playerClass = new PlayerClass(ModPlayerClass.getInstance().getRawData().getString(offlinePlayer.getUniqueId() + ".class"));
        this.nextPossibleClassSwitchTime = new DateTime(ModPlayerClass.getInstance().getRawData()
                .getString(offlinePlayer.getUniqueId() + ".nextPossibleClassSwitchTime"));
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
        return this.playerClass;
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
     * @throws NullPointerException if className is null.
     */
    public void setPlayerClass(@NotNull String className, boolean withCoolDown)
    {
        checkNotNull(className, "className can not be null.");
        this.setPlayerClass(new PlayerClass(className), withCoolDown);
    }


    /**
     * @throws NullPointerException if playerClass is null.
     */
    public void setPlayerClass(PlayerClass playerClass)
    {
        checkNotNull(playerClass, "playerClass can not be null.");
        this.setPlayerClass(playerClass, true, false, true);
    }


    public void setPlayerClass(@NotNull PlayerClass playerClass, boolean withCoolDown)
    {
        checkNotNull(playerClass, "playerClass can not be null.");
        this.setPlayerClass(playerClass, withCoolDown, false, true);
    }


    public void setPlayerClass(@NotNull PlayerClass playerClass, boolean withCoolDown, boolean silent)
    {
        this.setPlayerClass(playerClass, withCoolDown, silent, true);
    }


    public void setPlayerClass(@NotNull PlayerClass playerClass, boolean withCoolDown, boolean silent, boolean removeOldKit)
    {
        checkNotNull(playerClass, "playerClass can not be null.");

        if (this.toOfflinePlayer().isOnline() && removeOldKit)
        {
            new COnlinePlayer(this.toOfflinePlayer().getPlayer()).removePlayerClassKit();
        }

        this.playerClass = playerClass;


        if (withCoolDown)
        {
            DateTime currentDateTime = new DateTime();
            this.nextPossibleClassSwitchTime = currentDateTime.plusMinutes(
                    ModPlayerClass.getInstance().getConfig().getInt("classes.coolDownInMinutes"));
        }

        if (this.toOfflinePlayer().isOnline())
        {
            new COnlinePlayer(this.offlinePlayer.getUniqueId()).givePlayerClassKit();
            NinOnlinePlayer np = NinOnlinePlayer.fromPlayer(this.toOfflinePlayer().getPlayer());

            if (!silent)
            {
                this.toOfflinePlayer().getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        TranslationUtils.transWithArgs(ModPlayerClass.getMessages(np.getLocale()),
                                new Object[]{playerClass.getName()}, "switchedPlayerClass")));
            }
        }

        this.save();
    }


    /**
     * Move the player to the default player class.
     */
    public void moveToDefaultPlayerClass()
    {
        this.setPlayerClass(PlayerClass.getDefault(), false, false);
    }


    /**
     * Move the player to the default player class.
     */
    public void moveToDefaultPlayerClass(boolean withCoolDown, boolean silent)
    {
        this.setPlayerClass(PlayerClass.getDefault(), withCoolDown, silent);
    }


    /**
     * Check if the cooldown has expired.
     *
     * @return True if the coolDown has expired
     */
    public boolean hasPlayerClassSwitchCoolDownExpired()
    {
        return this.nextPossibleClassSwitchTime == null || this.nextPossibleClassSwitchTime.isBeforeNow();
    }


    /**
     * Get the next possible time to switch player class for this player.
     *
     * @return Next possible time to switch player class.
     */
    @Nullable
    public DateTime getNextPossibleClassSwitchTime()
    {
        return this.nextPossibleClassSwitchTime;
    }


    public boolean canBecomeClass(@NotNull String className)
    {
        checkNotNull(className, "className can not be null.");
        checkArgument(PlayerClass.PlayerClassExists(className), String.format("Player class with name '%s' does not exist.", className));
        return this.canBecomeClass(new PlayerClass(className));
    }


    public boolean canBecomeClass(PlayerClass playerClass)
    {
        return ModPlayerClass.getInstance().getFactionsHook().canBecomeClass(this.toOfflinePlayer(), playerClass);
    }


    public void save()
    {
        ModPlayerClass.getInstance().getRawData().set(this.offlinePlayer.getUniqueId() + ".class", this.playerClass.getName());

        if(this.nextPossibleClassSwitchTime != null)
            ModPlayerClass.getInstance().getRawData().set(this.offlinePlayer.getUniqueId() + ".nextPossibleClassSwitchTime", this.nextPossibleClassSwitchTime.toString());
    }
}
