package tk.martijn_heil.kingdomessentials.playerclass.hooks;


import org.bukkit.Bukkit;
import tk.martijn_heil.kingdomessentials.playerclass.ModPlayerClass;
import tk.martijn_heil.kingdomessentials.signs.ExecutableSign;
import tk.martijn_heil.kingdomessentials.signs.ModSigns;

public class ModSignsHook
{
    public ModSignsHook()
    {
        if(Bukkit.getPluginManager().isPluginEnabled("KE-ModSigns"))
        {
            ModPlayerClass.getInstance().getNinLogger().info("ModSigns detected, hooking in..");

            ModPlayerClass.getInstance().getNinLogger().info("Registering executable signs..");
            ModSigns.getInstance().getRegister().addExecutableSign(new ExecutableSign("SetClass", new SetClassSignExecutor()));
        }
    }
}
