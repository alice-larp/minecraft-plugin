package in.aerem.AlicePlugin;

import java.util.logging.Logger;

import org.bukkit.Color;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Wool;

public class InteractListener implements Listener {
    private final Logger logger;

    public InteractListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        logger.info(event.getClickedBlock().toString());
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.isBlockInHand()) {
            BlockState state = event.getClickedBlock().getState();
            Wool wool = (Wool) state.getData();
            if (wool != null) {
                Color c = wool.getColor().getColor();
                LightsController.setColor(c.getRed(), c.getGreen(), c.getBlue());
            } else {
                logger.warning("Not a wool!");
            }
        }
    }
}
