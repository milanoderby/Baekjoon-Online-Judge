import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static int N, E;
    private static Vector<Path>[] undirectedGraph;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        E = Integer.parseInt(tokenizer.nextToken());

        undirectedGraph = new Vector[N + 1];
        for (int i = 0; i <= N; i++) {
            undirectedGraph[i] = new Vector<>();
        }

        for (int i = 0; i < E; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());

            undirectedGraph[u].add(new Path(v, w));
            undirectedGraph[v].add(new Path(u, w));
        }

        tokenizer = new StringTokenizer(br.readLine());
        int v1 = Integer.parseInt(tokenizer.nextToken());
        int v2 = Integer.parseInt(tokenizer.nextToken());

        int[] distanceFromV1 = new int[N + 1];
        int[] distanceFromV2 = new int[N + 1];

        initDistanceArray(distanceFromV1);
        initDistanceArray(distanceFromV2);

        dijkstra(v1, distanceFromV1);
        dijkstra(v2, distanceFromV2);


        getAnswer(distanceFromV1, distanceFromV1);

        // src - v1 - v2 - dest 경로의 거리계산

        int distFromV1ToSrc = distanceFromV1[1];
        int distFromV1ToV2 = distanceFromV1[v2];
        int distFromV2ToDest = distanceFromV2[N];

        int minDistance1 = Integer.MAX_VALUE;
        if (
                distFromV1ToSrc != Integer.MAX_VALUE
                && distFromV1ToV2 != Integer.MAX_VALUE
                && distFromV2ToDest != Integer.MAX_VALUE
        ) {
            minDistance1 = distFromV1ToSrc + distFromV1ToV2 + distFromV2ToDest;
        }

        // src - v2 - v1 - dest 경로의 거리계산
        int distFromV2ToSrc = distanceFromV2[1];
        int distFromV2ToV1 = distanceFromV2[v1];
        int distFromV1ToDest = distanceFromV1[N];

        int minDistance2 = Integer.MAX_VALUE;
        if (
                distFromV2ToSrc != Integer.MAX_VALUE
                        && distFromV2ToV1 != Integer.MAX_VALUE
                        && distFromV1ToDest != Integer.MAX_VALUE
        ) {
            minDistance2 = distFromV2ToSrc + distFromV2ToV1 + distFromV1ToDest;
        }

        int minDistance = Math.min(minDistance1, minDistance2);
        int answer = (minDistance == Integer.MAX_VALUE) ? -1 : minDistance;
        System.out.println(answer);
    }

     private static void initDistanceArray(int[] distanceArray) {
        for (int i = 1; i <= N; i++) {
            distanceArray[i] = Integer.MAX_VALUE;
        }
    }

    private static void dijkstra(int src, int[] distanceArray) {
        distanceArray[src] = 0;
        PriorityQueue<Path> minDistVertexPQ = new PriorityQueue<>(new PathComparator());
        minDistVertexPQ.add(new Path(src, 0));
        while (!minDistVertexPQ.isEmpty()) {
            Path current = minDistVertexPQ.poll();
            if (distanceArray[current.vertex] < current.dist) {
                continue;
            }
            for (Path next : undirectedGraph[current.vertex]) {
                int currentDist = distanceArray[next.vertex];
                int newDist = distanceArray[current.vertex] + next.dist;
                if (newDist < currentDist) {
                    distanceArray[next.vertex] = newDist;
                    minDistVertexPQ.add(new Path(next.vertex, newDist));
                }
            }
        }
    }


    private static void getAnswer(int[] distanceFromV1, int[] distanceFromV2) {

    }

    private static class Path {
        int vertex;
        int dist;

        public Path(int vertex, int dist) {
            this.vertex = vertex;
            this.dist = dist;
        }
    }

    private static class PathComparator implements Comparator<Path> {

        @Override
        public int compare(Path p1, Path p2) {
            if (p1.dist == p2.dist) {
                return p1.vertex - p2.vertex;
            }
            return p1.dist - p2.dist;
        }
    }
}