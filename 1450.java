import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int C = Integer.parseInt(tokenizer.nextToken());

        int[] weightOfStuff = new int[N];

        tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            weightOfStuff[i] = Integer.parseInt(tokenizer.nextToken());
        }

        int halfOfN = N / 2;
        List<Long> partSum1 = new ArrayList<>();
        List<Long> partSum2 = new ArrayList<>();

        getPartSum(partSum1, Arrays.copyOfRange(weightOfStuff, 0, halfOfN));
        getPartSum(partSum2, Arrays.copyOfRange(weightOfStuff, halfOfN, N));

        Collections.sort(partSum1);
        Collections.sort(partSum2);

        int answer = 0;
        for (Long sum : partSum1) {
            answer += upperBound(partSum2, C - sum) + 1;
        }
        bw.append(answer + "");
        bw.flush();
    }

    private static void getPartSum(List<Long> partSum, int[] num) {
        for (int i = 0; i < (1 << num.length); i++) {
            long sum = 0;
            for (int j = 0; j < num.length; j++) {
                if ((i & (1 << j)) > 0) {
                    sum += num[j];
                }
            }
            partSum.add(sum);
        }
    }

    private static int upperBound(List<Long> arrayList, long key) {
        int start = 0;
        int end = arrayList.size() - 1;

        if (arrayList.get(start) > key) {
            return -1;
        }

        if (arrayList.get(end) <= key) {
            return end;
        }

        while (start + 1 < end) {
            int mid = (start + end) / 2;

            if (arrayList.get(mid) <= key) {
                start = mid;
            } else {
                end = mid;
            }
        }
        return start;
    }
}