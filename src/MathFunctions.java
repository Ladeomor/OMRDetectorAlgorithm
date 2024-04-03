
public class MathFunctions{
    public static float Lerp(float minOrg, float maxOrg, float valOrg, float minNew, float maxNew){
        return minNew + ((maxNew - minNew) * (valOrg - minOrg))/(maxOrg - minOrg);
    }
}