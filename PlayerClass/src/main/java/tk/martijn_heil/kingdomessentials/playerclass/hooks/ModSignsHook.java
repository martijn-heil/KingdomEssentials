package tk.martijn_heil.kingdomessentials.playerclass.hooks;


import org.bukkit.Bukkit;
import tk.martijn_heil.kingdomessentials.signs.ExecutableSign;
import tk.martijn_heil.kingdomessentials.signs.ModSigns;

import java.util.logging.Logger;

public class ModSignsHook
{
    public ModSignsHook(Logger logger)
    {
        if(Bukkit.getPluginManager().isPluginEnabled("KE-ModSigns"))
        {
            logger.info("ModSigns detected, hooking in..");
            new Hook(logger);
        }
    }

    private class Hook
    {
        public Hook(Logger logger)
        {
            logger.info("Registering executable signs..");
            ModSigns.getInstance().getRegister().addExecutableSign(new ExecutableSign("SetClass", new SetClassSignExecutor()));
        }
    }
}
