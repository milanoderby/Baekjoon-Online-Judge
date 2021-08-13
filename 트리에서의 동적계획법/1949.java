import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    private static List<Integer>[] graph;
    private static int[] countOfPerson;
    public static void main(String[] args) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        countOfPerson = new int[N + 1];
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            countOfPerson[i] = Integer.parseInt(tokenizer.nextToken());
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
        int[][] maxCountOfPerson = new int[N + 1][2];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j < 2; j++) {
                maxCountOfPerson[i][j] = -1;
            }
        }

        int answer = Math.max(getMaxCountOfPerson(1, true, isVisited, maxCountOfPerson), getMaxCountOfPerson(1, false, isVisited, maxCountOfPerson));
        bw.append(answer + "");
        bw.flush();
    }

    private static int getMaxCountOfPerson(int root, boolean rootIsIncluded, boolean[] isVisited, int[][] maxCountOfPerson) {
        int isIncluded = rootIsIncluded ? 1 : 0;
        if (maxCountOfPerson[root][isIncluded] != -1) {
            return maxCountOfPerson[root][isIncluded];
        }

        isVisited[root] = true;
        int count = rootIsIncluded ? countOfPerson[root] : 0;
        for (Integer next : graph[root]) {
            if (!isVisited[next]) {
                int ifNextIsNotIncluded = getMaxCountOfPerson(next, false, isVisited, maxCountOfPerson);
                if (rootIsIncluded) {
                    count += ifNextIsNotIncluded;
                } else {
                    int ifNextIsIncluded = getMaxCountOfPerson(next, true, isVisited, maxCountOfPerson);
                    count += Math.max(ifNextIsNotIncluded, ifNextIsIncluded);
                }
            }
        }
        isVisited[root] = false;

        return maxCountOfPerson[root][isIncluded] = count;
    }
}
