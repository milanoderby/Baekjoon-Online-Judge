import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    private static int DIVISOR = 1000000007;
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
        int sizeOfSegmentTree = (int) Math.pow(2, heightOfTree + 1);

        long[] segmentTree = new long[sizeOfSegmentTree];
        int rootNode = 1;
        initSegmentTree(segmentTree, rootNode, numbers, 0, N - 1);

        for (int i = 0; i < M + K; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());

            switch (command) {
                case 1:
                    long newValue = c;
                    numbers[b - 1] = newValue;
                    updateSegmentTree(segmentTree, rootNode, newValue, b - 1, 0, N - 1);
                    break;
                case 2:
                    long result = getProduct(segmentTree, rootNode, b - 1, c - 1, 0, N - 1);
                    bw.write(result + "\n");
                    break;
            }
        }

        bw.flush();
    }

    private static void initSegmentTree(long[] segmentTree, int node, long[] numbers, int left, int right) {
        if (left == right) {
            segmentTree[node] = numbers[left];
            return;
        }

        int mid = (left + right) / 2;
        initSegmentTree(segmentTree, 2 * node, numbers, left, mid);
        initSegmentTree(segmentTree, 2 * node + 1, numbers, mid + 1, right);
        segmentTree[node] = (segmentTree[2 * node] * segmentTree[2 * node + 1]) % DIVISOR;
    }

    private static void updateSegmentTree(long[] segmentTree, int node, long newValue, int index, int left, int right) {
        if (index < left || right < index) {
            return;
        }

        if (left == right) {
            segmentTree[node] = newValue;
            return;
        }

        int mid = (left + right) / 2;
        updateSegmentTree(segmentTree, 2 * node, newValue, index, left, mid);
        updateSegmentTree(segmentTree, 2 * node + 1, newValue, index, mid + 1, right);

        segmentTree[node] = (segmentTree[2 * node] * segmentTree[2 * node + 1]) % DIVISOR;
    }

    private static long getProduct(long[] segmentTree, int node, int start, int end, int left, int right) {
        if (end < left || right < start) {
            return 1;
        }

        if (start <= left && right <= end) {
            return segmentTree[node];
        }

        int mid = (left + right) / 2;
        long productOfLeftChild = getProduct(segmentTree, 2 * node, start, end, left, mid);
        long productOfRightChild = getProduct(segmentTree, 2 * node + 1, start, end, mid + 1, right);
        return (productOfLeftChild * productOfRightChild) % DIVISOR;
    }
}