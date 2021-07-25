import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());

        int[] clock1 = new int[n];
        int[] clock2 = new int[n];

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            clock1[i] = Integer.parseInt(tokenizer.nextToken());
        }

        tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            clock2[i] = Integer.parseInt(tokenizer.nextToken());
        }

        Arrays.sort(clock1);
        int[] angleBetweenClock1 = new int[2 * n];
        for (int i = 1; i < n; i++) {
            angleBetweenClock1[i - 1] = clock1[i] - clock1[i - 1];
        }
        angleBetweenClock1[n - 1] = clock1[0] + 360000 - clock1[n - 1];
        for (int i = n; i < 2 * n; i++) {
            angleBetweenClock1[i] = angleBetweenClock1[i - n];
        }

        Arrays.sort(clock2);
        int[] angleBetweenClock2 = new int[n];
        for (int i = 1; i < n; i++) {
            angleBetweenClock2[i - 1] = clock2[i] - clock2[i - 1];
        }
        angleBetweenClock2[n - 1] = clock2[0] + 360000 - clock2[n - 1];
        int[] pi = getPiArray(angleBetweenClock2);

        if (findPattern(angleBetweenClock1, angleBetweenClock2, pi)) {
            bw.append("possible");
        } else {
            bw.append("impossible");
        }
        bw.flush();
    }

    private static int[] getPiArray(int[] arr) {
        int[] pi = new int[arr.length];
        for (int i = 1, j = 0; i + j < arr.length; ) {
            if (arr[i + j] == arr[j]) {
                pi[i + j] = j + 1;
                j++;
            } else {
                if (j > 0) {
                    i += j - pi[j - 1];
                    j = pi[j - 1];
                } else {
                    i++;
                }
            }
        }
        return pi;
    }

    private static boolean findPattern(int[] angle1, int[] angle2, int[] pi) {
        for (int i = 0, j = 0; i + angle2.length <= angle1.length ; ) {
            if (angle1[i + j] == angle2[j]) {
                j++;
                if (j == angle2.length) {
                    return true;
                }
            } else {
                if (j > 0) {
                    i += j - pi[j - 1];
                    j = pi[j - 1];
                } else {
                    i++;
                }
            }
        }
        return false;
    }
}