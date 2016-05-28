package tk.martijn_heil.kingdomessentials.playerclass.hooks;


import org.bukkit.Bukkit;
import tk.martijn_heil.kingdomessentials.playerclass.ModPlayerClass;

public class ModSignsHook
{
    public ModSignsHook()
    {
        if(Bukkit.getPluginManager().isPluginEnabled("KE-ModSigns"))
        {
            ModPlayerClass.getInstance().getNinLogger().info("ModSigns detected, hooking in..");
            // TODO: register SetClass sign.
        }
    }
}
