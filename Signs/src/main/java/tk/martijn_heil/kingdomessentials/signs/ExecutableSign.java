package tk.martijn_heil.kingdomessentials.signs;


import lombok.Getter;
import org.bukkit.entity.Player;
import tk.martijn_heil.nincore.api.NinCore;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;

public class ExecutableSign
{
    @Getter private String action;
    @Getter private SignExecutor executor;


    public ExecutableSign(String action, SignExecutor executor)
    {
        this.action = action;
        this.executor = executor;
    }


    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof ExecutableSign)) return false;

        ExecutableSign sign = (ExecutableSign) obj;
        return this.getAction().equals(sign.getAction());
    }


    public void execute(Player p, String value)
    {
        try
        {
            this.executor.execute(p, value);
        }
        catch (ValidationException ve)
        {
            ve.getTarget().sendError(ve.getPlayerMessage());
        }
        catch (TechnicalException te)
        {
            if (te.getLogLevel() == null)
            {
                NinCore.getImplementingPlugin().getLogger().warning(te.getMessage());
            }
            else
            {
                NinCore.getImplementingPlugin().getLogger().log(te.getLogLevel(), te.getMessage());
            }
        }
    }
}
