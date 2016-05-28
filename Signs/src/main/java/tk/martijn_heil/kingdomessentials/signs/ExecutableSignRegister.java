package tk.martijn_heil.kingdomessentials.signs;


import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class ExecutableSignRegister
{
    @Getter private List<ExecutableSign> registeredSigns = new ArrayList<>();


    public void addExecutableSign(ExecutableSign sign)
    {
        checkArgument(!registeredSigns.contains(sign), "this sign is already registered.");
    }


    @Nullable
    public ExecutableSign get(String action)
    {
        for (ExecutableSign sign : registeredSigns)
        {
            if(sign.getAction().equals(action)) return sign;
        }

        return null;
    }
}