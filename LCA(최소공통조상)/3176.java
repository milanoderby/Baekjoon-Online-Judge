import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        List<Road>[] cityNetwork = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            cityNetwork[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(tokenizer.nextToken());
            int B = Integer.parseInt(tokenizer.nextToken());
            int C = Integer.parseInt(tokenizer.nextToken());

            cityNetwork[A].add(new Road(B, C));
            cityNetwork[B].add(new Road(A, C));
        }

        int rootNode = 1;

        boolean[] isVisited = new boolean[N + 1];
        isVisited[rootNode] = true;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(rootNode);

        int bitLengthOfN = Integer.toBinaryString(N).length();
        int[][] parent = new int[bitLengthOfN + 1][N + 1];

        int[] depth = new int[N + 1];
        depth[0] = -1;
        depth[rootNode] = 0;

        int[][] minDistanceToAncestor = new int[bitLengthOfN + 1][N + 1];
        minDistanceToAncestor[0][rootNode] = Integer.MAX_VALUE;

        int[][] maxDistanceToAncestor = new int[bitLengthOfN + 1][N + 1];
        maxDistanceToAncestor[0][rootNode] = -1;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (Road road : cityNetwork[cur]) {
                int next = road.getCity();
                if (isVisited[next]) {
                    continue;
                }
                isVisited[next] = true;
                queue.add(next);
                parent[0][next] = cur;
                depth[next] = depth[cur] + 1;

                int distance = road.getDistance();
                minDistanceToAncestor[0][next] = distance;
                maxDistanceToAncestor[0][next] = distance;
            }
        }

        for (int i = 1; i < bitLengthOfN; i++) {
            for (int j = 1; j <= N; j++) {
                int temp = parent[i - 1][j];
                parent[i][j] = parent[i - 1][temp];

                int minDistance = Math.min(minDistanceToAncestor[i - 1][j], minDistanceToAncestor[i - 1][temp]);
                minDistanceToAncestor[i][j] = minDistance;

                int maxDistance = Math.max(maxDistanceToAncestor[i - 1][j], maxDistanceToAncestor[i - 1][temp]);
                maxDistanceToAncestor[i][j] = maxDistance;
            }
        }

        int K = Integer.parseInt(br.readLine());
        for (int i = 0; i < K; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int D = Integer.parseInt(tokenizer.nextToken());
            int E = Integer.parseInt(tokenizer.nextToken());

            int deeperCity = depth[D] > depth[E] ? D : E;
            int shallowCity = depth[D] <= depth[E] ? D : E;

            int minDistance = Integer.MAX_VALUE;
            int maxDistance = Integer.MIN_VALUE;
            for (int j = bitLengthOfN; j >= 0; j--) {
                int ancestorOfDeeperCity = parent[j][deeperCity];
                if (depth[ancestorOfDeeperCity] >= depth[shallowCity]) {
                    minDistance = Math.min(minDistance, minDistanceToAncestor[j][deeperCity]);
                    maxDistance = Math.max(maxDistance, maxDistanceToAncestor[j][deeperCity]);
                    deeperCity = ancestorOfDeeperCity;
                }
            }

            if (deeperCity != shallowCity) {
                for (int j = bitLengthOfN; j >= 0; j--) {
                    int ancestorOfDeeperCity = parent[j][deeperCity];
                    int ancestorOfShallowCity = parent[j][shallowCity];
                    if (ancestorOfDeeperCity != ancestorOfShallowCity) {
                        int temp = Math.min(minDistanceToAncestor[j][deeperCity], minDistanceToAncestor[j][shallowCity]);
                        minDistance = Math.min(minDistance, temp);

                        temp = Math.max(maxDistanceToAncestor[j][deeperCity], maxDistanceToAncestor[j][shallowCity]);
                        maxDistance = Math.max(maxDistance, temp);

                        deeperCity = ancestorOfDeeperCity;
                        shallowCity = ancestorOfShallowCity;
                    }
                }
                int temp = Math.min(minDistanceToAncestor[0][deeperCity], minDistanceToAncestor[0][shallowCity]);
                minDistance = Math.min(minDistance, temp);

                temp = Math.max(maxDistanceToAncestor[0][deeperCity], maxDistanceToAncestor[0][shallowCity]);
                maxDistance = Math.max(maxDistance, temp);
            }

            bw.write(minDistance + " " + maxDistance + "\n");
        }
        bw.flush();
    }

    private static class Road {
        private int city;
        private int distance;

        public Road(int city, int distance) {
            this.city = city;
            this.distance = distance;
        }

        public int getCity() {
            return city;
        }

        public int getDistance() {
            return distance;
        }
    }
}