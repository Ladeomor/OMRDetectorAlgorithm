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

                // inputImage = shadeFinder(inputImage, 11, 11, 100, new Rectangle(new Vector2(240, 48), new Vector2(301, 548)), 35, new String[]{"A", "B", "C", "D"}, LabelAxis.horizontal);
                // inputImage = shadeFinder(inputImage, 32, 32, 100, new Rectangle(new Vector2(36, 150), new Vector2(1350, 1290)), 30, new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}, LabelAxis.vertical);

                MarkingScript script = new MarkingScript(inputImage, 100, 11, 11);
                script.ShadedAreas.add(new QuestionShadedArea("1-35", new Rectangle(new Vector2(240, 48), new Vector2(301, 548)), 35, new String[]{"A", "B", "C", "D"}, LabelAxis.horizontal, new String[]{"A", "B", "C", "D", "B", "C", "C", "B", "A", "B", "D", "B", "C", "B", "C", "B", "A", "D", "C", "C", "B", "C", "A", "A", "A", "D", "A", "B", "B", "B", "C", "C", "B", "B", "D", "D"}));
                script.ShadedAreas.add(new DataShadedArea("Exam no", new Rectangle(new Vector2(57, 277), new Vector2(157, 417)), 7, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}, LabelAxis.vertical));

                script.EvaluateShaded();
                System.out.println("total score: " + script.TotalScore);
                System.out.println("Exam no: " + ((DataShadedArea)(script.ShadedAreas.get(1))).Data);
                omrDetectionAlgo.display(script.BufferedImage);

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
    

    

    public static String[] shadeFinder(BufferedImage grayImage, int m, int n, int threshold, Rectangle rect, int rowNo, String[] label, LabelAxis labelAxis){
        
        if(rect.D.y >= grayImage.getHeight() && rect.D.x >= grayImage.getWidth()) return null;
        String[] output = new String[rowNo];

        for(int y = (int)rect.A.y ; y + n < rect.D.y; y++){
            for(int x = (int)rect.A.x; x + m < rect.D.x; x++){
                int ave = averagePixelValue(grayImage, m, n, x, y);
                
                // Area is shaded
                if (ave < threshold){
                    int rgb = 0;
                    rgb = (255<<24) | (255<<16);
                    SetPixels(grayImage, m, n, x, y, rgb);

                    switch (labelAxis) {
                        case horizontal:
                            // int outputIndex = (int)Lerp(rect.A.y, rect.D.y, y + n/2, 0, rowNo);
                            // int labelIndex = (int)Lerp(rect.A.x, rect.D.x, x + m/2, 0, label.length );
                            // System.out.println(outputIndex);

                            output[(int)MathFunctions.Lerp(rect.A.y, rect.D.y, y + n/2, 0, rowNo)] = label[(int)MathFunctions.Lerp(rect.A.x, rect.D.x, x + m/2, 0, label.length)];
                            break;
                    
                        case vertical:
                            output[(int)MathFunctions.Lerp(rect.A.x, rect.D.x, x + m/2, 0, rowNo)] = label[(int)MathFunctions.Lerp(rect.A.y, rect.D.y, y + n/2, 0, label.length)];
                            break;
                    }
                    // System.out.println(ave);
                }
            }
        }

        // for(int i = 0; i < output.length;  i++)  System.out.println(output[i]);
        return output;
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