import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        Line[] lines = new Line[2];
        for (int i = 0; i < 2; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(tokenizer.nextToken());
            int y1 = Integer.parseInt(tokenizer.nextToken());
            int x2 = Integer.parseInt(tokenizer.nextToken());
            int y2 = Integer.parseInt(tokenizer.nextToken());

            lines[i] = new Line(new Point(x1, y1), new Point(x2, y2));
        }

        if (intersect(lines[0], lines[1])) {
            bw.append("1");
        } else {
            bw.append("0");
        }

        bw.flush();
    }

    private static long ccw(Point a, Point b, Point c) {
        return (long) (b.x - a.x) * (c.y - a.y) - (long) (b.y - a.y) * (c.x - a.x);
    }

    private static boolean intersect(Line l1, Line l2) {
        long ccwOfABC = ccw(l1.smallPoint, l1.bigPoint, l2.smallPoint);
        long ccwOfABD = ccw(l1.smallPoint, l1.bigPoint, l2.bigPoint);
        long ccwOfCDA = ccw(l2.smallPoint, l2.bigPoint, l1.smallPoint);
        long ccwOfCDB = ccw(l2.smallPoint, l2.bigPoint, l1.bigPoint);

        ccwOfABC = convertToSmallNum(ccwOfABC);
        ccwOfABD = convertToSmallNum(ccwOfABD);
        ccwOfCDA = convertToSmallNum(ccwOfCDA);
        ccwOfCDB = convertToSmallNum(ccwOfCDB);
        if (ccwOfABC * ccwOfABD == 0 && ccwOfCDA * ccwOfCDB == 0) {
            return l2.smallPoint.compareTo(l1.bigPoint) <= 0 && l1.smallPoint.compareTo(l2.bigPoint) <= 0;
        }
        return ccwOfABC * ccwOfABD <= 0 && ccwOfCDA * ccwOfCDB <= 0;
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

    private static class Line {
        private Point smallPoint;
        private Point bigPoint;

        public Line(Point p1, Point p2) {
            if (p1.compareTo(p2) < 0) {
                smallPoint = p1;
                bigPoint = p2;
            } else {
                smallPoint = p2;
                bigPoint = p1;
            }
        }
    }
}