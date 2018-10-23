package in.aerem.AlicePlugin;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.logging.Logger;

public class Renderer extends MapRenderer {
    private final Logger logger;
    private final BitMatrix bitMatrix;

    public Renderer(Logger logger) throws WriterException {
        this.logger = logger;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        bitMatrix = qrCodeWriter.encode("experimental qr code", BarcodeFormat.QR_CODE, 128, 128);
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        for (int x = 0; x < bitMatrix.getHeight(); x++) {
            for (int y = 0; y < bitMatrix.getWidth(); y++) {
                mapCanvas.setPixel(x, y, bitMatrix.get(x, y) ? MapPalette.DARK_BROWN : MapPalette.WHITE);
            }
        }
    }
}
