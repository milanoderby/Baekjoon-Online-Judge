import java.io.*;
import java.util.*;

public class Main {
    private static int[] myGroup;
    private static boolean findCycle = false;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int M = Integer.parseInt(tokenizer.nextToken());

        myGroup = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            myGroup[i] = i;
        }

        int answer = Integer.MAX_VALUE;
        for (int turn = 1; turn <= M; turn++) {
            tokenizer = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());

            union(a, b);
            if (findCycle) {
                answer = turn;
                break;
            }
        }
        if (answer == Integer.MAX_VALUE) {
            bw.append(0 + "");
        } else {
            bw.append(answer + "");
        }
        bw.flush();
    }

    private static void union(int a, int b) {
        a = findGroup(a);
        b = findGroup(b);

        if (a != b) {
            int min = Math.min(a, b);
            int max = Math.max(a, b);
            myGroup[min] = max;
        } else {
            findCycle = true;
        }
    }

    private static int findGroup(int num) {
        if (myGroup[num] == num) {
            return num;
        }
        return myGroup[num] = findGroup(myGroup[num]);
    }
}