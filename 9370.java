import java.io.*;
import java.util.*;

public class Main {
    private static final int MAX_DISTANCE = 50000001;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int V = Integer.parseInt(tokenizer.nextToken());
            int E = Integer.parseInt(tokenizer.nextToken());
            int numOfPossibleDestination = Integer.parseInt(tokenizer.nextToken());

            tokenizer = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(tokenizer.nextToken());
            int midpoint1 = Integer.parseInt(tokenizer.nextToken());
            int midpoint2 = Integer.parseInt(tokenizer.nextToken());

            Vector<Path>[] undirectedGraph = new Vector[V + 1];
            for (int j = 1; j <= V; j++) {
                undirectedGraph[j] = new Vector<>();
            }

            for (int j = 0; j < E; j++) {
                tokenizer = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(tokenizer.nextToken());
                int b = Integer.parseInt(tokenizer.nextToken());
                int d = Integer.parseInt(tokenizer.nextToken());

                undirectedGraph[a].add(new Path(b, d));
                undirectedGraph[b].add(new Path(a, d));
            }

            int[] possibleDestination = new int[numOfPossibleDestination];
            for (int j = 0; j < numOfPossibleDestination; j++) {
                possibleDestination[j] = Integer.parseInt(br.readLine());
            }

            int[] distanceFromSrc = new int[V + 1];
            int[] distanceFromMidpoint1 = new int[V + 1];
            int[] distanceFromMidpoint2 = new int[V + 1];

            initDistanceArray(V, distanceFromSrc);
            initDistanceArray(V, distanceFromMidpoint1);
            initDistanceArray(V, distanceFromMidpoint2);

            dijkstra(src, distanceFromSrc, undirectedGraph);
            dijkstra(midpoint1, distanceFromMidpoint1, undirectedGraph);
            dijkstra(midpoint2, distanceFromMidpoint2, undirectedGraph);

            // (시작점 ~ 목적점 최단거리) = (시작점 ~ 중간점1) + (중간점1 ~ 중간점2) + (중간점2 ~ 목적점)
            // (시작점 ~ 목적점 최단거리) = (시작점 ~ 중간점2) + (중간점2 ~ 중간점1) + (중간점1 ~ 목적점)
            // 위의 2개의 식 중 하나라도 성립하면,
            // (중간점1 ~ 중간점2) 경로를 지나는 것이 (시작점 ~ 목적점) 까지 가는 최단경로인 것임을 증명가능합니다.

            List<Integer> answerList = new ArrayList<>();
            for (int j = 0; j < numOfPossibleDestination; j++) {
                int dest = possibleDestination[j];
                if (distanceFromSrc[dest] == distanceFromSrc[midpoint1] + distanceFromMidpoint2[midpoint1] + distanceFromMidpoint2[dest]) {
                    answerList.add(dest);
                } else if(distanceFromSrc[dest] == distanceFromSrc[midpoint2] + distanceFromMidpoint1[midpoint2] + distanceFromMidpoint1[dest]) {
                    answerList.add(dest);
                }
            }

            Collections.sort(answerList);
            for (Integer answer : answerList) {
                bw.append(answer + " ");
            }
            bw.append("\n");
        }
        bw.flush();
    }

     private static void initDistanceArray(int V, int[] distanceArray) {
        for (int i = 1; i <= V; i++) {
            distanceArray[i] = MAX_DISTANCE;
        }
    }

    private static void dijkstra(int src, int[] distanceArray, Vector<Path>[] undirectedGraph) {
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