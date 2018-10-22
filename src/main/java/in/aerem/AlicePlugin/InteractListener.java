package in.aerem.AlicePlugin;

import java.util.logging.Logger;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;

public class InteractListener implements Listener {
    private final Logger logger;

    public InteractListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        logger.info("onPlayerInteract");
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.isBlockInHand()) {
            logger.info(event.getClickedBlock().toString());
            MaterialData matData = event.getClickedBlock().getState().getData();
            if (matData instanceof Wool) {
                Wool wool = (Wool) matData;
                Color c = wool.getColor().getColor();
                LightsController.setColor(c.getRed(), c.getGreen(), c.getBlue());
            } else {
                logger.warning("Not a wool!");
            }
        }
    }
}
