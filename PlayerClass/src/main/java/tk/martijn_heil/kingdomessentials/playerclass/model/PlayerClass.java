package tk.martijn_heil.kingdomessentials.playerclass.model;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.martijn_heil.kingdomessentials.playerclass.ModPlayerClass;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PlayerClass
{
    private String id;
    private ConfigurationSection classSection;


    /**
     * Constructor
     *
     * @param id The player class name.
     * @throws NullPointerException if id is null.
     */
    public PlayerClass(@NotNull String id)
    {
        checkNotNull(id, "id can not be null.");

        this.id = id;
        this.classSection = ModPlayerClass.getInstance().getConfig().getConfigurationSection("classes.classes." + id);

        if(classSection == null) throw new IllegalArgumentException("No class could be found for ID: " + id);
    }

    
    @Override
    public String toString()
    {
        return this.getDisplayName();
    }


    public String getDisplayName()
    {
        return classSection.getString("displayName");
    }


    public String getId()
    {
        return id;
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
        return id.equals(ModPlayerClass.getInstance().getConfig().getString("classes.defaultClass"));
    }


    public List<String> getCmdsExecutedOnPlayerRespawn()
    {
        return classSection.getStringList("commandsExecutedOnPlayerRespawn");
    }


    /**
     * Check if a player class exists.
     *
     * @param id The player class id to check for.
     * @return true if this player class exists.
     */
    @Contract("null -> false")
    public static boolean PlayerClassExists(@Nullable String id)
    {
        return (id != null) && ModPlayerClass.getInstance().getConfig().getConfigurationSection("classes.classes").getKeys(false).contains(id);
    }


    /**
     * Get the default player class.
     *
     * @return The default player class.
     */
    @Contract(" -> !null")
    public static PlayerClass getDefault()
    {
        return new PlayerClass(ModPlayerClass.getInstance().getConfig().getString("classes.defaultClass"));
    }


    public static List<PlayerClass> getAll()
    {
        List<PlayerClass> list = new ArrayList<>();

        for (String classId : ModPlayerClass.getInstance().getConfig().getConfigurationSection("classes.classes").getKeys(false))
        {
            list.add(new PlayerClass(classId));
        }

        return list;
    }
}
