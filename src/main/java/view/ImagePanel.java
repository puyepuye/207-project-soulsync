package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImagePanel extends JPanel {
    private BufferedImage image;

    // Constructor to load image from URL
    public ImagePanel(String imageUrl) {
        try {
            // Load the image from the URL
            URL url = new URL(imageUrl);
            this.image = ImageIO.read(url);  // Read the image from the URL
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Determine the scale factor to fit the image width-wise or height-wise
            float scale = Math.max((float) panelWidth / imgWidth, (float) panelHeight / imgHeight);

            // Calculate the new image dimensions
            int scaledWidth = (int) (imgWidth * scale);
            int scaledHeight = (int) (imgHeight * scale);

            // Calculate x and y to center the image
            int x = (panelWidth - scaledWidth) / 2;
            int y = (panelHeight - scaledHeight) / 2;

            // Draw the scaled image, centered within the panel
            g.drawImage(image, x, y, scaledWidth, scaledHeight, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(330, 200); // Size to match the layout
    }
}
