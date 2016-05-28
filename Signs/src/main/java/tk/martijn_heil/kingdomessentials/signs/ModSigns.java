package tk.martijn_heil.kingdomessentials.signs;


import lombok.Getter;
import tk.martijn_heil.nincore.api.Core;

public class ModSigns extends Core
{
    @Getter private final ExecutableSignRegister register = new ExecutableSignRegister();
    @Getter private static ModSigns instance;


    public ModSigns()
    {
        instance = this;
    }


    @Override
    public void onEnableInner()
    {

    }
}
