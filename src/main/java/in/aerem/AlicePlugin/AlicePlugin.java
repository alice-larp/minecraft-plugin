package in.aerem.AlicePlugin;

import org.bukkit.plugin.java.JavaPlugin;

public class AlicePlugin extends JavaPlugin {
    public AlicePlugin(){}

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}