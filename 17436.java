import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    private static long M, answer;
    private static int[] primeNumber;
    private static boolean[] isSelected;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            M = Long.parseLong(tokenizer.nextToken());

            primeNumber = new int[N];
            isSelected = new boolean[N];

            tokenizer = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                primeNumber[i] = Integer.parseInt(tokenizer.nextToken());
            }

            getCombination(0);
            System.out.println(answer);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void getCombination(int index) {
        if (index == N) {
            int count = 0;
            long resultOfMultiply = 1;
            for (int i = 0; i < N; i++) {
                if (isSelected[i]) {
                    count++;
                    resultOfMultiply *= primeNumber[i];
                }
            }

            if (count > 0) {
                int multiplier = count % 2 == 1 ? 1 : -1;
                answer += multiplier * (M / resultOfMultiply);
            }
            return;
        }

        isSelected[index] = true;
        getCombination(index + 1);
        isSelected[index] = false;
        getCombination(index + 1);
    }
}