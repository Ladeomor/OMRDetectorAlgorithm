
public class rectangle{
    public Vector2 A;
    public Vector2 B;
    public Vector2 C;
    public Vector2 D;

    rectangle(Vector2 minmin, Vector2 maxmax){
        A = minmin;
        D = maxmax;
        B.x = D.x;
        B.y = A.y;
        C.x = A.x;
        C.y = D.y; 
    }
}