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
                // inputImage = toGrayScale(inputImage);
                inputImage = shadeFinder(inputImage, 11, 11, 100, new Rectangle(new Vector2(200, 50), new Vector2(560, 550)));
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

    // // convert grayscale image to BW
	// private static BufferedImage boostContrast(BufferedImage img) {
	// 	// compute average pixel darkness
	// 	int avg = 0;
	// 	for (int y=0; y<img.getHeight(); y++) {
	// 			for (int x=0; x<img.getWidth(); x++) {
	// 				avg += img.getRGB(x, y)& 0xFF;
	// 			}
	// 		}
	// 	avg /= img.getHeight() * img.getWidth();

	// 	// convert grayscale pixels in img to BW
	// 	for (int y=0; y<img.getHeight(); y++) {
	// 		for (int x=0; x<img.getWidth(); x++) {
	// 			int p = img.getRGB(x, y)& 0xFF;
	// 			if (p>avg)
	// 				p = (255<<24) | (255<<16) | (255<<8) | 255;
	// 			else
	// 				p = (255<<24) | (0<<16) | (0<<8) | 0;
	// 			img.setRGB(x, y, p);
	// 		}
	// 	}
	// 	return img;
	// }

    // scale Black and white image
    private static BufferedImage Contrast(BufferedImage img, float minOrg, float maxOrg, float minNew, float maxNew) {
		// convert grayscale pixels in img to BW
		for (int y=0; y<img.getHeight(); y++) {
			for (int x=0; x<img.getWidth(); x++) {
				int p = img.getRGB(x, y)& 0xFF;
                int newPix = (int)Lerp(minOrg, maxOrg, p, minNew, maxNew);
                if (newPix > 255) newPix = 255;
                if (newPix < 0) newPix =  0;
                int rgb = 0;
                rgb = (255<<24) | (newPix<<16) | (newPix<<8) | newPix;
				img.setRGB(x, y, rgb);
			}
		}
		return img;
	}

    public static float Lerp(float minOrg, float maxOrg, float valOrg, float minNew, float maxNew){
        return minOrg + ((maxNew - minNew) * (valOrg - minOrg))/(maxOrg - minOrg);
    }

    public static BufferedImage shadeFinder(BufferedImage img, int m, int n, int threshold, Rectangle rect){
        BufferedImage grayImage = null;
        grayImage = toGrayScale(img);
        grayImage = Contrast(grayImage, 0, 190, 0, 1);
        grayImage = Contrast(grayImage, 0, 1, 0, 255);
        if(rect.D.y >= grayImage.getHeight() && rect.D.x >= grayImage.getWidth()) return grayImage;

        for(int y = (int)rect.A.y ; y + n < rect.D.y; y++){
            for(int x = (int)rect.A.x; x + m < rect.D.x; x++){
                int ave = averagePixelValue(grayImage, m, n, x, y);
                
                if (ave < threshold){
                    int rgb = 0;
                    rgb = (255<<24) | (255<<16);
                    SetPixels(grayImage, m, n, x, y, rgb);
                    // System.out.println(ave);
                }
            }
        }
        return grayImage;
    }

    // use for black and white images only
    public static int averagePixelValue(BufferedImage img, int w, int h, int xpos, int ypos){
        int sum = 0;
        for (int j = ypos; j < ypos + h; j++) {
            for (int i = xpos; i < xpos + w; i++) {
                int redVal = ((int)img.getRGB(i, j) >> 16) & 0xFF;
                sum += redVal;
            }
        }
        return  sum/(w*h);
    }


    public static void SetPixels(BufferedImage img, int w, int h, int xpos, int ypos, int val){
        for (int j = ypos; j < ypos + h; j++) {
            for (int i = xpos; i < xpos + w; i++) {
                img.setRGB(i, j, val);
            }
        }
    }

}