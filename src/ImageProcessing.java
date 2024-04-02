import java.awt.image.BufferedImage;

public class ImageProcessing{
    
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

    // transform an Image to pure a Black and white image
    public static BufferedImage Contrast(BufferedImage img, float minOrg, float maxOrg, float minNew, float maxNew) {
		for (int y=0; y<img.getHeight(); y++) {
			for (int x=0; x<img.getWidth(); x++) {
				int p = img.getRGB(x, y)& 0xFF;
                int newPix = (int)MathFunctions.Lerp(minOrg, maxOrg, p, minNew, maxNew);
                if (newPix > 255) newPix = 255;
                if (newPix < 0) newPix =  0;
                int rgb = 0;
                rgb = (255<<24) | (newPix<<16) | (newPix<<8) | newPix;
				img.setRGB(x, y, rgb);
			}
		}
		return img;
	}
}