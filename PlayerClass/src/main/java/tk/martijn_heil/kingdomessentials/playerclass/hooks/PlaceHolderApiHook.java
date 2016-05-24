package tk.martijn_heil.kingdomessentials.playerclass.hooks;


import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import tk.martijn_heil.kingdomessentials.playerclass.KingdomEssPlayerClass;
import tk.martijn_heil.kingdomessentials.playerclass.model.COnlinePlayer;

public class PlaceHolderApiHook
{
    private boolean usePlaceHolderApi;


    public PlaceHolderApiHook()
    {
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceHolderAPI"))
        {
            KingdomEssPlayerClass.getInstance().getNinLogger().info("PlaceHolderAPI detected! Hooking in.");
            this.usePlaceHolderApi = true;
            new Hook(KingdomEssPlayerClass.getInstance());
        }
    }


    public String parse(Player p, String s)
    {
        return (this.usePlaceHolderApi) ? PlaceholderAPI.setPlaceholders(p, s) : s;
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
                case("class"):
                    return new COnlinePlayer(player).getPlayerClass().getName();
            }

            return null;
        }
    }
}
