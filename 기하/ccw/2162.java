import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        Line[] lines = new Line[N];
        for (int i = 0; i < N; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(tokenizer.nextToken());
            int y1 = Integer.parseInt(tokenizer.nextToken());
            int x2 = Integer.parseInt(tokenizer.nextToken());
            int y2 = Integer.parseInt(tokenizer.nextToken());

            lines[i] = new Line(new Point(x1, y1), new Point(x2, y2));
        }

        DisjointSet disjointSet = new DisjointSet(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (intersect(lines[i], lines[j])) {
                    disjointSet.unionGroup(i, j);
                }
            }
        }

        bw.append(disjointSet.getCountOfGroup() + "\n");
        bw.append(disjointSet.getSizeOfBiggestGroup() + "\n");

        bw.flush();
    }

    private static long ccw(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    private static boolean intersect(Line l1, Line l2) {
        long ccwOfABC = ccw(l1.smallPoint, l1.bigPoint, l2.smallPoint);
        long ccwOfABD = ccw(l1.smallPoint, l1.bigPoint, l2.bigPoint);
        long ccwOfCDA = ccw(l2.smallPoint, l2.bigPoint, l1.smallPoint);
        long ccwOfCDB = ccw(l2.smallPoint, l2.bigPoint, l1.bigPoint);
        if (ccwOfABC * ccwOfABD == 0 && ccwOfCDA * ccwOfCDB == 0) {
            return l2.smallPoint.compareTo(l1.bigPoint) <= 0 && l1.smallPoint.compareTo(l2.bigPoint) <= 0;
        }
        return ccwOfABC * ccwOfABD <= 0 && ccwOfCDA * ccwOfCDB <= 0;
    }

    private static class DisjointSet {
        private int[] group;

        public DisjointSet(int size) {
            group = new int[size];
            for (int i = 0; i < size; i++) {
                group[i] = i;
            }
        }

        public void unionGroup(int one, int other) {
            int groupOfOne = findGroup(one);
            int groupOfOther = findGroup(other);
            if (groupOfOne < groupOfOther) {
                group[groupOfOther] = groupOfOne;
            } else {
                group[groupOfOne] = groupOfOther;
            }
        }

        public int findGroup(int one) {
            if (one == group[one]) {
                return one;
            }
            return group[one] = findGroup(group[one]);
        }

        public int getCountOfGroup() {
            boolean[] isGroup = new boolean[group.length];
            for (int i = 0; i < group.length; i++) {
                int groupNum = findGroup(i);
                isGroup[groupNum] = true;
            }

            int countOfGroup = 0;
            for (int i = 0; i < isGroup.length; i++) {
                if (isGroup[i]) {
                    countOfGroup++;
                }
            }
            return countOfGroup;
        }

        public int getSizeOfBiggestGroup() {
            int[] sizeOfGroup = new int[group.length];
            for (int i = 0; i < group.length; i++) {
                int groupNum = findGroup(i);
                sizeOfGroup[groupNum]++;
            }

            int sizeOfBiggestGroup = 0;
            for (int i = 0; i < sizeOfGroup.length; i++) {
                sizeOfBiggestGroup = Math.max(sizeOfBiggestGroup, sizeOfGroup[i]);
            }
            return sizeOfBiggestGroup;
        }
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