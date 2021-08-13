import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static List<Integer>[] myGraph;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int R = Integer.parseInt(tokenizer.nextToken());
        int Q = Integer.parseInt(tokenizer.nextToken());

        myGraph = new List[N + 1];
        for (int i = 0; i < myGraph.length; i++) {
            myGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            myGraph[u].add(v);
            myGraph[v].add(u);
        }

        boolean[] isVisited = new boolean[N + 1];
        int[] countOfSubTree = new int[N + 1];
        dfs(R, isVisited, countOfSubTree);

        for (int i = 0; i < Q; i++) {
            int u = Integer.parseInt(br.readLine());
            bw.append(countOfSubTree[u] + "\n");
        }
        bw.flush();
    }

    private static int dfs(int root, boolean[] isVisited, int[] countOfSubTree) {
        int count = 1;
        isVisited[root] = true;
        for (Integer vertex : myGraph[root]) {
            if (!isVisited[vertex]) {
                count += dfs(vertex, isVisited, countOfSubTree);
            }
        }
        return countOfSubTree[root] = count;
    }
}