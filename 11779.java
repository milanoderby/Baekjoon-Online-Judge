import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());

        int[] distance = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        int[] pastVertex = new int[N + 1];

        Vector<Path>[] cityGraph = new Vector[N + 1];
        for (int i = 0; i <= N; i++) {
            cityGraph[i] = new Vector<>();
        }

        for (int i = 0; i < M; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(tokenizer.nextToken());
            int dest = Integer.parseInt(tokenizer.nextToken());
            int cost = Integer.parseInt(tokenizer.nextToken());

            cityGraph[src].add(new Path(dest, cost));
        }
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int src = Integer.parseInt(tokenizer.nextToken());
        int dest = Integer.parseInt(tokenizer.nextToken());

        distance[src] = 0;
        PriorityQueue<Path> pathPriorityQueue = new PriorityQueue<>(new PathComparator());
        pathPriorityQueue.add(new Path(src, 0));

        while (!pathPriorityQueue.isEmpty()) {
            Path cur = pathPriorityQueue.poll();
            if (distance[cur.dest] < cur.cost) {
                continue;
            }
            for (Path next : cityGraph[cur.dest]) {
                if (distance[next.dest] > distance[cur.dest] + next.cost) {
                    distance[next.dest] = distance[cur.dest] + next.cost;
                    pastVertex[next.dest] = cur.dest;

                    pathPriorityQueue.add(new Path(next.dest, distance[next.dest]));
                }
            }
        }

        bw.append(distance[dest] + "\n");

        List<Integer> minCostPath = new ArrayList<>();
        int pointOnMinCostPath = dest;
        while (pointOnMinCostPath != 0) {
            minCostPath.add(pointOnMinCostPath);
            pointOnMinCostPath = pastVertex[pointOnMinCostPath];
        }

        bw.append(minCostPath.size() + "\n");
        Collections.reverse(minCostPath);
        for (Integer vertex : minCostPath) {
            bw.append(vertex + " ");
        }
        bw.flush();
    }

    private static class Path {
        private int dest;
        private int cost;

        public Path(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }

    private static class PathComparator implements Comparator<Path> {

        @Override
        public int compare(Path p1, Path p2) {
            if (p1.cost == p2.cost) {
                return p1.dest - p2.dest;
            }
            return p1.cost - p2.cost;
        }
    }
}
