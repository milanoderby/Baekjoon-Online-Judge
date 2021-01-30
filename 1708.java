import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    private static Point pivotPoint;
    private static minYXComparator minYXComp;
    private static ccwComparator ccwComp;

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int N = Integer.parseInt(br.readLine());

            List<Point> pointList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(tokenizer.nextToken());
                int y = Integer.parseInt(tokenizer.nextToken());

                Point cur = new Point(x, y);
                pointList.add(cur);
            }

            minYXComp = new minYXComparator();
            Collections.sort(pointList, minYXComp);
            pivotPoint = pointList.get(0);

            ccwComp = new ccwComparator();
            Collections.sort(pointList.subList(1, N), ccwComp);

            Stack<Point> pointStack = new Stack<>();
            pointStack.push(pointList.get(0));
            pointStack.push(pointList.get(1));

            for (int i = 2; i < N; i++) {
                Point third = pointList.get(i);

                while (pointStack.size() >= 2) {
                    Point second = pointStack.peek();
                    pointStack.pop();
                    Point first = pointStack.peek();

                    if (ccw(first, second, third) > 0) {
                        pointStack.push(second);
                        break;
                    }
                }
                pointStack.push(third);
            }

            System.out.println(pointStack.size());
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static class Point {
        long x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static int ccw(Point a, Point b, Point c) {
        long ccw = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);

        if (ccw > 0)
            return 1;

        if (ccw < 0)
            return -1;

        return 0;
    }

    private static class minYXComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            if (o1.y == o2.y) {
                return (int) (o1.x - o2.x);
            }
            return (int) (o1.y - o2.y);
        }
    }

    private static long getDistance(Point p1, Point p2) {
        return (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) + (p2.y - p1.y);
    }

    private static class ccwComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            int direction = -1 * ccw(pivotPoint, o1, o2);
            if (direction == 0) {
                return getDistance(pivotPoint, o1) - getDistance(pivotPoint, o2) > 0 ? 1 : -1;
            }
            return direction;
        }
    }
}