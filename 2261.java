import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    private static List<Point> points;
    private static Comparator<Point> compareByX;
    private static Comparator<Point> compareByY;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            compareByX = new ComparatorByX();
            compareByY = new ComparatorByY();

            N = Integer.parseInt(br.readLine());
            points = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(tokenizer.nextToken());
                int y = Integer.parseInt(tokenizer.nextToken());

                points.add(new Point(x, y));
            }

            Collections.sort(points, compareByX);

            System.out.println(divideAndConquer(0, N-1));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static int divideAndConquer(int start, int end) {
        if (end - start == 2) {
            int a = getDistance(points.get(start), points.get(start + 1));
            int b = getDistance(points.get(start), points.get(start + 2));
            int c = getDistance(points.get(start + 1), points.get(start + 2));
            return Math.min(Math.min(a, b), c);
        }

        if (end - start == 1) {
            return getDistance(points.get(start), points.get(start + 1));
        }

        int mid = (start + end) / 2;
        int d1 = divideAndConquer(start, mid);
        int d2 = divideAndConquer(mid + 1, end);

        int d = Math.min(d1, d2);
        int midLineX = (points.get(mid).x + points.get(mid+1).x) / 2;
        List<Point> newPoints = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            int xDiff = points.get(i).x - midLineX;
            if(xDiff * xDiff < d){
                newPoints.add(points.get(i));
            }
        }

        Collections.sort(newPoints, compareByY);
        for (int i = 0; i < newPoints.size() - 1; i++) {
            for (int j = i + 1; j < newPoints.size(); j++) {
                int yDiff = newPoints.get(j).y - newPoints.get(i).y;
                if (yDiff * yDiff > d) {
                    break;
                }
                int distanceBetweenIAndJ = getDistance(newPoints.get(i), newPoints.get(j));
                d = Math.min(d, distanceBetweenIAndJ);
            }
        }

        return d;
    }

    private static int getDistance (Point a, Point b) {
        return (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
    }

    private static class Point {
        int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class ComparatorByX implements Comparator<Point>{
        @Override
        public int compare(Point o1, Point o2) {
            return o1.x - o2.x;
        }
    };

    private static class ComparatorByY implements Comparator<Point>{
        @Override
        public int compare(Point o1, Point o2) {
            return o1.y - o2.y;
        }
    };
}
