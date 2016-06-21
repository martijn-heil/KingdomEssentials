package tk.martijn_heil.kingdomessentials.playerclass;


import com.google.common.base.Preconditions;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import tk.martijn_heil.kingdomessentials.playerclass.model.PlayerClass;

public class Players
{
    /**
     * Populate a player's data.
     *
     * @param p The player to populate data for.
     * @throws NullPointerException if p is null.
     */
    public static void updatePlayerData(@NotNull OfflinePlayer p)
    {
        Preconditions.checkNotNull(p, "p can not be null.");

        FileConfiguration data = ModPlayerClass.getInstance().getRawData();

        String playerUUID = p.getUniqueId().toString();
        String defaultClassId = PlayerClass.getDefault().getId();


        if (!data.isSet(playerUUID)) // he's new.
        {
            ModPlayerClass.getInstance().getNinLogger().info("Creating new data entry for player: " + p.getName() + " (" + playerUUID + ")");

            data.set(playerUUID + ".class", defaultClassId);
        }


        if (!data.isSet(playerUUID + ".class"))
        {
            data.set(playerUUID + ".class", defaultClassId);
        }


        // Check if player's class still exists
        if (!PlayerClass.PlayerClassExists(data.getString(playerUUID + ".class")))
        {
            // If player's class doesn't exist, put him back in the default class.
            data.set(playerUUID + ".class", defaultClassId);
        }
    }
}
