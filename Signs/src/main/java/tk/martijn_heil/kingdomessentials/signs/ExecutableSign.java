package tk.martijn_heil.kingdomessentials.signs;


import lombok.Getter;
import org.bukkit.entity.Player;

public class ExecutableSign
{
    @Getter private String action;
    @Getter private SignExecutor executor;
    @Getter private String requiredCreatePermission;
    @Getter private String requiredUsePermission;


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
        this.executor.execute(p, value);
    }
}
