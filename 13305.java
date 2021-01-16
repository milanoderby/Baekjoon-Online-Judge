import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int N = Integer.parseInt(br.readLine());
            int[] distance = new int[N];

            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            for (int i = 0; i < N - 1; i++) {
                distance[i] = Integer.parseInt(tokenizer.nextToken());
            }

            long answer = 0;

            tokenizer = new StringTokenizer(br.readLine());
            long minCostOfOil = Integer.parseInt(tokenizer.nextToken());
            long sumOfdistance = 0;
            for (int i = 0; i < N - 1; i++) {
                sumOfdistance += distance[i];
                int costOfOil = Integer.parseInt(tokenizer.nextToken());
                if (costOfOil < minCostOfOil) {
                    answer += minCostOfOil * sumOfdistance;
                    minCostOfOil = costOfOil;
                    sumOfdistance = 0;
                }
            }
            answer += minCostOfOil * sumOfdistance;
            System.out.println(answer);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}