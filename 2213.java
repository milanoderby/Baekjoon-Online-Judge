import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static List<Integer>[] graph;
    private static int[] weightOfVertex;
    public static void main(String[] args) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        weightOfVertex = new int[N + 1];

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            weightOfVertex[i] = Integer.parseInt(tokenizer.nextToken());
        }

        graph = new List[N + 1];
        for (int i = 0; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());

            graph[u].add(v);
            graph[v].add(u);
        }

        boolean[] isVisited = new boolean[N + 1];
        int[][] maxWeightOfSubTree = new int[N + 1][2];
        for (int i = 0; i <= N; i++) {
            maxWeightOfSubTree[i][0] = -1;
            maxWeightOfSubTree[i][1] = -1;
        }

        bw.append(Math.max(getMaxWeightOfSubTree(1, true, isVisited, maxWeightOfSubTree), getMaxWeightOfSubTree(1, false, isVisited, maxWeightOfSubTree)) + "");
        bw.flush();
    }

    private static int getMaxWeightOfSubTree(int root, boolean rootIsIncluded, boolean[] isVisited, int[][] maxWeightOfSubTree) {
        int index = rootIsIncluded ? 1 : 0;
        if (maxWeightOfSubTree[root][index] != -1) {
            return maxWeightOfSubTree[root][index];
        }

        isVisited[root] = true;
        int weight = rootIsIncluded ? weightOfVertex[root] : 0;
        for (Integer next : graph[root]) {
            if (!isVisited[next]) {
                int tempWeight = getMaxWeightOfSubTree(next, false, isVisited, maxWeightOfSubTree);
                if (rootIsIncluded) {
                    weight += tempWeight;
                } else {
                    weight += Math.max(tempWeight, getMaxWeightOfSubTree(next, true, isVisited, maxWeightOfSubTree));
                }
            }
        }
        isVisited[root] = false;

        return maxWeightOfSubTree[root][index] = weight;
    }
}
