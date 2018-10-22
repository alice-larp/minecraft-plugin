package in.aerem.AlicePlugin;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.logging.Logger;

public class Renderer extends MapRenderer {
    private final Logger logger;

    public Renderer(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                mapCanvas.setPixel(x, y, MapPalette.BLUE);
            }
        }
    }
}
