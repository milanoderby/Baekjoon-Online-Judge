import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());
        }
        Arrays.sort(arr);

        int i = 0;
        int j = N - 1;

        int minSolution = 0, maxSolution = Integer.MAX_VALUE;
        while (i < j) {
            int sum = arr[i] + arr[j];
            if (Math.abs(minSolution + maxSolution) > Math.abs(sum)) {
                minSolution = arr[i];
                maxSolution = arr[j];
            }
            if (sum <= 0) {
                i++;
            } else {
                j--;
            }
        }
        bw.append(minSolution + " " + maxSolution);
        bw.flush();
    }
}