import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N, M;
    private static int answer;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int T = Integer.parseInt(br.readLine());

            for (int i = 0; i < T; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                N = Integer.parseInt(tokenizer.nextToken());
                M = Integer.parseInt(tokenizer.nextToken());
                answer = 0;
                for (int j = 1; j <= M; j++) {
                    getAnswer(1, j);
                }
                System.out.println(answer);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void getAnswer(int num, int selectedNum) {
        if (num == N) {
            answer++;
        }

        for (int i = selectedNum + 1; i <= M; i++) {
            getAnswer(num + 1, i);
        }
    }
}