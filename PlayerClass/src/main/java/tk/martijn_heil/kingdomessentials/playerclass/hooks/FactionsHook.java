package tk.martijn_heil.kingdomessentials.playerclass.hooks;


import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import tk.martijn_heil.kingdomessentials.playerclass.KingdomEssPlayerClass;
import tk.martijn_heil.kingdomessentials.playerclass.model.COnlinePlayer;
import tk.martijn_heil.kingdomessentials.playerclass.model.PlayerClass;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FactionsHook
{
    /**
     * Check if a player can become a player class.
     *
     * @param playerClass the {@link PlayerClass} to check for.
     * @return True if the player can become this class.
     */
    public boolean canBecomeClass(@NotNull OfflinePlayer p, @NotNull PlayerClass playerClass)
    {
        if(!Bukkit.getPluginManager().isPluginEnabled("Factions")) return true;

        // Noone with any sense would check for a className with null, if null is passed this cleary indicates some
        // mistake, and we should not fail silently.
        checkNotNull(playerClass, "playerClass can not be null.");
        checkNotNull(p, "p can not be null.");


        // If this player class does not use MaxPercentagePerFaction.
        if(!playerClass.usesMaxPercentagePerFaction())
        {
            return true;
        }

        // Get MPlayer
        MPlayer mPlayer = MPlayer.get(p.getUniqueId());

        // If the player is in no faction, he can always can become the class.
        if(!mPlayer.hasFaction())
        {
            return true;
        }

        List<MPlayer> list = mPlayer.getFaction().getMPlayers();

        float total = 0;

        // Players in a class which doesn't use MaxPercentagePerFaction should not be counted to the total.
        for (MPlayer mP: list)
        {
            COnlinePlayer cPlayer = new COnlinePlayer(mP.getUuid());

            if(cPlayer.getPlayerClass().usesMaxPercentagePerFaction())
            {
                total++;
            }
        }

        float effect = (100/total);
        float count = 0;

        // Count all players in this specific player class.
        for (MPlayer mP : list)
        {
            COnlinePlayer cPlayer = new COnlinePlayer(mP.getUuid());

            if(cPlayer.getPlayerClass().equals(playerClass))
            {
                count++;
            }
        }


        // Calculate percentage
        float currentPercentage = count*100/total;
        float newPercentage = currentPercentage + effect;


        //int roundedCurrentPercentage = Math.round(currentPercentage);

        // Percentage of players with the class already reached the max percentage.
        return !(newPercentage > KingdomEssPlayerClass.getInstance().getConfig().getDouble("soulbound.classes." +
                playerClass.getName() + ".maxPercentagePerFaction"));
    }
}
