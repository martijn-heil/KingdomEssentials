package tk.martijn_heil.kingdomessentials.playerclass.hooks;


import org.bukkit.entity.Player;
import tk.martijn_heil.kingdomessentials.playerclass.exceptions.PlayerClassNotFoundException;
import tk.martijn_heil.kingdomessentials.playerclass.model.COfflinePlayer;
import tk.martijn_heil.kingdomessentials.playerclass.model.PlayerClass;
import tk.martijn_heil.kingdomessentials.signs.SignExecutor;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.AccessDeniedException;

public class SetClassSignExecutor implements SignExecutor
{
    @Override
    public void execute(Player executor, String value) throws ValidationException, TechnicalException
    {
        if(!executor.hasPermission("kingdomess.playerclass.sign.use.setclass")) throw new AccessDeniedException(executor);
        if(!PlayerClass.PlayerClassExists(value)) throw new PlayerClassNotFoundException(executor);

        // All validation has passed..
        new COfflinePlayer(executor).setPlayerClass(new PlayerClass(value), true, false);
    }
}
