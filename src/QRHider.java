import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class QRHider {
    public static void hideQR(String coverPath, String qrPath, String outputPath) throws Exception {
        BufferedImage cover = ImageIO.read(new File(coverPath));
        BufferedImage qr = ImageIO.read(new File(qrPath));
        
        int width = qr.getWidth();
        int height = qr.getHeight();

        BufferedImage result = new BufferedImage(cover.getWidth(), cover.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < cover.getHeight(); y++) {
            for (int x = 0; x < cover.getWidth(); x++) {
                int rgbCover = cover.getRGB(x, y);
                if (x < width && y < height) {
                    int qrPixel = qr.getRGB(x, y) & 0x010101; // keep LSB
                    rgbCover = (rgbCover & 0xFEFEFE) | qrPixel; // inject LSB
                }
                result.setRGB(x, y, rgbCover);
            }
        }

        ImageIO.write(result, "png", new File(outputPath));
        System.out.println("QR embedded image saved to: " + outputPath);
    }
}
