import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            int[] rankOfLastYear = new int[n + 1];
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int team = Integer.parseInt(tokenizer.nextToken());
                rankOfLastYear[team] = j + 1;
            }

            boolean[][] isHigher = new boolean[n + 1][n + 1];
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    if (rankOfLastYear[j] < rankOfLastYear[k]) {
                        isHigher[j][k] = true;
                    }
                }
            }

            int m = Integer.parseInt(br.readLine());
            for (int j = 0; j < m; j++) {
                tokenizer = new StringTokenizer(br.readLine());
                int team1 = Integer.parseInt(tokenizer.nextToken());
                int team2 = Integer.parseInt(tokenizer.nextToken());

                isHigher[team1][team2] = !isHigher[team1][team2];
                isHigher[team2][team1] = !isHigher[team2][team1];
            }

            List<Integer>[] directedGraph = new List[n + 1];
            for (int j = 1; j <= n; j++) {
                directedGraph[j] = new ArrayList<>();
            }
            int[] countOfHigherThan = new int[n + 1];
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    if (isHigher[j][k]) {
                        directedGraph[j].add(k);
                        countOfHigherThan[k]++;
                    }
                }
            }

            int highestRankTeam = 0;
            for (int j = 1; j <= n; j++) {
                if (countOfHigherThan[j] == 0) {
                    highestRankTeam = j;
                    break;
                }
            }

            if (highestRankTeam == 0) {
                bw.append("IMPOSSIBLE\n");
                continue;
            }

            Queue<Integer> teamQueue = new ArrayDeque<>();
            teamQueue.offer(highestRankTeam);

            StringBuilder rankOrder = new StringBuilder();
            while (!teamQueue.isEmpty()) {
                int cur = teamQueue.poll();
                rankOrder.append(cur + " ");
                for (Integer next : directedGraph[cur]) {
                    countOfHigherThan[next]--;
                    if (countOfHigherThan[next] == 0) {
                        teamQueue.offer(next);
                    }
                }
            }

            boolean findAllRank = true;
            for (int j = 1; j <= n; j++) {
                if (countOfHigherThan[j] > 0) {
                    findAllRank = false;
                    break;
                }
            }

            if (findAllRank) {
                bw.append(rankOrder + "\n");
            } else {
                bw.append("IMPOSSIBLE\n");
            }
        }
        bw.flush();
    }
}