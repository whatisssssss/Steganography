import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRExtractor {
    public static String extractQR(String imagePath) {
        try {
            BufferedImage stegoImage = ImageIO.read(new File(imagePath));
            int width = stegoImage.getWidth();
            int height = stegoImage.getHeight();

            BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = stegoImage.getRGB(x, y);

                    int r = (pixel >> 16) & 1;
                    int g = (pixel >> 8) & 1;
                    int b = pixel & 1;

                    int bit = (r + g + b) >= 2 ? 255 : 0;
                    int rgb = (bit << 16) | (bit << 8) | bit;

                    qrImage.setRGB(x, y, rgb);
                }
            }

            // Optional: Save the recovered QR image to see what it looks like
            ImageIO.write(qrImage, "png", new File("recovered_qr.png"));

            LuminanceSource source = new BufferedImageLuminanceSource(qrImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();

        } catch (NotFoundException e) {
            System.err.println("❌ QR Code not found in the image.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
