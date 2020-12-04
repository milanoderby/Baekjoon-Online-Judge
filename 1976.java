import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    private static int N, M;
    private static int[][] cityMap;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());
            M = Integer.parseInt(br.readLine());

            StringTokenizer tokenizer;
            cityMap = new int[N+1][N+1];
            for (int i = 1; i <= N; i++) {
                tokenizer = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; j++) {
                    cityMap[i][j] = Integer.parseInt(tokenizer.nextToken());
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
