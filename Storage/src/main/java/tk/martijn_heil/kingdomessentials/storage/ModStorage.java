package tk.martijn_heil.kingdomessentials.storage;


import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import tk.martijn_heil.nincore.api.Core;

public class ModStorage extends Core
{
    @Getter
    private static ModStorage instance;


    public ModStorage()
    {
        instance = this;
    }


    @Override
    public void onEnableInner()
    {
        this.saveDefaultConfig();

        if (!this.getDataManager().dataFileExists())
        {
            this.getDataManager().createDataFile();
        }

        this.getDataManager().loadDataFile();
    }


    public FileConfiguration getRawData()
    {
        return this.getDataManager().getData();
    }
}