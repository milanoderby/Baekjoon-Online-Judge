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
        points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            int z = Integer.parseInt(tokenizer.nextToken());
            points.add(new Point(i, x, y, z));
        }

        DisjointSet disjointSet = new DisjointSet(N);

        List<Path> pathList = new ArrayList<>();
        Collections.sort(points, Comparator.comparingInt(Point::getX));
        for (int i = 0; i + 1 < N; i++) {
            pathList.add(new Path(points.get(i).getIndex(), points.get(i + 1).getIndex(), points.get(i + 1).getX() - points.get(i).getX()));
        }

        Collections.sort(points, Comparator.comparingInt(Point::getY));
        for (int i = 0; i + 1 < N; i++) {
            pathList.add(new Path(points.get(i).getIndex(), points.get(i + 1).getIndex(), points.get(i + 1).getY() - points.get(i).getY()));
        }

        Collections.sort(points, Comparator.comparingInt(Point::getZ));
        for (int i = 0; i + 1 < N; i++) {
            pathList.add(new Path(points.get(i).getIndex(), points.get(i + 1).getIndex(), points.get(i + 1).getZ() - points.get(i).getZ()));
        }

        Collections.sort(pathList, Comparator.comparingInt(Path::getDistance));
        long minCost = 0;
        for (Path path : pathList) {
            if (disjointSet.findGroup(path.getSrc()) == disjointSet.findGroup(path.getDest())) {
                continue;
            }
            disjointSet.unionGroup(path.getSrc(), path.getDest());
            minCost += path.getDistance();
        }

        bw.append(minCost + "");
        bw.flush();
    }

    private static class Point {
        private int index;
        private int x;
        private int y;
        private int z;

        public Point(int index, int x, int y, int z) {
            this.index = index;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getIndex() {
            return index;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }

    private static class Path {
        private int src;
        private int dest;
        private int distance;

        public Path(int src, int dest, int distance) {
            this.src = src;
            this.dest = dest;
            this.distance = distance;
        }

        public int getSrc() {
            return src;
        }

        public int getDest() {
            return dest;
        }

        public int getDistance() {
            return distance;
        }

        private double getDistanceBetweenPoint (Point p1, Point p2) {
            return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        }
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
}