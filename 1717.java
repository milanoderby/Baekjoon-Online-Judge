import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N, M;
    private static int[] groupOfNum;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());

            groupOfNum = new int[N+1];
            for (int i = 0; i <= N; i++) {
                groupOfNum[i] = i;
            }

            for (int i = 0; i < M; i++) {
                tokenizer = new StringTokenizer(br.readLine());
                int op = Integer.parseInt(tokenizer.nextToken());
                int a = Integer.parseInt(tokenizer.nextToken());
                int b = Integer.parseInt(tokenizer.nextToken());
                if (op == 0) {
                    unionGroup(a, b);
                } else {
                    if (findGroup(a) == findGroup(b)) {
                        System.out.println("YES");
                    } else {
                        System.out.println("NO");
                    }
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void unionGroup(int a, int b) {
        a = findGroup(a);
        b = findGroup(b);

        if (a < b) {
            groupOfNum[b] = a;
        } else {
            groupOfNum[a] = b;
        }
    }

    private static int findGroup(int num) {
        while (num != groupOfNum[num]) {
            num = groupOfNum[num];
        }
        return num;
    }
}
