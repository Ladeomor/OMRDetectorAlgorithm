import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OMRDetectionAlgo {
    public static void main(String[] args) {
        OMRDetectionAlgo omrDetectionAlgo = new OMRDetectionAlgo();
        BufferedImage img = null;
        try {
            // Provide the path to your image file
            File file = new File("images/roll.png");
            img = ImageIO.read(file);
            omrDetectionAlgo.display(img);
            System.out.println("Image loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    public void display (BufferedImage img) {
        //System.out.println("  Displaying image.");
        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        frame = new JFrame();
        frame.setSize(img.getWidth(), img.getHeight());
        label.setIcon(new ImageIcon(img));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}