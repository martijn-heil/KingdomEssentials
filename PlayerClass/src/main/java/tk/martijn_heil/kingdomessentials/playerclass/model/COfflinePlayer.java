package tk.martijn_heil.kingdomessentials.playerclass.model;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import tk.martijn_heil.kingdomessentials.playerclass.KingdomEssPlayerClass;
import tk.martijn_heil.nincore.api.entity.NinOfflinePlayer;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;
import tk.martijn_heil.nincore.api.util.TranslationUtils;

import java.util.ResourceBundle;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;


public class COfflinePlayer
{
    private UUID uuid;
    private OfflinePlayer offlinePlayer;



    public COfflinePlayer(@NotNull UUID uuid)
    {
        Preconditions.checkNotNull(uuid);

        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        this.uuid = uuid;
    }


    public COfflinePlayer(@NotNull OfflinePlayer p)
    {
        Preconditions.checkNotNull(p);

        this.offlinePlayer = p;
        this.uuid = p.getUniqueId();
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
        String playerClassname = KingdomEssPlayerClass.getInstance().getRawData().getString(uuid + ".class");

        return new PlayerClass(playerClassname);
    }


    /**
     * Set the player's class.
     *
     * @param className The class name.
     */
    public void setPlayerClass(@NotNull String className)
    {
        Preconditions.checkNotNull(className);
        this.setPlayerClass(new PlayerClass(className), true);
    }


    /**
     * Set the player's class.
     *
     * @param className The class name.
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
        if(this.toOfflinePlayer().isOnline())
        {
            new COnlinePlayer(this.toOfflinePlayer().getPlayer()).removePlayerClassKit();
        }

        KingdomEssPlayerClass.getInstance().getRawData().set(uuid + ".class", playerClass.getName());

        if(withCoolDown)
        {
            DateTime currentDateTime = new DateTime();
            DateTime nextPossibleClassSwitchTime = currentDateTime.plusMinutes(
                    KingdomEssPlayerClass.getInstance().getConfig().getInt("classes.coolDownInMinutes"));
            KingdomEssPlayerClass.getInstance().getRawData().set(uuid + ".nextPossibleClassSwitchTime",
                    nextPossibleClassSwitchTime.toString());
        }

        if(this.toOfflinePlayer().isOnline())
        {
            new COnlinePlayer(this.uuid).givePlayerClassKit();
            NinOnlinePlayer np = NinOnlinePlayer.fromPlayer(this.toOfflinePlayer().getPlayer());

            if(!silent)
            {
                this.toOfflinePlayer().getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        TranslationUtils.transWithArgs(ResourceBundle.getBundle("lang.mainMsgs", np.getLocale()),
                                new Object[] {playerClass.getName()}, "switchedPlayerClass")));
            }
        }
    }


    /**
     * Move the player to the default player class.
     *
     */
    public void moveToDefaultPlayerClass()
    {
        this.setPlayerClass(PlayerClass.getDefault(), false);
    }


    /**
     * Move the player to the default player class.
     *
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
        DateTime nextPossibleClassSwitchTime = new DateTime(KingdomEssPlayerClass.getInstance().getRawData()
                .getString(uuid + ".nextPossibleClassSwitchTime"));
        return nextPossibleClassSwitchTime.isBeforeNow();
    }


    /**
     * Get the next possible time to switch player class for this player.
     *
     * @return Next possible time to switch player class.
     */
    public DateTime getNextPossibleClassSwitchTime()
    {
        return DateTime.parse(KingdomEssPlayerClass.getInstance().getRawData().getString(uuid + ".nextPossibleClassSwitchTime"));
    }


    public boolean canBecomeClass(String className)
    {
        checkArgument(PlayerClass.PlayerClassExists(className), String.format("Player class with name '%s' does not exist.", className));
        return this.canBecomeClass(new PlayerClass(className));
    }


    public boolean canBecomeClass(PlayerClass playerClass)
    {
        return KingdomEssPlayerClass.getInstance().getFactionsHook().canBecomeClass(this.toOfflinePlayer(), playerClass);
    }
}
