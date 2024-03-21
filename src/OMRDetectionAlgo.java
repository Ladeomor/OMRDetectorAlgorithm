import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OMRDetectionAlgo {
    public static void main(String[] args) {
        OMRDetectionAlgo omrDetectionAlgo = new OMRDetectionAlgo();
        BufferedImage inputImage = null;
        try {
            // Provide the path to your image file
            File file = new File("images/OMR_SHEET_TWO.jpg");
            inputImage = ImageIO.read(file);
            if(inputImage != null){
//                omrDetectionAlgo.display(img);
                inputImage = toGrayScale(inputImage);
                omrDetectionAlgo.display(inputImage);
                System.out.println("Image loaded successfully!");

            }

        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    //display image
    public void display (BufferedImage img) {
        System.out.println("  Displaying image.");
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
   //convert image to grayscale
    public static BufferedImage toGrayScale (BufferedImage img) {
        System.out.println("Converting to GrayScale.");

        BufferedImage grayImage = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        int rgb=0, r=0, g=0, b=0;
        for (int y=0; y<img.getHeight(); y++) {
            for (int x=0; x<img.getWidth(); x++) {
                rgb = (int)(img.getRGB(x, y));
                r = ((rgb >> 16) & 0xFF);
                g = ((rgb >> 8) & 0xFF);
                b = (rgb & 0xFF);
                rgb = (int)((r+g+b)/3);
                rgb = (255<<24) | (rgb<<16) | (rgb<<8) | rgb;
                grayImage.setRGB(x,y,rgb);
            }
        }

        return grayImage;
    }




}