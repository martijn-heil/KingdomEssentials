package tk.martijn_heil.kingdomessentials.playerclass.exceptions;

import org.bukkit.command.CommandSender;
import tk.martijn_heil.kingdomessentials.playerclass.ModPlayerClass;
import tk.martijn_heil.nincore.api.NinCore;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;
import tk.martijn_heil.nincore.api.util.TranslationUtils;

public class PlayerCannotBecomeClassException extends ValidationException
{
    public PlayerCannotBecomeClassException(CommandSender target)
    {
        super(target, TranslationUtils.getStaticMsg(ModPlayerClass.getMessages(NinCore.get().getEntityManager().getNinCommandSender(target)
                .getLocale()), "commandError.playerCannotBecomeClass"), null);
    }
}
