
public class Rectangle{
    public Vector2 A;
    public Vector2 B;
    public Vector2 C;
    public Vector2 D;

    public Rectangle(Vector2 minmin, Vector2 maxmax){
        A = minmin;
        D = maxmax;
        B = new Vector2(0, 0);
        C = new Vector2(0, 0);

        B.x = D.x;
        B.y = A.y;
        C.x = A.x;
        C.y = D.y; 
    }
}