package tk.martijn_heil.kingdomessentials.playerclass.model;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.martijn_heil.kingdomessentials.playerclass.KingdomEssPlayerClass;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PlayerClass
{
    private String name;
    private ConfigurationSection classSection;

    /**
     * Constructor
     *
     * @param className The player class name.
     */
    public PlayerClass(@NotNull String className)
    {
        checkNotNull(className, "className can not be null.");

        name = className;
        this.classSection = KingdomEssPlayerClass.getInstance().getConfig().getConfigurationSection("classes.classes." + name);
    }

    /**
     * Get the player class' name.
     *
     * @return the player class' name.
     */
    public String getName()
    {
        return name;
    }


    /**
     * Get the kit name of the essentials kit for this class.
     *
     * @return The name of the essentials kit for this class.
     */
    public String getKitName()
    {
        return classSection.getString("kitName");
    }


    /**
     * Check if this player class uses maxPercentagePerFaction.
     *
     * @return true if this player class uses maxPercentagePerFaction.
     */
    public boolean usesMaxPercentagePerFaction()
    {
        return classSection.getBoolean("useMaxPercentagePerFaction");
    }


    /**
     * Check if this player class is this default player class.
     *
     * @return true if this player class is the default player class.
     */
    public boolean isDefaultPlayerClass()
    {
        return name.equals(KingdomEssPlayerClass.getInstance().getConfig().getString("classes.defaultClass"));
    }


    public List<String> getCmdsExecutedOnPlayerRespawn()
    {
        return classSection.getStringList("commandsExecutedOnPlayerRespawn");
    }


    /**
     *  Check if a player class exists.
     *
     * @param className The player class name to check for.
     * @return true if this player class exists.
     */
    @Contract("null -> false")
    public static boolean PlayerClassExists(@Nullable String className)
    {
        return (className != null) && KingdomEssPlayerClass.getInstance().getConfig().getConfigurationSection("classes.classes").getKeys(false).contains(className);
    }


    /**
     * Get the default player class.
     *
     * @return The default player class.
     */
    @Contract(" -> !null")
    public static PlayerClass getDefault()
    {
        return new PlayerClass(KingdomEssPlayerClass.getInstance().getConfig().getString("classes.defaultClass"));
    }
}
