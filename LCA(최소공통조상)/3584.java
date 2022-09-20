import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
        int T = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            int N = Integer.parseInt(br.readLine());

            List<Integer>[] tree = new List[N + 1];
            for (int i = 1; i <= N; i++) {
                tree[i] = new ArrayList<>();
            }

            int[] parent = new int[N + 1];
            for (int i = 0; i < N - 1; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                int A = Integer.parseInt(tokenizer.nextToken());
                int B = Integer.parseInt(tokenizer.nextToken());

                parent[B] = A;
                tree[A].add(B);
            }

            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(tokenizer.nextToken());
            int node2 = Integer.parseInt(tokenizer.nextToken());

            int rootNode = -1;
            for (int node = 1; node <= N; node++) {
                if (parent[node] == 0) {
                    rootNode = node;
                    break;
                }
            }

            int[] depth = new int[N + 1];
            depth[rootNode] = 0;

            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(rootNode);
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                for (Integer next : tree[cur]) {
                    queue.add(next);
                    depth[next] = depth[cur] + 1;
                }
            }

            while (node1 != node2) {
                if (depth[node1] < depth[node2]) {
                    node2 = parent[node2];
                } else if (depth[node1] == depth[node2]) {
                    node1 = parent[node1];
                    node2 = parent[node2];
                } else {
                    node1 = parent[node1];
                }
            }

            bw.write(node1 + "\n");
        }
        bw.flush();
    }
}
