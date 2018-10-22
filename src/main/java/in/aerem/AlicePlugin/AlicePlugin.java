package in.aerem.AlicePlugin;

import org.bukkit.plugin.java.JavaPlugin;

public class AlicePlugin extends JavaPlugin {
    public AlicePlugin() {
    }

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        LightsController.switchLight(true);
        getServer().getPluginManager().registerEvents(new InteractListener(getLogger()), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
        LightsController.switchLight(false);
    }
}