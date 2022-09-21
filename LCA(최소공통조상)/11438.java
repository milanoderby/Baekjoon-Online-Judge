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
        List<Integer>[] tree = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(tokenizer.nextToken());
            int node2 = Integer.parseInt(tokenizer.nextToken());

            tree[node1].add(node2);
            tree[node2].add(node1);
        }

        int maxBitOfN = Integer.toBinaryString(N).length();
        int[][] parent = new int[maxBitOfN][N + 1];

        int rootNode = 1;

        boolean[] isVisited = new boolean[N + 1];
        isVisited[rootNode] = true;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(rootNode);

        int[] depth = new int[N + 1];
        depth[0] = -1;
        depth[rootNode] = 0;

        parent[0][rootNode] = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (Integer next : tree[cur]) {
                if (isVisited[next]) {
                    continue;
                }
                isVisited[next] = true;
                queue.add(next);
                depth[next] = depth[cur] + 1;
                parent[0][next] = cur;
            }
        }

        for (int i = 1; i < maxBitOfN; i++) {
            for (int j = 1; j <= N; j++) {
                int temp = parent[i - 1][j];
                parent[i][j] = parent[i - 1][temp];
            }
        }

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(tokenizer.nextToken());
            int node2 = Integer.parseInt(tokenizer.nextToken());

            // 노드의 깊이를 맞추는 작업
            int deepNode = depth[node1] > depth[node2] ? node1 : node2;
            int shallowNode = depth[node1] > depth[node2] ? node2 : node1;

            for (int j = maxBitOfN - 1; j >= 0; j--) {
                int ancestor = parent[j][deepNode];
                if (depth[ancestor] >= depth[shallowNode]) {
                    deepNode = ancestor;
                }
            }

            int lca = deepNode;
            if (deepNode != shallowNode) {
                for (int j = maxBitOfN - 1; j >= 0; j--) {
                    int ancestorOfDeepNode = parent[j][deepNode];
                    int ancestorOfShallowNode = parent[j][shallowNode];
                    if (ancestorOfDeepNode != ancestorOfShallowNode) {
                        deepNode = ancestorOfDeepNode;
                        shallowNode = ancestorOfShallowNode;
                    }
                }

                lca = parent[0][deepNode];
            }
            bw.write(lca + "\n");
        }
        bw.flush();
    }
}