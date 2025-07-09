package org.example.Views;

import org.example.Utils.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeFrame extends JFrame {
    private JSplitPane splitPane;

    public HomeFrame() {
        setTitle("POS - Trang Chủ");
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel imagePanel = createImagePanel();
        Font buttonFont = new Font("Quicksand", Font.PLAIN, 16);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JButton btnProduct = new JButton("Quản lý sách");
        btnProduct.setFont(buttonFont);

        JButton btnCategory = new JButton("Quản lý danh mục");
        btnCategory.setFont(buttonFont);

        JButton btnUser = new JButton("Quản lý tài khoản");
        btnUser.setFont(buttonFont);

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setFont(buttonFont);

        buttonPanel.add(btnProduct);
        buttonPanel.add(btnCategory);
        buttonPanel.add(btnUser);
        buttonPanel.add(btnLogout);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, buttonPanel);
        splitPane.setDividerLocation(3 * getWidth() / 5);
        splitPane.setDividerSize(0);

        add(splitPane, BorderLayout.CENTER);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation(3 * getWidth() / 5);
            }
        });

        btnProduct.addActionListener(e -> FrameManager.showBookFrame());
        btnUser.addActionListener(e -> FrameManager.showUserFrame());
        btnCategory.addActionListener(e -> FrameManager.showCategoryFrame());
        btnLogout.addActionListener(e -> {
            FrameManager.closeAll();
            logout();
        });

    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel() {
            private Image backgroundImage;
            {
                try {
                    URL url = new URL("https://wallpapers.com/images/hd/hefty-cloud-starr-night-sky-moon-mvoalnkqqtnrc6bz.jpg");
                    backgroundImage = new ImageIcon(url).getImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        imagePanel.setPreferredSize(new Dimension(600, 400)); // hoặc kích thước cụ thể

        return imagePanel;
    }


    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void logout() {
        dispose();
        JOptionPane.showMessageDialog(this, "Bạn đã đăng xuất!");
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}