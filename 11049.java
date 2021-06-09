import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        List<Matrix> matrixList = new ArrayList<>();
        // get 할 때, 1~N으로 맞춰주기 위해 의미 없는 값을 추가
        matrixList.add(new Matrix(-1, -1));
        for (int i = 1; i <= N; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());
            matrixList.add(new Matrix(r, c));
        }

        int[][] numOfOperation = new int[N+1][N+1];
        for (int i = N; i >= 1; i--) {
            for (int j = i; j <= N; j++) {
                if (i == j) {
                    numOfOperation[i][j] = 0;
                    continue;
                }

                int minOperation = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    minOperation = Math.min(
                            minOperation,
                            numOfOperation[i][k] +
                            numOfOperation[k+1][j] +
                            matrixList.get(i).row * matrixList.get(k).column * matrixList.get(j).column
                    );
                }
                numOfOperation[i][j] = minOperation;
            }
        }

        System.out.println(numOfOperation[1][N]);
    }

    private static class Matrix {
        int row;
        int column;

        public Matrix (int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}

