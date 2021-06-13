import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.Vector;

public class Main{
    private static int V, E;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        V = Integer.parseInt(tokenizer.nextToken());
        E = Integer.parseInt(tokenizer.nextToken());

        int src = Integer.parseInt(br.readLine());

        Vector<Path>[] directedGraph = new Vector[V + 1];
        for (int i = 0; i <= V; i++) {
            directedGraph[i] = new Vector<>();
        }

        for (int i = 0; i < E; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());

            directedGraph[u].add(new Path(v, w));
        }

        int[] distance = new int[V + 1];
        for (int i = 0; i <= V; i++) {
            distance[i]= Integer.MAX_VALUE;
        }

        distance[src] = 0;
        PriorityQueue<Path> minDistanceVertexPQ = new PriorityQueue<>(new PathComparator());
        minDistanceVertexPQ.add(new Path(src, 0));

        while (!minDistanceVertexPQ.isEmpty()) {
            Path path = minDistanceVertexPQ.poll();

            // 이미 현재 계산된 거리가 더 짧을 경우, 해당 경로는 최단거리계산에서 제외합니다.
            if (distance[path.vertex] < path.dist) {
                continue;
            }

            int currentVertex = path.vertex;
            for (Path next : directedGraph[currentVertex]) {
                int nextVertex = next.vertex;

                int currentDistance = distance[next.vertex];
                int newDistance = distance[currentVertex] + next.dist;

                // 시작점 ~ 다음정점까지의 거리보다
                // 시작점 ~ 현재정점까지의 거리 + 현재정점 ~ 다음정점까지의 거리가
                // 짧을 때
                // 다음정점까지의 거리 값을 갱신 & 최소거리정점을 구하는 우선순위 큐에 삽입
                if (newDistance < currentDistance) {
                    distance[nextVertex] = newDistance;
                    minDistanceVertexPQ.add(new Path(nextVertex, newDistance));
                }
            }
        }

        for (int i = 1; i <= V; i++) {
            if (distance[i] == Integer.MAX_VALUE) {
                System.out.println("INF");
            } else {
                System.out.println(distance[i]);
            }
        }
    }

    private static class Path {
        private int vertex;
        private int dist;

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