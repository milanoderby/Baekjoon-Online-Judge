import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static int N, M;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        boolean[][] isIsland = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                isIsland[i][j] = Integer.parseInt(tokenizer.nextToken()) > 0;
            }
        }

        int countOfGroup = 0;
        boolean[][] isVisited = new boolean[N][M];
        int[][] map = new int[N][M];
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {
                if (isIsland[y][x] && !isVisited[y][x]) {
                    countOfGroup++;
                    bfs(isIsland, isVisited, y, x, countOfGroup, map);
                }
            }
        }

        List<Path> pathList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            List<Integer> islandList = new ArrayList<>();
            int bridge = 0;
            for (int j = 0; j < M; j++) {
                if (isIsland[i][j]) {
                    if (islandList.isEmpty()) {
                        islandList.add(map[i][j]);
                    } else if (islandList.get(islandList.size() - 1) != map[i][j]) {
                        if (bridge >= 2) {
                            pathList.add(new Path(islandList.get(islandList.size() - 1), map[i][j], bridge));
                        }
                        bridge = 0;
                        islandList.add(map[i][j]);
                    } else {
                        bridge = 0;
                    }
                } else if (!islandList.isEmpty()) {
                    bridge++;
                }
            }
        }

        for (int j = 0; j < M; j++) {
            List<Integer> islandList = new ArrayList<>();
            int bridge = 0;
            for (int i = 0; i < N; i++) {
                if (isIsland[i][j]) {
                    if (islandList.isEmpty()) {
                        islandList.add(map[i][j]);
                    } else if (islandList.get(islandList.size() - 1) != map[i][j]) {
                        if (bridge >= 2) {
                            pathList.add(new Path(islandList.get(islandList.size() - 1), map[i][j], bridge));
                        }
                        bridge = 0;
                        islandList.add(map[i][j]);
                    } else {
                        bridge = 0;
                    }
                } else if (!islandList.isEmpty()) {
                    bridge++;
                }
            }
        }
        Collections.sort(pathList, Comparator.comparingInt(Path::getDistance));

        int minCost = 0;
        int countOfEdge = 0;
        DisjointSet disjointSet = new DisjointSet(countOfGroup + 1);
        for (Path path : pathList) {
            int groupOfSrc = disjointSet.findGroup(path.getSrc());
            int groupOfDest = disjointSet.findGroup(path.getDest());
            if (groupOfSrc != groupOfDest) {
                disjointSet.unionGroup(groupOfSrc, groupOfDest);
                minCost += path.getDistance();
                countOfEdge++;
                if (countOfEdge == countOfGroup - 1) {
                    break;
                }
            }
        }

        if (countOfEdge == countOfGroup - 1) {
            bw.append(minCost + "");
        } else {
            bw.append(-1 + "");
        }
        bw.flush();
    }

    private static void bfs(boolean[][] isIsland, boolean[][] isVisited, int y, int x,  int countOfGroup, int[][] map) {
        isVisited[y][x] = true;
        Queue<Point> pointQueue = new LinkedList<>();
        pointQueue.add(new Point(x, y));
        map[y][x] = countOfGroup;

        int[] dy = {0, 0, -1, 1};
        int[] dx = {-1, 1, 0, 0};

        while (!pointQueue.isEmpty()) {
            Point cur = pointQueue.poll();
            for (int dir = 0; dir < 4; dir++) {
                int ny = cur.y + dy[dir];
                int nx = cur.x + dx[dir];

                if (ny < 0 || ny >= N || nx < 0 || nx >= M) {
                    continue;
                }

                if (isIsland[ny][nx] && !isVisited[ny][nx]) {
                    isVisited[ny][nx] = true;
                    pointQueue.add(new Point(nx, ny));
                    map[ny][nx] = countOfGroup;
                }
            }
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