import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MenuFrame extends JFrame {

    public MenuFrame() {
        setTitle("Image Steganography Tool");
        setSize(920, 580);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(24, 24, 24));

        // ----- Left Panel: Title + Info -----
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(24, 24, 24));
        leftPanel.setPreferredSize(new Dimension(420, 0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(100, 30, 100, 30));

        JLabel title = new JLabel("<html><span style='color:#00e5ff;'>Image</span> Steganography Tool</html>");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        JLabel desc = new JLabel("<html><div style='margin-top:20px; color: #aaaaaa;'>🕵️‍♀️ Hide your secrets in plain sight. Choose an action to get started.</div></html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(title);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        leftPanel.add(desc);

        // ----- Right Panel: Buttons -----
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(33, 37, 41));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        JButton encodeBtn = createFancyButton("ENCODE IMAGE");
        JButton decodeBtn = createFancyButton("DECODE IMAGE");
        JButton qrBtn = createFancyButton("📷 QR CODE MODE");
        JButton extractBtn = createFancyButton("EXTRACT QR CODE");

        encodeBtn.addActionListener(e -> new Encryption().setVisible(true));
        decodeBtn.addActionListener(e -> new Decryption().setVisible(true));
        qrBtn.addActionListener(e -> new QRCodeWindow().setVisible(true));
        extractBtn.addActionListener(e -> new QRExtractWindow().setVisible(true));

        gbc.gridy = 0; rightPanel.add(encodeBtn, gbc);
        gbc.gridy = 1; rightPanel.add(decodeBtn, gbc);
        gbc.gridy = 2; rightPanel.add(qrBtn, gbc);
        gbc.gridy = 3; rightPanel.add(extractBtn, gbc);

        // ----- Footer -----
        JLabel footer = new JLabel("✨ Made with love by Shristi & Anwesha ✨");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setForeground(new Color(130, 130, 130));
        footer.setBorder(new EmptyBorder(10, 0, 10, 0));
        footer.setHorizontalAlignment(SwingConstants.CENTER);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    private JButton createFancyButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(270, 55));
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 123, 255));
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover Effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 150, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255));
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuFrame().setVisible(true));
    }
}
