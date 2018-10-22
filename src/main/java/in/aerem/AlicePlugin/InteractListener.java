package in.aerem.AlicePlugin;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class InteractListener implements Listener {
    private final Logger logger;

    public InteractListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        logger.info(event.getClickedBlock().toString());
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.PURPLE_WOOL) {
                LightsController.switchLight(true);
            }
            if (event.getClickedBlock().getType() == Material.BLACK_WOOL) {
                LightsController.switchLight(false);
            }
        }
    }
}
