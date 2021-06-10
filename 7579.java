import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int M = Integer.parseInt(tokenizer.nextToken());

        int[] memory = new int[N];
        int[] cost = new int[N];
        int sumOfCost = 0;

        tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            memory[i] = Integer.parseInt(tokenizer.nextToken());
        }

        tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            cost[i] = Integer.parseInt(tokenizer.nextToken());
            sumOfCost += cost[i];
        }

        // 1번째 ~ N번째 앱까지 고려할 때, 비용이 cost일 때, 확보할 수 있는 최대 메모리
        int[][] DP = new int[N][sumOfCost + 1];
        DP[0][cost[0]] = memory[0];
        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= sumOfCost; j++) {
                DP[i][j] = DP[i-1][j];
                if (j-cost[i] >= 0) {
                    DP[i][j] = Math.max(DP[i][j], DP[i-1][j-cost[i]] + memory[i]);
                }
            }
        }

        for (int minCost = 0; minCost <= sumOfCost; minCost++) {
            if (DP[N-1][minCost] >= M) {
                System.out.println(minCost);
                break;
            }
        }
    }

}

