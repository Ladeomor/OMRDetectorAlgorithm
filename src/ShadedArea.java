
public class ShadedArea{
    public String SectionTitle;
    public Rectangle Rect;
    public int RowNo;
    public String[] Label;
    public LabelAxis Label_Axis;

    public ShadedArea(String sectionTitle, Rectangle rect, int rowNo, String[] label, LabelAxis labelAxis){
        SectionTitle = sectionTitle;
        Rect = rect;
        RowNo = rowNo;
        Label = label;
        Label_Axis = labelAxis;
    }
}