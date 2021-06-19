import java.io.*;
import java.util.*;

public class Main {
    private static int[][] viaPath;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());

        final int MAX_DISTANCE = 100001;

        int[][] distance = new int[N + 1][N + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                if (i == j) {
                    continue;
                }
                distance[i][j] = MAX_DISTANCE;
            }
        }

        viaPath = new int[N + 1][N + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                // i와 j가 연결되어있지 않다면, viaPath[i][j]는 -1으로 설정합니다.
                viaPath[i][j] = -1;
            }
        }

        for (int i = 0; i < M; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());

            distance[a][b] = Math.min(distance[a][b], c);
            // a와 b가 직접적으로 연결되어있다면, viaPath[a][b]는 0으로 설정합니다.
            viaPath[a][b] = 0;
        }

        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (distance[i][j] > distance[i][k] + distance[k][j]) {
                        distance[i][j] = distance[i][k] + distance[k][j];
                        viaPath[i][j] = k;
                    }
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (distance[i][j] == MAX_DISTANCE) {
                    bw.append(0 + " ");
                } else {
                    bw.append(distance[i][j] + " ");
                }
            }
            bw.append("\n");
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (viaPath[i][j] == -1) {
                    bw.append(0 + "\n");
                    continue;
                }

                List<Integer> minCostPath = new ArrayList<>();
                minCostPath.add(i);
                getMinCostPath(i, j, minCostPath);
                minCostPath.add(j);

                bw.append(minCostPath.size() + " ");
                for (Integer path : minCostPath) {
                    bw.append(path + " ");
                }
                bw.append("\n");
            }
        }
        bw.flush();
    }

    private static void getMinCostPath(int src, int dest, List<Integer> minCostPath) throws IOException {
        if (viaPath[src][dest] == 0) {
            return;
        }

        int midVertex = viaPath[src][dest];

        getMinCostPath(src, midVertex, minCostPath);
        minCostPath.add(midVertex);
        getMinCostPath(midVertex, dest, minCostPath);
    }
}