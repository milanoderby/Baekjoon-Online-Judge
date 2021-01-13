import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int[][][] answer;
    private static boolean[][][] findAnswer;
    private static int SIZE = 21;

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            answer = new int[SIZE][SIZE][SIZE];
            findAnswer = new boolean[SIZE][SIZE][SIZE];

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    for (int k = 0; k < SIZE; k++) {
                        answer[i][j][k] = w(i, j, k);
                        findAnswer[i][j][k] = true;
                    }
                }
            }

            while (true) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(tokenizer.nextToken());
                int b = Integer.parseInt(tokenizer.nextToken());
                int c = Integer.parseInt(tokenizer.nextToken());

                if (a == -1 && b == -1 && c == -1){
                    break;
                }
                System.out.println("w(" + a + ", " + b + ", " + c + ") = " + w(a, b, c));
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static int w(int a, int b, int c) {
        if (0 <= a && 0 <= b && 0 <= c && a < SIZE && b < SIZE && c < SIZE) {
            if (findAnswer[a][b][c]) {
                return answer[a][b][c];
            }
        }

        if (a <= 0 || b <= 0 || c <= 0) {
            return 1;
        }

        if (a > 20 || b > 20 || c > 20) {
            return w(20, 20, 20);
        }

        if (a < b && b < c) {
            return w(a, b, c - 1) + w(a, b - 1, c - 1) - w(a, b - 1, c);
        }

        return w(a - 1, b, c) + w(a - 1, b - 1, c) + w(a - 1, b, c - 1) - w(a - 1, b - 1, c - 1);
    }
}