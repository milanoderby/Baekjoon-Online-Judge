import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static boolean[][] canCalculate;
    private static int numOfWeight, sumOfWeight, numOfMarble;
    private static int[] weight, marble;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        numOfWeight = Integer.parseInt(br.readLine());
        weight = new int[numOfWeight];

        sumOfWeight = 0;
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < numOfWeight; i++) {
            weight[i] = Integer.parseInt(tokenizer.nextToken());
            sumOfWeight += weight[i];
        }

        numOfMarble =Integer.parseInt(br.readLine());
        marble = new int[numOfMarble];

        tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < numOfMarble; i++) {
            marble[i] = Integer.parseInt(tokenizer.nextToken());
        }

        canCalculate = new boolean[numOfWeight][sumOfWeight + 1];
        canCalculate[0][weight[0]] = true;
        for (int i = 1; i < numOfWeight; i++) {
            canCalculate[i][weight[i]] = true;
            for (int j = 0; j <= sumOfWeight; j++) {
                if (canCalculate[i - 1][j]) {
                    canCalculate[i][j] = true;

                    int newSumWeight = j + weight[i];
                    canCalculate[i][newSumWeight] = true;

                    int newDiffWeight = Math.abs(j - weight[i]);
                    canCalculate[i][newDiffWeight] = true;
                }
            }
        }

        for (int i = 0; i < numOfMarble; i++) {
            if (marble[i] > sumOfWeight) {
                System.out.print("N ");
                continue;
            }

            if (canCalculate[numOfWeight - 1][marble[i]]) {
                System.out.print("Y ");
            } else {
                System.out.print("N ");
            }
        }
    }
}