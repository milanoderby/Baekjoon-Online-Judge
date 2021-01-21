import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
    private static int SIZE_OF_MATRIX = 2;
    private static long OPERAND = 1000000007;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            long n = Long.parseLong(br.readLine());
            System.out.println(getAnswer(n));
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private static long getAnswer(long n) {
        long[][] factorMatrix = {{1, 1}, {1, 2}};
        long[][] answerMatrix = {{1, 0}, {0, 1}};
        if (n <= 1) {
            return n;
        }
        long k = n / 2;
        int position = (int) (n % 2);
        while (k != 0) {
            if (k % 2 == 1) {
                answerMatrix = multiplyMatrix(factorMatrix, answerMatrix);
            }
            factorMatrix = multiplyMatrix(factorMatrix, factorMatrix);
            k /= 2;
        }
        return answerMatrix[position][1];
    }
    private static long[][] multiplyMatrix(long[][] matrix1, long[][] matrix2) {
        long[][] newMatrix = new long[SIZE_OF_MATRIX][SIZE_OF_MATRIX];
        for (int i = 0; i < SIZE_OF_MATRIX; i++) {
            for (int j = 0; j < SIZE_OF_MATRIX; j++) {
                long sum = 0;
                for (int k = 0; k < SIZE_OF_MATRIX; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                newMatrix[i][j] = sum % OPERAND;
            }
        }
        return newMatrix;
    }
}