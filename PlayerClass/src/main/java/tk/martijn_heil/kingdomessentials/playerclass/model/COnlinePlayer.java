package tk.martijn_heil.kingdomessentials.playerclass.model;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.martijn_heil.kingdomessentials.playerclass.ModPlayerClass;
import tk.martijn_heil.nincore.api.NinCore;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;
import tk.martijn_heil.nincore.api.util.ServerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class COnlinePlayer extends COfflinePlayer
{
    private Player player;


    /**
     * Constructor
     *
     * @param uuid The player's UUID
     */
    public COnlinePlayer(UUID uuid)
    {
        super(uuid);
        this.player = Bukkit.getPlayer(uuid);
    }


    public COnlinePlayer(Player p)
    {
        super(p);
        this.player = p;
    }


    public NinOnlinePlayer toNinOnlinePlayer()
    {
        return NinCore.get().getEntityManager().getNinOnlinePlayer(this.toPlayer());
    }


    public Player toPlayer()
    {
        return this.player;
    }


    public void givePlayerClassKit()
    {
        ServerUtils.dispatchCommand("essentials:kit " + this.getPlayerClass().getKitName() + " " + this.player.getName());
    }


    public void removePlayerClassKit()
    {
        for (ItemStack i : this.player.getInventory().getContents())
        {
            if (ModPlayerClass.isPartOfKit(i, this.getPlayerClass().getId()))
            {
                this.player.getInventory().remove(i);
            }
        }

        List<ItemStack> armorContentsStaging = new ArrayList<>();
        for (ItemStack i : this.player.getInventory().getArmorContents())
        {
            if (!ModPlayerClass.isPartOfKit(i, this.getPlayerClass().getKitName()))
            {
                armorContentsStaging.add(i);
            }
        }
        this.player.getInventory().setArmorContents(armorContentsStaging.toArray(new ItemStack[armorContentsStaging.size()]));

        List<ItemStack> storageContentsStaging = new ArrayList<>();
        for (ItemStack i : this.player.getInventory().getStorageContents())
        {
            if (!ModPlayerClass.isPartOfKit(i, this.getPlayerClass().getKitName()))
            {
                storageContentsStaging.add(i);
            }
        }
        this.player.getInventory().setStorageContents(storageContentsStaging.toArray(new ItemStack[storageContentsStaging.size()]));

        List<ItemStack> extraContentsStaging = new ArrayList<>();
        for (ItemStack i : this.player.getInventory().getExtraContents())
        {
            if (!ModPlayerClass.isPartOfKit(i, this.getPlayerClass().getKitName()))
            {
                extraContentsStaging.add(i);
            }
        }
        this.player.getInventory().setExtraContents(extraContentsStaging.toArray(new ItemStack[extraContentsStaging.size()]));
    }
}