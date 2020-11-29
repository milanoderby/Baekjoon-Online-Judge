import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static class Point {
        double x, y, z;
    }
    private static final int NUM_OF_POINT = 3;
    private static Point[] points = new Point[NUM_OF_POINT];
    private static int X = 0;
    private static int Y = 1;
    private static int Z = 2;

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            for (int i=0; i<NUM_OF_POINT; i++) {
                Point point = new Point();
                point.x = Integer.parseInt(tokenizer.nextToken());
                point.y = Integer.parseInt(tokenizer.nextToken());
                point.z = Integer.parseInt(tokenizer.nextToken());

                points[i] = point;
            }

            Point mid = getMidPoint(points[X], points[Y]);
            while(Math.abs(getDistance(points[X], points[Z]) - getDistance(mid, points[Z])) >= Math.pow(10, -6)
            && Math.abs(getDistance(points[Y], points[Z]) - getDistance(mid, points[Z])) >= Math.pow(10, -6)) {
                if(getDistance(mid, points[Z]) == 0){
                    System.out.println(0);
                    return;
                }
                if (getDistance(points[X], points[Z]) <= getDistance(points[Y], points[Z])){
                    points[Y] = getMidPoint(points[X], points[Y]);
                } else{
                    points[X] = getMidPoint(points[X], points[Y]);
                }
                mid = getMidPoint(points[X], points[Y]);
            }
            System.out.println(getDistance(mid, points[Z]));
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static Point getMidPoint(Point a, Point b){
        Point point = new Point();
        point.x = (a.x + b.x)/2;
        point.y = (a.y + b.y)/2;
        point.z = (a.z + b.z)/2;
        return point;
    }

    private static double getDistance(Point a, Point b){
        double distance = Math.sqrt(Math.pow((a.x - b.x), 2) +  Math.pow((a.y - b.y), 2) +  Math.pow((a.z - b.z), 2));
        return distance;
    }
}
