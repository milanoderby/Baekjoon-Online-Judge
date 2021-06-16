import java.io.*;
import java.util.StringTokenizer;

public class Main {
    private static int MAX_DISTANCE = 1000000001;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int V = Integer.parseInt(tokenizer.nextToken());
        int E = Integer.parseInt(tokenizer.nextToken());

        int[][] distance = new int[V + 1][V + 1];
        initDistanceArray(distance, V);

        for (int i = 0; i < E; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());

            distance[a][b] = c;
        }

        floydWarshall(distance, V);
        int lengthOfMinimumCycle = getLengthOfMinimumCycle(distance, V);
        if (lengthOfMinimumCycle == MAX_DISTANCE) {
            bw.append(-1 + "");
        } else {
            bw.append(lengthOfMinimumCycle + "");
        }
        bw.flush();
    }

    private static void initDistanceArray(int[][] distance, int N) {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                distance[i][j] = MAX_DISTANCE;
            }
        }
    }

    private static void floydWarshall(int[][] distance, int N) {
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                }
            }
        }
    }

    private static int getLengthOfMinimumCycle(int[][] distance, int N) {
        int lengthOfMininumCycle = MAX_DISTANCE;
        for (int i = 1; i <= N; i++) {
            if (lengthOfMininumCycle > distance[i][i]) {
                lengthOfMininumCycle = distance[i][i];
            }
        }
        return lengthOfMininumCycle;
    }
}