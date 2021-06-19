import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());
        }
        Arrays.sort(arr);
        int x = Integer.parseInt(br.readLine());

        int i = 0;
        int j = n - 1;

        int answer = 0;
        while (i < j) {
            int sum = arr[i] + arr[j];
            if (sum == x) {
                answer++;
                i++;
            } else if (sum > x) {
                j--;
            } else {
                i++;
            }
        }
        bw.append(answer + "");
        bw.flush();
    }
}