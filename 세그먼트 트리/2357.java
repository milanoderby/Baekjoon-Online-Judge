import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.function.ToIntBiFunction;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int M = Integer.parseInt(tokenizer.nextToken());

        int[] numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(br.readLine());
        }

        int heightOfSegmentTree = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
        int sizeOfSegmentTree = (int) Math.pow(2, heightOfSegmentTree);
        int[] minimumSegmentTree = new int[sizeOfSegmentTree];
        int[] maximumSegmentTree = new int[sizeOfSegmentTree];

        int rootNode = 1;
        initSegmentTree(minimumSegmentTree, rootNode, numbers, 0, N - 1, Math::min);
        initSegmentTree(maximumSegmentTree, rootNode, numbers, 0, N - 1, Math::max);

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());

            int minimumValue = getResult(minimumSegmentTree, rootNode, 0, N - 1, a - 1, b - 1, Math::min, Integer.MAX_VALUE);
            int maximumValue = getResult(maximumSegmentTree, rootNode, 0, N - 1, a - 1, b - 1, Math::max, Integer.MIN_VALUE);
            bw.write(minimumValue + " " + maximumValue + "\n");
        }
        bw.flush();
    }

    private static void initSegmentTree(int[] segmentTree, int node, int[] numbers, int left, int right, ToIntBiFunction<Integer, Integer> compareFunction) {
        if (left == right) {
            segmentTree[node] = numbers[left];
            return;
        }

        int mid = (left + right) / 2;
        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;
        initSegmentTree(segmentTree, leftChildNode, numbers, left, mid, compareFunction);
        initSegmentTree(segmentTree, rightChildNode, numbers, mid + 1, right, compareFunction);

        segmentTree[node] = compareFunction.applyAsInt(segmentTree[leftChildNode], segmentTree[rightChildNode]);
    }

    private static int getResult(int[] segmentTree, int node, int left, int right, int start, int end, ToIntBiFunction<Integer, Integer> compareFunciton, int defaultValue) {
        if (start <= left && right <= end) {
            return segmentTree[node];
        }

        if (end < left || right < start) {
            return defaultValue;
        }

        int mid = (left + right) / 2;
        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;

        int resultOfLeftNode = getResult(segmentTree, leftChildNode, left, mid, start, end, compareFunciton, defaultValue);
        int resultOfRightNode = getResult(segmentTree, rightChildNode, mid + 1, right, start, end, compareFunciton, defaultValue);

        int result = compareFunciton.applyAsInt(resultOfLeftNode, resultOfRightNode);
        return result;
    }
}