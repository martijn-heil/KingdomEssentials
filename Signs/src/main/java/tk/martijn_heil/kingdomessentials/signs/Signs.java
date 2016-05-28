package tk.martijn_heil.kingdomessentials.signs;


import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Signs
{
    public static boolean isKingdomEssSign(@NotNull Sign s)
    {
        Preconditions.checkNotNull(s, "s can not be null.");
        return s.getLine(0).equals(ChatColor.YELLOW + "[KingdomKits]");
    }


    @Contract(pure = true)
    public static boolean isKingdomEssSign(@NotNull String firstLine)
    {
        Preconditions.checkNotNull(firstLine, "firstLine can not be null.");

        return firstLine.equals(ChatColor.YELLOW + "[KingdomKits]");
    }


    @Contract(pure = true)
    public static String getKingdomKitsPrefix()
    {
        return ChatColor.YELLOW + "[KingdomKits]";
    }
}
