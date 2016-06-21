package tk.martijn_heil.kingdomessentials.playerclass.hooks;


import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import tk.martijn_heil.kingdomessentials.playerclass.ModPlayerClass;
import tk.martijn_heil.kingdomessentials.playerclass.model.COnlinePlayer;

public class PlaceHolderApiHook
{
    private boolean usePlaceHolderApi = false;


    public PlaceHolderApiHook()
    {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
        {
            ModPlayerClass.getInstance().getNinLogger().info("PlaceholderAPI detected! Hooking in.");
            this.usePlaceHolderApi = true;
            new Hook(ModPlayerClass.getInstance());
        }
    }


    public String parse(Player p, String s)
    {
        //return (this.usePlaceHolderApi) ? PlaceholderAPI.setPlaceholders(p, s) : s;
        if(usePlaceHolderApi)
        {
            ModPlayerClass.getInstance().getNinLogger().info("Should use placeholderapi");
            String newString = PlaceholderAPI.setPlaceholders(p, s);
            ModPlayerClass.getInstance().getNinLogger().info("New string: " + newString);
            return newString;
        }
        else
        {
            ModPlayerClass.getInstance().getNinLogger().info("SHould not use placeholderapi");
            return s;
        }
    }


    private class Hook extends EZPlaceholderHook
    {
        public Hook(Plugin plugin)
        {
            super(plugin, "kingdomess");
        }


        @Override
        public String onPlaceholderRequest(Player player, String s)
        {
            switch (s)
            {
                case ("class_display_name"):
                    return new COnlinePlayer(player).getPlayerClass().getDisplayName();

                case("class_id"):
                    return new COnlinePlayer(player).getPlayerClass().getId();
            }

            return null;
        }
    }
}
