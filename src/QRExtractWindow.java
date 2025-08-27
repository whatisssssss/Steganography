import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRExtractWindow extends JFrame {
    private JLabel messageLabel;

    public QRExtractWindow() {
        setTitle("QR Code Extractor");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(33, 33, 33));

        JButton chooseImageBtn = new JButton("Choose Image with Hidden QR");
        chooseImageBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        chooseImageBtn.setBackground(new Color(30, 136, 229));
        chooseImageBtn.setForeground(Color.WHITE);
        chooseImageBtn.setFocusPainted(false);
        chooseImageBtn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        chooseImageBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        chooseImageBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                chooseImageBtn.setBackground(new Color(21, 101, 192));
            }

            public void mouseExited(MouseEvent e) {
                chooseImageBtn.setBackground(new Color(30, 136, 229));
            }
        });

        chooseImageBtn.addActionListener(this::extractQR);

        messageLabel = new JLabel("Extracted message will appear here", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(33, 33, 33));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(chooseImageBtn);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(messageLabel);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);
    }

    private void extractQR(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showOpenDialog(this);
        if (option != JFileChooser.APPROVE_OPTION) return;

        File imageFile = chooser.getSelectedFile();

        try {
            BufferedImage stegoImage = ImageIO.read(imageFile);
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

            LuminanceSource source = new BufferedImageLuminanceSource(qrImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);

            messageLabel.setText("Message: " + result.getText());

        } catch (NotFoundException nfe) {
            messageLabel.setText("No QR code found.");
        } catch (Exception ex) {
            messageLabel.setText("Failed to extract QR code.");
            ex.printStackTrace();
        }
    }
}
