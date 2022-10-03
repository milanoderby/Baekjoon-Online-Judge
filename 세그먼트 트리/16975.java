import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] array = new int[N];

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            array[i] = Integer.parseInt(tokenizer.nextToken());
        }

        int heightOfSegmentTree = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
        int sizeOfSegmentTree = (int) Math.pow(2, heightOfSegmentTree);
        long[] segmentTree = new long[sizeOfSegmentTree];

        int rootNode = 1;

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(tokenizer.nextToken());
            switch (cmd) {
                case 1:
                    int start = Integer.parseInt(tokenizer.nextToken());
                    int end = Integer.parseInt(tokenizer.nextToken());
                    int k = Integer.parseInt(tokenizer.nextToken());

                    start--;
                    end--;
                    updateSegmentTree(segmentTree, rootNode, 0, N - 1, start, end, k);
                    break;
                case 2:
                    int index = Integer.parseInt(tokenizer.nextToken());

                    index--;
                    long sum = getSum(segmentTree, rootNode, 0, N - 1, index);
                    long answer = array[index] + sum;
                    bw.write(answer + "\n");
                    break;
            }
        }

        bw.flush();
    }

    private static void updateSegmentTree(long[] segmentTree, int node, int left, int right, int start, int end, int k) {
        if (right < start || end < left) {
            return;
        }

        if (start <= left && right <= end) {
            segmentTree[node] += k;
            return;
        }

        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;

        int mid = (left + right) / 2;
        updateSegmentTree(segmentTree, leftChildNode, left, mid, start, end, k);
        updateSegmentTree(segmentTree, rightChildNode, mid + 1, right, start, end, k);
    }

    private static long getSum(long[] segmentTree, int node, int left, int right, int index) {
        if (index < left || right < index) {
            return 0;
        }

        if (left == right) {
            return segmentTree[node];
        }

        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;

        int mid = (left + right) / 2;

        long sumOfLeftChild = getSum(segmentTree, leftChildNode, left, mid, index);
        long sumOfRightChild = getSum(segmentTree, rightChildNode, mid + 1, right, index);

        return segmentTree[node] + sumOfLeftChild + sumOfRightChild;
    }
}