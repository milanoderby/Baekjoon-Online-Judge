import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    private static Set<String> S;
    private static int N, M;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());

            S = new HashSet<>();
            for (int i = 0; i < N; i++) {
                String str = br.readLine();
                for (int lastIndex = 1; lastIndex <=str.length(); lastIndex++) {
                    S.add(str.substring(0, lastIndex));
                }
            }

            int answer = 0;
            for (int i = 0; i < M; i++) {
                if (S.contains(br.readLine())) {
                    answer++;
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
