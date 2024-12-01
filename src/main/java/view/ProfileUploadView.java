package view;

import data_access.Cloudinary.CloudinaryUploader;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ProfileUploadView extends JFrame {
    private JLabel imageLabel;
    private File selectedFile;
    private String uploadedImageUrl;

    public ProfileUploadView() {
        setTitle("Change Profile Picture");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // UI Componentss
        JButton selectImageButton = new JButton("Select Image");
        JButton saveButton = new JButton("Save");
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Set up layout
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectImageButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
        add(imageLabel, BorderLayout.CENTER);

        // Button actions
        selectImageButton.addActionListener(e -> selectImage());

        saveButton.addActionListener(e -> saveImage());
    }

    private void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            try {
                // Correct orientation if needed
                BufferedImage originalImage = ImageIO.read(selectedFile);
                BufferedImage orientedImage = correctOrientation(originalImage, selectedFile);

                // Scale image while preserving aspect ratio
                ImageIcon imageIcon = new ImageIcon(scaleImage(orientedImage, 400, 430));
                imageLabel.setIcon(imageIcon);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveImage() {
        if (selectedFile != null) {
            // Initialize CloudinaryUploader and upload image
            CloudinaryUploader uploader = new CloudinaryUploader();
            uploadedImageUrl = uploader.uploadImage(selectedFile);

            if (uploadedImageUrl != null) {
                System.out.println("Uploaded Image URL: " + uploadedImageUrl);
                JOptionPane.showMessageDialog(this, "Image uploaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Image upload failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No image selected", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public String getUploadedImageUrl() {
        return uploadedImageUrl;
    }

    // Correct image orientation via EXIF data
    private BufferedImage correctOrientation(BufferedImage image, File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                switch (orientation) {
                    case 6: // Rotate 90 deg clockwise
                        return rotateImage(image, 90);
                    case 3: // Rotate 180 deg
                        return rotateImage(image, 180);
                    case 8: // Rotate 90 deg counterclockwise
                        return rotateImage(image, 270);
                    default: // No rotation
                        return image;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image; // Return original if no orientation issues
    }

    // Rotate image by a certain given angle
    private BufferedImage rotateImage(BufferedImage image, int angle) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(Math.toRadians(angle), height / 2.0, height / 2.0);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotatedImage;
    }

    // Scale image while maintaining image ratio
    private Image scaleImage(BufferedImage image, int maxWidth, int maxHeight) {
        int width = image.getWidth();
        int height = image.getHeight();

        double aspectRatio = (double) width / height;

        if (width > maxWidth) {
            width = maxWidth;
            height = (int) (width / aspectRatio);
        }

        if (height > maxHeight) {
            height = maxHeight;
            width = (int) (height * aspectRatio);
        }

        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

//    Note from Yoli: Please don't remove this, Khob Khun Ka!

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            ProfileUploadView view = new ProfileUploadView();
//            view.setVisible(true);
//        });
//    }
}
