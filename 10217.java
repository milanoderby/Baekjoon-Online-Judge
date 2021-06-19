import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.Vector;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(tokenizer.nextToken());
            int M = Integer.parseInt(tokenizer.nextToken());
            int K = Integer.parseInt(tokenizer.nextToken());

            // distance[i][j]: 정점 i까지 최소비용 j를 사용해서 도착하는 소요시간
            int distance[][] = new int[N + 1][M + 1];
            initDistanceArray(distance, N, M);

            Vector<Path>[] myGraph = new Vector[N + 1];
            for (int j = 1; j <= N; j++) {
                myGraph[j] = new Vector<>();
            }

            for (int j = 0; j < K; j++) {
                tokenizer = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(tokenizer.nextToken());
                int v = Integer.parseInt(tokenizer.nextToken());
                int c = Integer.parseInt(tokenizer.nextToken());
                int d = Integer.parseInt(tokenizer.nextToken());

                myGraph[u].add(new Path(v, c, d));
            }

            PriorityQueue<Path> minDistVertexPQ = new PriorityQueue<>(new PathComparator());
            distance[1][0] = 0;
            minDistVertexPQ.add(new Path(1, 0, 0));
            while (!minDistVertexPQ.isEmpty()) {
                Path cur = minDistVertexPQ.poll();
                if (distance[cur.vertex][cur.cost] < cur.dist) {
                    continue;
                }

                for (Path next : myGraph[cur.vertex]) {
                    if (cur.cost + next.cost > M) {
                        continue;
                    }

                    if (distance[next.vertex][cur.cost + next.cost] > distance[cur.vertex][cur.cost] + next.dist) {
                        distance[next.vertex][cur.cost + next.cost] = distance[cur.vertex][cur.cost] + next.dist;
                        minDistVertexPQ.add(new Path(next.vertex, cur.cost + next.cost, distance[next.vertex][cur.cost + next.cost]));
                    }
                }
            }

            int minDistance = Integer.MAX_VALUE;
            for (int j = 1; j <= M; j++) {
                if (minDistance > distance[N][j]) {
                    minDistance = distance[N][j];
                }
            }

            if (minDistance == Integer.MAX_VALUE) {
                bw.append("Poor KCM\n");
            } else {
                bw.append(minDistance + "\n");
            }
        }
        bw.flush();
    }

    private static void initDistanceArray(int distance[][], int N, int M) {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    private static class Path {
        int vertex;
        int cost;
        int dist;

        public Path(int vertex, int cost, int dist) {
            this.vertex = vertex;
            this.cost = cost;
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