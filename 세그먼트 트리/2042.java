import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int M = Integer.parseInt(tokenizer.nextToken());
        int K = Integer.parseInt(tokenizer.nextToken());

        long[] numbers = new long[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = Long.parseLong(br.readLine());
        }

        int heightOfTree = (int) Math.ceil(Math.log(N) / Math.log(2));
        int sizeOfTree = (int) Math.pow(2, heightOfTree + 1);
        long[] segmentTree = new long[sizeOfTree];
        int rootNode = 1;
        initSegmentTree(numbers, segmentTree, rootNode, 0, N - 1);

        for (int i = 0; i < M + K; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            long c = Long.parseLong(tokenizer.nextToken());

            switch (a) {
                case 1:
                    long oldValue = numbers[b - 1];
                    long newValue = c;
                    long diff = newValue - oldValue;
                    numbers[b - 1] = c;

                    updateSegmentTree(segmentTree, rootNode, 0, N - 1, b - 1, diff);
                    break;
                case 2:
                    long result = getSum(segmentTree, rootNode, b - 1, c - 1, 0, N - 1);
                    bw.write(result + "\n");
                    break;
            }
        }

        bw.flush();
    }

    private static long initSegmentTree(long[] numbers, long[] segmentTree, int node, int start, int end) {
        if (start == end) {
            return segmentTree[node] = numbers[start];
        }

        int mid = (start + end) / 2;
        long leftChild = initSegmentTree(numbers, segmentTree, 2 * node, start, mid);
        long rightChild = initSegmentTree(numbers, segmentTree, 2 * node + 1, mid + 1, end);

        return segmentTree[node] = leftChild + rightChild;
    }

    private static long getSum(long[] segmentTree, int node, int left, long right, int start, int end) {
        if (end < left || right < start) {
            return 0;
        }

        if (left <= start && end <= right) {
            return segmentTree[node];
        }

        int mid = (start + end) / 2;
        long sumOfLeftChild = getSum(segmentTree, 2 * node, left, right, start, mid);
        long sumOfRightChild = getSum(segmentTree, 2 * node + 1, left, right, mid + 1, end);
        return sumOfLeftChild + sumOfRightChild;
    }

    private static void updateSegmentTree(long[] segmentTree, int node, int start, int end, int index, long diff) {
        if (index < start || end < index) {
            return;
        }

        segmentTree[node] += diff;
        if (start == end) {
            return;
        }

        int mid = (start + end) / 2;
        updateSegmentTree(segmentTree, 2 * node, start, mid, index, diff);
        updateSegmentTree(segmentTree, 2 * node + 1, mid + 1, end, index, diff);
    }
}