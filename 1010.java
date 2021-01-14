import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        try {
            int SIZE = 30;
            int[][] answer = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                answer[1][i] = i;
            }

            for (int i = 2; i < SIZE; i++) {
                for (int j = i; j < SIZE; j++) {
                    for (int k = i - 1; k <= j - 1; k++) {
                        answer[i][j] += answer[i - 1][k];
                    }
                }
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int T = Integer.parseInt(br.readLine());

            for (int i = 0; i < T; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                int N = Integer.parseInt(tokenizer.nextToken());
                int M = Integer.parseInt(tokenizer.nextToken());
                System.out.println(answer[N][M]);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}