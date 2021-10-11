import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        Point[] points = new Point[3];
        for (int i = 0; i < 3; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());

            points[i] = new Point(x, y);
        }

        long ccw = ccw(points[0], points[1], points[2]);
        ccw = convertToSmallNum(ccw);
        bw.append(ccw + "");

        bw.flush();
    }

    private static long ccw(Point a, Point b, Point c) {
        return (long) (b.x - a.x) * (c.y - a.y) - (long) (b.y - a.y) * (c.x - a.x);
    }

    private static long convertToSmallNum(long number) {
        if (number == 0) {
            return 0;
        }
        return number > 0 ? 1 : -1;
    }

    private static class Point implements Comparable<Point> {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point p) {
            int compareByX = Integer.compare(x, p.x);
            if (compareByX != 0) {
                return compareByX;
            }

            return Integer.compare(y, p.y);
        }
    }
}