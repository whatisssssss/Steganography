import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import com.google.zxing.WriterException;
import java.io.IOException;

public class QRCodeWindow extends JFrame {
    private JTextField messageField;
    private File qrFile;
    private File coverImageFile;
    private JLabel statusLabel;

    public QRCodeWindow() {
        setTitle("QR Code Steganography");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(28, 30, 34));

        // ----- Input Panel -----
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        inputPanel.setBackground(new Color(28, 30, 34));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        JLabel msgLabel = new JLabel("Enter your secret message:");
        msgLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        msgLabel.setForeground(Color.WHITE);

        messageField = new JTextField();
        messageField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageField.setBackground(new Color(245, 245, 245));
        messageField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        inputPanel.add(msgLabel);
        inputPanel.add(messageField);

        // ----- Button Panel -----
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        buttonPanel.setBackground(new Color(28, 30, 34));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JButton generateBtn = createFancyButton("Generate QR Code");
        JButton chooseCoverBtn = createFancyButton("Choose Cover Image");
        JButton hideBtn = createFancyButton("Hide QR in Image");

        buttonPanel.add(generateBtn);
        buttonPanel.add(chooseCoverBtn);
        buttonPanel.add(hideBtn);

        // ----- Status Panel -----
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(new Color(28, 30, 34));
        statusLabel = new JLabel("Status: Waiting...");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        statusLabel.setForeground(new Color(144, 238, 144));
        statusPanel.add(statusLabel);

        // ----- Action Listeners -----
        generateBtn.addActionListener(e -> {
            String text = messageField.getText().trim();
            if (text.isEmpty()) {
                updateStatus(" Message is empty!", Color.PINK);
                return;
            }
            try {
                qrFile = new File("generated_qr.png");
                QRGenerator.generateQR(text, qrFile.getAbsolutePath());
                updateStatus(" QR Code generated successfully!", new Color(50, 205, 50));
            } catch (WriterException | IOException ex) {
                updateStatus("⚠️ Error: " + ex.getMessage(), Color.ORANGE);
            }
        });

        chooseCoverBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                coverImageFile = chooser.getSelectedFile();
                updateStatus("🖼️ Cover image selected: " + coverImageFile.getName(), new Color(135, 206, 250));
            }
        });

        hideBtn.addActionListener(e -> {
            if (qrFile == null || !qrFile.exists()) {
                updateStatus("Generate QR Code first!", Color.PINK);
                return;
            }
            if (coverImageFile == null) {
                updateStatus("Select a cover image first!", Color.PINK);
                return;
            }
            try {
                QRHider.hideQR(coverImageFile.getAbsolutePath(), qrFile.getAbsolutePath(), "hidden_output.png");
                updateStatus(" Hidden image saved as 'hidden_output.png'", new Color(144, 238, 144));
            } catch (Exception ex) {
                updateStatus("Error: " + ex.getMessage(), Color.ORANGE);
            }
        });

        // ----- Assemble -----
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void updateStatus(String text, Color color) {
        statusLabel.setText("Status: " + text);
        statusLabel.setForeground(color);
    }

    private JButton createFancyButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 123, 255));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 105, 230), 2),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover glow effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 150, 255));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 255, 255), 2),
                        BorderFactory.createEmptyBorder(12, 20, 12, 20)));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 123, 255));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 105, 230), 2),
                        BorderFactory.createEmptyBorder(12, 20, 12, 20)));
            }
        });

        return button;
    }
}
