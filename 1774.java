import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static List<Point> points;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int M = Integer.parseInt(tokenizer.nextToken());
        points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            points.add(new Point(x, y));
        }

        DisjointSet disjointSet = new DisjointSet(N);
        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(tokenizer.nextToken()) - 1;
            int dest = Integer.parseInt(tokenizer.nextToken()) - 1;

            disjointSet.unionGroup(src, dest);
        }

        List<Path> pathList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                pathList.add(new Path(i, j));
            }
        }
        Collections.sort(pathList, Comparator.comparingDouble(Path::getDistance));

        double minCost = 0;
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
        private int x;
        private int y;

        public Point(int x, int y) {
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