package in.aerem.AlicePlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.plugin.java.JavaPlugin;
import spark.Spark;

public class AlicePlugin extends JavaPlugin {
    public AlicePlugin() {
    }

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        LightsController.switchLight(true);
        getServer().getPluginManager().registerEvents(new InteractListener(getLogger()), this);
        Spark.init();
        Spark.post("/command", (request, response) -> {
            String command = request.body();
            getLogger().info("Executing command: " + command);
            getServer().getScheduler().runTask(this, () -> {
                try {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
                } catch (Exception e) {
                    getLogger().warning(e.toString());
                }
            });
            return "";
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
        Spark.stop();
        LightsController.switchLight(false);
    }
}