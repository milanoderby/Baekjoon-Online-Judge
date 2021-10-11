import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());

            points[i] = new Point(x, y);
        }

        long dimension = 0;
        for (int i = 1; i + 1 < N; i++) {
            long ccw = ccw(points[0], points[i], points[i + 1]);
            dimension += ccw;
        }
        dimension = Math.abs(dimension);

        double answer = (double) dimension / 2;
        answer *= 10;
        answer = Math.round(answer);
        answer /= 10;

        bw.append(String.format("%.1f", answer));
        bw.flush();
    }

    private static long ccw(Point a, Point b, Point c) {
        return (long) (b.x - a.x) * (c.y - a.y) - (long) (b.y - a.y) * (c.x - a.x);
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