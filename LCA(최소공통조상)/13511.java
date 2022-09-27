import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        List<Path>[] tree = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());

            tree[u].add(new Path(v, w));
            tree[v].add(new Path(u, w));
        }

        // 1. 경로의 비용 구하기
        // 1-1) u와 v의 LCA 구하기
        // 1-2) 경로의 비용 = (u와 LCA까지의 depth차이) + (v와 LCA까지의 depth차이)

        // 1-1) LCA 구하기
        // 1-1-1) 각각의 depth 구하기
        // 1-1-2) 각각의 parent[k][child] 배열 구하기

        int rootNode = 1;
        boolean[] isVisited = new boolean[N + 1];
        isVisited[rootNode] = true;
        
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(rootNode);
        
        int[] depth = new int[N + 1];
        depth[rootNode] = 0;
        depth[0] = -1;
        
        int bitCountOfN = Integer.toBinaryString(N).length();
        int[][] parent = new int[bitCountOfN + 1][N + 1];
        long[][] weightToParent = new long[bitCountOfN + 1][N +1];
        
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (Path path : tree[cur]) {
                int next = path.getVertex();
                int weight = path.getWeight();
                
                if (isVisited[next]) {
                    continue;
                }
                isVisited[next] = true;
                queue.add(next);
                
                depth[next] = depth[cur] + 1;
                parent[0][next] = cur;
                weightToParent[0][next] = weight;
            }
        }

        for (int i = 1; i < parent.length; i++) {
            for (int j = 1; j <= N; j++) {
                int temp = parent[i - 1][j];
                parent[i][j] = parent[i - 1][temp];

                weightToParent[i][j] = weightToParent[i - 1][j] + weightToParent[i - 1][temp];
            }
        }

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(tokenizer.nextToken());
            int u, v, k, lca, distanceFromUToLca, distanceFromVToLca;
            switch (command) {
                case 1:
                    u = Integer.parseInt(tokenizer.nextToken());
                    v = Integer.parseInt(tokenizer.nextToken());

                    long weight = getWeight(u, v, parent, depth, weightToParent, bitCountOfN);
                    bw.write(weight + "\n");
                    break;
                default:
                    u = Integer.parseInt(tokenizer.nextToken());
                    v = Integer.parseInt(tokenizer.nextToken());
                    k = Integer.parseInt(tokenizer.nextToken());

                    lca = getLca(u, v, parent, depth, bitCountOfN);
                    distanceFromUToLca = (depth[u] - depth[lca]);
                    distanceFromVToLca = (depth[v] - depth[lca]);
                    int answer;
                    if (k == 1) {
                        answer = u;
                    } else if (k - 1 <= distanceFromUToLca) {
                        answer = u;
                        for (int j = bitCountOfN; j >= 0; j--) {
                            int ancestorOfU = parent[j][answer];
                            if (depth[u] - depth[ancestorOfU] <= (k - 1)) {
                                answer = ancestorOfU;
                            }
                        }
                    } else {
                        int distanceFromVToAnswer = distanceFromVToLca - (k - 1 - distanceFromUToLca);
                        answer = v;
                        for (int j = bitCountOfN; j >= 0; j--) {
                            int ancestorOfV = parent[j][answer];
                            if (depth[v] - depth[ancestorOfV] <= distanceFromVToAnswer) {
                                answer = ancestorOfV;
                            }
                        }
                    }
                    // 1 - 2 - 4
                    //     ㄴ - 5
                    // ㄴ - 3 - 6
                    bw.write(answer + "\n");
                    break;
            }
        }
        bw.flush();
    }

    private static long getWeight(int u, int v, int[][] parent, int[] depth, long[][] weightToParent, int bitCountOfN) {
        int deepVertex = depth[u] > depth[v] ? u : v;
        int shallowVertex = depth[u] <= depth[v] ? u : v;

        long weightFromUToLca = 0;
        long weightFromVToLca = 0;
        for (int i = bitCountOfN; i >= 0; i--) {
            int ancestorOfDeepVertex = parent[i][deepVertex];
            if (depth[ancestorOfDeepVertex] >= depth[shallowVertex]) {
                weightFromUToLca += weightToParent[i][deepVertex];
                deepVertex = ancestorOfDeepVertex;
            }
        }

        if (deepVertex != shallowVertex) {
            for (int i = bitCountOfN; i >= 0; i--) {
                int ancestorOfDeepVertex = parent[i][deepVertex];
                int ancestorOfShallowVertex = parent[i][shallowVertex];
                if (ancestorOfDeepVertex != ancestorOfShallowVertex) {
                    weightFromUToLca += weightToParent[i][deepVertex];
                    weightFromVToLca += weightToParent[i][shallowVertex];

                    deepVertex = ancestorOfDeepVertex;
                    shallowVertex = ancestorOfShallowVertex;
                }
            }
            weightFromUToLca += weightToParent[0][deepVertex];
            weightFromVToLca += weightToParent[0][shallowVertex];
        }
        return weightFromUToLca + weightFromVToLca;
    }

    private static int getLca(int u, int v, int[][] parent, int[] depth, int bitCountOfN) {
        int deepVertex = depth[u] > depth[v] ? u : v;
        int shallowVertex = depth[u] <= depth[v] ? u : v;

        for (int i = bitCountOfN; i >= 0; i--) {
            int ancestorOfDeepVertex = parent[i][deepVertex];
            if (depth[ancestorOfDeepVertex] >= depth[shallowVertex]) {
                deepVertex = ancestorOfDeepVertex;
            }
        }
        int lca = deepVertex;

        if (deepVertex != shallowVertex) {
            for (int i = bitCountOfN; i >= 0; i--) {
                int ancestorOfDeepVertex = parent[i][deepVertex];
                int ancestorOfShallowVertex = parent[i][shallowVertex];
                if (ancestorOfDeepVertex != ancestorOfShallowVertex) {
                    deepVertex = ancestorOfDeepVertex;
                    shallowVertex = ancestorOfShallowVertex;
                }
            }

            lca = parent[0][deepVertex];
        }
        return lca;
    }

    private static class Path {
        private int vertex;
        private int weight;

        public Path(int vertex, int cost) {
            this.vertex = vertex;
            this.weight = cost;
        }

        public int getVertex() {
            return vertex;
        }

        public int getWeight() {
            return weight;
        }
    }
}