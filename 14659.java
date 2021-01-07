import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());

            int maxHeight = 0;
            int maxKillEnemy = 0;
            int answer = 0;
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                int height = Integer.parseInt(tokenizer.nextToken());
                if (maxHeight >= height) {
                    maxKillEnemy++;
                    answer = Math.max(maxKillEnemy, answer);
                } else {
                    maxHeight = height;
                    maxKillEnemy = 0;
                }
            }
            System.out.println(answer);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}