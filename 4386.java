import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static List<Point> points;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(tokenizer.nextToken());
            double y = Double.parseDouble(tokenizer.nextToken());
            points.add(new Point(x, y));
        }

        List<Path> pathList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                pathList.add(new Path(i, j));
            }
        }
        Collections.sort(pathList, Comparator.comparingDouble(Path::getDistance));

        double minCost = 0;
        DisjointSet disjointSet = new DisjointSet(n);
        for (Path path : pathList) {
            if (disjointSet.findGroup(path.getSrc()) == disjointSet.findGroup(path.getDest())) {
                continue;
            }
            disjointSet.unionGroup(path.getSrc(), path.getDest());
            minCost += path.getDistance();
        }

        minCost *= 100;
        minCost = Math.round(minCost);
        minCost /= 100;
        bw.append(String.format("%.2f", minCost));
        bw.flush();
    }

    private static class DisjointSet {
        private int[] group;

        public DisjointSet(int size) {
            group = new int[size];
            for (int i = 0; i < size; i++) {
                group[i] = i;
            }
        }

        public void unionGroup(int a, int b) {
            int groupOfA = findGroup(a);
            int groupOfB = findGroup(b);

            int min = Math.min(groupOfA, groupOfB);
            int max = Math.max(groupOfA, groupOfB);
            group[max] = min;
        }

        public int findGroup(int i) {
            if (group[i] == i) {
                return i;
            }
            return group[i] = findGroup(group[i]);
        }
    }

    private static class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Path {
        private int src;
        private int dest;
        private double distance;

        public Path(int src, int dest) {
            this.src = src;
            this.dest = dest;
            this.distance = getDistanceBetweenPoint(points.get(src), points.get(dest));
        }

        public int getSrc() {
            return src;
        }

        public int getDest() {
            return dest;
        }

        public double getDistance() {
            return distance;
        }

        private double getDistanceBetweenPoint (Point p1, Point p2) {
            return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        }
    }
}