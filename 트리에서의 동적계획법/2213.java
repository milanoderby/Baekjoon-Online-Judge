import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

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

        int weight1 = getMaxWeightOfSubTree(1, true, isVisited, maxWeightOfSubTree);
        int weight2 = getMaxWeightOfSubTree(1, false, isVisited, maxWeightOfSubTree);

        List<Integer> independentSet = new ArrayList<>();
        if (weight1 >= weight2) {
            bw.append(weight1 + "\n");
            getIndependentSet(1, true, isVisited, maxWeightOfSubTree, independentSet);
        } else {
            bw.append(weight2 + "\n");
            getIndependentSet(1, false, isVisited, maxWeightOfSubTree, independentSet);
        }
        Collections.sort(independentSet);
        for (Integer integer : independentSet) {
            bw.append(integer + " ");
        }

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
                int weightIfNextNotIncuded = getMaxWeightOfSubTree(next, false, isVisited, maxWeightOfSubTree);
                if (rootIsIncluded) {
                    weight += weightIfNextNotIncuded;
                } else {
                    int weightIfNextIncuded = getMaxWeightOfSubTree(next, true, isVisited, maxWeightOfSubTree);
                    weight += Math.max(weightIfNextIncuded, weightIfNextNotIncuded);
                }
            }
        }
        isVisited[root] = false;
        return maxWeightOfSubTree[root][index] = weight;
    }

    private static void getIndependentSet(int root, boolean rootIsIncluded, boolean[] isVisited, int[][] maxWeightOfSubTree, List<Integer> independentSet) {
        isVisited[root] = true;
        if (rootIsIncluded) {
            independentSet.add(root);
            for (Integer next : graph[root]) {
                if (!isVisited[next]) {
                    getIndependentSet(next, false, isVisited, maxWeightOfSubTree, independentSet);
                }
            }
        } else {
            for (Integer next : graph[root]) {
                if (!isVisited[next]) {
                    int weight1 = maxWeightOfSubTree[next][1];
                    int weight2 = maxWeightOfSubTree[next][0];

                    if (weight1 >= weight2) {
                        getIndependentSet(next, true, isVisited, maxWeightOfSubTree, independentSet);
                    } else {
                        getIndependentSet(next, false, isVisited, maxWeightOfSubTree, independentSet);
                    }
                }
            }
        }
    }
}
