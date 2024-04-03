import java.awt.image.BufferedImage;
import java.util.*;;

public class MarkingScript{
    public BufferedImage BufferedImage;
    public List<ShadedArea> ShadedAreas;
    public int ColorThreshold;
    public int HorzGridSize;
    public int VertGridSize;
    public int TotalScore;
    
    public MarkingScript(BufferedImage bufferedImage, int colorThresh, int horzGridSize, int vertGridSize){
        ShadedAreas = new ArrayList<ShadedArea>();
        ColorThreshold = colorThresh;
        HorzGridSize = horzGridSize;
        VertGridSize = vertGridSize;

        BufferedImage = ImageProcessing.toGrayScale(bufferedImage);
        BufferedImage = ImageProcessing.Contrast(BufferedImage, 0, 190, 0, 1);
        BufferedImage = ImageProcessing.Contrast(BufferedImage, 0, 1, 0, 255);
    }

    public void EvaluateShaded(){
        for (ShadedArea shadedArea : ShadedAreas) {

            // question shaded areas
            if(shadedArea instanceof QuestionShadedArea){
                QuestionShadedArea QSA = (QuestionShadedArea)shadedArea;
                String[] tickedAnswers = OMRDetectionAlgo.shadeFinder(BufferedImage, HorzGridSize, VertGridSize, ColorThreshold, QSA.Rect, QSA.RowNo, QSA.Label, QSA.Label_Axis);
                int sectionScore = 0;
                for(int i = 0; i < tickedAnswers.length;  i++){
                    if(tickedAnswers[i]  == QSA.Answers[i]) sectionScore++;
                }
                QSA.Score = sectionScore;
                TotalScore += sectionScore;
            }

            // data shaded areas
            if(shadedArea instanceof DataShadedArea){
                DataShadedArea DSA = (DataShadedArea)shadedArea;
                String[] outputData = OMRDetectionAlgo.shadeFinder(BufferedImage, HorzGridSize, VertGridSize, ColorThreshold, DSA.Rect, DSA.RowNo, DSA.Label, DSA.Label_Axis);
                DSA.Data = "";
                for(int i = 0; i < outputData.length; i++){
                    if(outputData[i] == null) DSA.Data += " ";
                    else DSA.Data += outputData[i];
                }
            }
        }
    }
}