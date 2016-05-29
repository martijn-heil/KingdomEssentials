package tk.martijn_heil.kingdomessentials.signs;


import org.bukkit.entity.Player;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;


public interface SignExecutor
{
    void execute(Player executor, String value) throws ValidationException, TechnicalException;
}
