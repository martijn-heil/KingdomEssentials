package tk.martijn_heil.kingdomessentials.playerclass;


import com.google.common.base.Preconditions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.martijn_heil.kingdomessentials.playerclass.model.COnlinePlayer;
import tk.martijn_heil.kingdomessentials.playerclass.model.PlayerClass;

public class Players
{
    /**
     * Populate a player's data.
     *
     * @param p The player to populate data for.
     * @throws NullPointerException if p is null.
     */
    public static void populateData(@NotNull Player p)
    {
        Preconditions.checkNotNull(p, "p can not be null.");

        FileConfiguration data = KingdomEssPlayerClass.getInstance().getRawData();
        FileConfiguration config = KingdomEssPlayerClass.getInstance().getConfig();

        String playerUUID = p.getUniqueId().toString();
        String defaultClassName = config.getString("classes.defaultClass");


        if (!data.isSet(playerUUID)) // he's new.
        {
            KingdomEssPlayerClass.getInstance().getNinLogger().info("Creating new data entry for player: " + p.getName() + " (" + playerUUID + ")");

            data.set(playerUUID + ".class", defaultClassName);
            data.set(playerUUID + ".lastSeenName", p.getName());
        }


        if (!data.isSet(playerUUID + ".class"))
        {
            data.set(playerUUID + ".class", defaultClassName);
        }


        if (!data.isSet(playerUUID + ".lastSeenName"))
        {
            data.set(playerUUID + ".lastSeenName", p.getName());
        }


        // Update last seen name.
        data.set(playerUUID + ".lastSeenName", p.getName());

        // Check if player's class still exists
        if (!PlayerClass.PlayerClassExists(data.getString(playerUUID + ".class")))
        {
            // If player's class doesn't exist, put him back in the default class.
            COnlinePlayer cOnlinePlayer = new COnlinePlayer(p.getUniqueId());

            cOnlinePlayer.moveToDefaultPlayerClass();
        }
    }
}
