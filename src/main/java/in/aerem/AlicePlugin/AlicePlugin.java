package in.aerem.AlicePlugin;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.paho.client.mqttv3.*;
import spark.Spark;

public class AlicePlugin extends JavaPlugin {
    private static Gson gson = new Gson();
    private IMqttClient client;

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        try {
            client = new MqttClient("tcp://raspberrypi:1883", "Minecraft");
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect(options);
            client.subscribe("minecraft/command", (topic, msg) -> {
                AlicePlugin.this.getLogger().info("MQTT: " + topic + " " + msg.toString());
                try {
                    String command = gson.fromJson(msg.toString(), CommandRequest.class).command;
                    AlicePlugin.this.executeCommand(command);
                } catch (Exception e) {
                    getLogger().warning(e.toString());
                }
            });
            getServer().getPluginManager().registerEvents(new InteractListener(getLogger(), client), this);
            getLogger().info("MQTT Init and subscribe OK");
        } catch (MqttException e) {
            getServer().getPluginManager().registerEvents(new InteractListener(getLogger(), null), this);
            e.printStackTrace();
        }

        Spark.init();
        Spark.post("/command", (request, response) -> {
            String command = gson.fromJson(request.body(), CommandRequest.class).command;
            executeCommand(command);
            return "";
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
        Spark.stop();
    }

    private void executeCommand(String command) {
        getLogger().info("Executing command: " + command);
        getServer().getScheduler().runTask(this, () -> {
            try {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
            } catch (Exception e) {
                getLogger().warning(e.toString());
            }
        });
    }

}