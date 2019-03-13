package in.aerem.AlicePlugin;

import java.util.logging.Logger;

import com.google.zxing.WriterException;
import org.bukkit.block.Block;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.material.MaterialData;
import org.bukkit.Material;
import org.bukkit.material.Wool;
import org.bukkit.material.Lever;

public class InteractListener implements Listener {
    private final Logger logger;

    public InteractListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
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

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block.getType().equals(Material.LEVER)) {
            Lever lever = (Lever) block.getState().getData();
            if (lever.isPowered()) {
                LightsController.setColor(255, 0, 0);
            } else {
                LightsController.setColor(0, 255, 0);
            }
        }
    }

    @EventHandler
    public void onMapInitialize(MapInitializeEvent e) {
        logger.info("onMapInitialize");
        MapView mapView = e.getMap();
        for (MapRenderer r : mapView.getRenderers()) {
            mapView.removeRenderer(r);
        }
        try {
            mapView.addRenderer(new Renderer(logger));
        } catch (WriterException e1) {
            logger.warning(e.toString());
        }
    }
}
