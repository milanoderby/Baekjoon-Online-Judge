import java.io.*;
import java.util.StringTokenizer;

public class Main {
    private static int MAX_POWER_INPUT = 20;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int m = Integer.parseInt(br.readLine());

        int[] powerOfTwo = new int[MAX_POWER_INPUT];
        powerOfTwo[0] = 1;
        for (int i = 1; i < powerOfTwo.length; i++) {
            powerOfTwo[i] = 2 * powerOfTwo[i - 1];
        }

        int[][] function = new int[powerOfTwo.length][m + 1];

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 1; i <= m; i++) {
            function[0][i] = Integer.parseInt(tokenizer.nextToken());
        }

        for (int i = 1; i < powerOfTwo.length; i++) {
            for (int j = 1; j <= m; j++) {
                int temp = function[i - 1][j];
                function[i][j] = function[i - 1][temp];
            }
        }
        
        int Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(tokenizer.nextToken());
            int x = Integer.parseInt(tokenizer.nextToken());

            int decreasingN = n;
            int inputOfFunction = x;
            for (int j = powerOfTwo.length - 1; j >= 0; j--) {
                if (decreasingN >= powerOfTwo[j]) {
                    decreasingN -= powerOfTwo[j];

                    inputOfFunction = function[j][inputOfFunction];
                }
            }
            bw.write(inputOfFunction + "\n");
        }
        bw.flush();
    }
}