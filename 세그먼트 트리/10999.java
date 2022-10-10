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

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int M = Integer.parseInt(tokenizer.nextToken());
        int K = Integer.parseInt(tokenizer.nextToken());

        long[] numbers = new long[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = Long.parseLong(br.readLine());
        }

        int heightOfSegmentTree = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
        int sizeOfSegmentTree = (int) Math.pow(2, heightOfSegmentTree);
        long[] segmentTree = new long[sizeOfSegmentTree];
        int rootNode = 1;

        initSegmentTree(segmentTree, rootNode, numbers, 0, N - 1);

        long[] lazy = new long[sizeOfSegmentTree];

        for (int i = 0; i < M + K; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(tokenizer.nextToken());
            int start, end;
            switch (command) {
                case 1:
                    start = Integer.parseInt(tokenizer.nextToken()) - 1;
                    end = Integer.parseInt(tokenizer.nextToken()) - 1;
                    long numberToAdd = Long.parseLong(tokenizer.nextToken());

                    updateSegmentTree(segmentTree, rootNode, lazy, 0, N - 1, start, end, numberToAdd);
                    break;
                case 2:
                    start = Integer.parseInt(tokenizer.nextToken()) - 1;
                    end = Integer.parseInt(tokenizer.nextToken()) - 1;

                    long answer = getSum(segmentTree, rootNode, lazy, 0, N - 1, start, end);
                    bw.write(answer + "\n");
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

        int leftNode = 2 * node;
        int rightNode = 2 * node + 1;
        int mid = (left + right) / 2;

        initSegmentTree(segmentTree, leftNode, numbers, left, mid);
        initSegmentTree(segmentTree, rightNode, numbers, mid + 1, right);

        segmentTree[node] = segmentTree[leftNode] + segmentTree[rightNode];
    }

    private static void updateSegmentTree(long[] segmentTree, int node, long[] lazy, int left, int right, int start, int end, long numberToAdd) {
        int range = right - left + 1;
        boolean isLeafNode = left == right;
        updateLazy(segmentTree, node, lazy, range, isLeafNode);

        // 범위에 해당하지 않으므로 값을 더하지 않고, 종료
        if (right < start || end < left) {
            return;
        }

        int leftNode = 2 * node;
        int rightNode = 2 * node + 1;
        // 범위에 해당하므로 값을 더하고, 종료
        if (start <= left && right <= end) {
            segmentTree[node] += range * numberToAdd;

            // 리프노드가 아닐 경우, 현재 더해야할 값을 자식 노드로 전파
            if (!isLeafNode) {
                lazy[leftNode] += numberToAdd;
                lazy[rightNode] += numberToAdd;
            }
            return;
        }

        int mid = (left + right) / 2;
        updateSegmentTree(segmentTree, leftNode, lazy, left, mid, start, end, numberToAdd);
        updateSegmentTree(segmentTree, rightNode, lazy, mid + 1, right, start, end, numberToAdd);

        segmentTree[node] = segmentTree[leftNode] + segmentTree[rightNode];
    }

    private static long getSum(long[] segmentTree, int node, long[] lazy, int left, int right, int start, int end) {
        int range = right - left + 1;
        boolean isLeafNode = left == right;
        updateLazy(segmentTree, node, lazy, range, isLeafNode);

        // 범위에 해당하지 않으면, 0을 반환
        if (end < left || right < start) {
            return 0;
        }

        // 범위에 해당하면, 더한 값을 반환
        if (start <= left && right <= end) {
            return segmentTree[node];
        }

        int leftNode = 2 * node;
        int rightNode = 2 * node + 1;
        int mid = (left + right) / 2;
        long sumOfLeftNode = getSum(segmentTree, leftNode, lazy, left, mid, start, end);
        long sumOfRightNode = getSum(segmentTree, rightNode, lazy, mid + 1, right, start, end);

        return sumOfLeftNode + sumOfRightNode;
    }

    private static void updateLazy(long[] segmentTree, int node, long[] lazy, int range, boolean isLeafNode) {
        // lazy가 있다면, 현재 노드의 합 값은 더해주고, 자식노드로 전파
        long propagation = lazy[node];
        segmentTree[node] += propagation * range;
        lazy[node] = 0;

        int leftNode = 2 * node;
        int rightNode = 2 * node + 1;

        // 리프노드가 아닐 경우, 자식노드가 있으므로 lazy를 전파
        if (!isLeafNode) {
            lazy[leftNode] += propagation;
            lazy[rightNode] += propagation;
        }
    }
}