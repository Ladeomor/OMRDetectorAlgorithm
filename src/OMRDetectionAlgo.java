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
            File file = new File("images/OMR_SHEET_ONE.jpg");
            inputImage = ImageIO.read(file);
            if(inputImage != null){
//                omrDetectionAlgo.display(img);
                // inputImage = toGrayScale(inputImage);
                inputImage = shadeFinder(inputImage, 15, 15, 60);
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
        img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
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

    public static BufferedImage shadeFinder(BufferedImage img, int m, int n, int threshold){
        BufferedImage grayImage = null;
        grayImage = toGrayScale(img);
        for(int y= 0; y + n < grayImage.getHeight(); y++){
            for(int x = 0; x + m < grayImage.getWidth(); x++){
                int ave = averagePixelValue(grayImage, m, n, x, y);
                int rgb = 0;
                rgb = (255<<24) | (255<<16);
                if (ave < threshold) SetPixels(grayImage, m, n, x, y, rgb);
            }
        }
        return grayImage;
    }

    // use for black and white images only
    public static int averagePixelValue(BufferedImage img, int w, int h, int xpos, int ypos){
        int sum = 0;
        for (int j = ypos; j < ypos + h; j++) {
            for (int i = xpos; i < xpos + w; i++) {
                int redVal = (img.getRGB(xpos, ypos) >> 16) & 0xFF;
                sum += redVal;
            }
        }
        return  sum/(w*h);
    }


    public static void SetPixels(BufferedImage img, int w, int h, int xpos, int ypos, int val){
        for (int j = ypos; j < ypos + h; j++) {
            for (int i = xpos; i < xpos + w; i++) {
                img.setRGB(xpos, ypos, val);
            }
        }
    }

}