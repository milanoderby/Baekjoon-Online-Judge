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
    public static void main(String[] args) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        graph = new List[N + 1];
        for (int i = 0; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());

            graph[u].add(v);
            graph[v].add(u);
        }

        boolean[] isVisited = new boolean[N + 1];
        int[][] countOfEarlyAdapter = new int[N + 1][2];
        for (int i = 0; i <= N; i++) {
            countOfEarlyAdapter[i][0] = -1;
            countOfEarlyAdapter[i][1] = -1;
        }

        int count1 = dfs(1, true, isVisited, countOfEarlyAdapter);
        int count2 = dfs(1, false, isVisited, countOfEarlyAdapter);

        int answer = Math.min(count1, count2);
        bw.append(answer + "");
        bw.flush();
    }

    private static int dfs(int root, boolean rootIsEarlyAdapter, boolean[] isVisited, int[][] countOfEarlyAdapter) {
        int index = rootIsEarlyAdapter == true ? 1 : 0;
        if (countOfEarlyAdapter[root][index] != -1) {
            return countOfEarlyAdapter[root][index];
        }

        int count = rootIsEarlyAdapter == true ? 1 : 0;
        isVisited[root] = true;
        for (Integer next : graph[root]) {
            if (!isVisited[next]) {
                int temp = dfs(next, true, isVisited, countOfEarlyAdapter);
                if (rootIsEarlyAdapter) {
                    count += Math.min(temp, dfs(next, false, isVisited, countOfEarlyAdapter));
                } else {
                    count += temp;
                }
            }
        }
        isVisited[root] = false;

        return countOfEarlyAdapter[root][index] = count;
    }
}
