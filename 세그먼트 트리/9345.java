import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.function.ToIntBiFunction;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(tokenizer.nextToken());
            int K = Integer.parseInt(tokenizer.nextToken());

            int[] dvd = new int[N];
            for (int j = 0; j < N; j++) {
                dvd[j] = j;
            }

            int heightOfSegmentTree = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
            int sizeOfSegmentTree = (int) Math.pow(2, heightOfSegmentTree);

            int[] minSegmentTree = new int[sizeOfSegmentTree];
            int[] maxSegmentTree = new int[sizeOfSegmentTree];

            int rootNode = 1;
            initSegmentTree(minSegmentTree, rootNode, dvd, 0, N - 1, Integer::min);
            initSegmentTree(maxSegmentTree, rootNode, dvd, 0, N - 1, Integer::max);

            for (int j = 0; j < K; j++) {
                tokenizer = new StringTokenizer(br.readLine());
                int command = Integer.parseInt(tokenizer.nextToken());
                int A = Integer.parseInt(tokenizer.nextToken());
                int B = Integer.parseInt(tokenizer.nextToken());

                switch (command) {
                    case 0:
                        int oldDvdOfA = dvd[A];
                        int oldDvdOfB = dvd[B];

                        dvd[A] = oldDvdOfB;
                        dvd[B] = oldDvdOfA;

                        updateSegmentTree(minSegmentTree, rootNode, 0, N - 1, A, oldDvdOfB, Integer::min);
                        updateSegmentTree(minSegmentTree, rootNode, 0, N - 1, B, oldDvdOfA, Integer::min);

                        updateSegmentTree(maxSegmentTree, rootNode, 0, N - 1, A, oldDvdOfB, Integer::max);
                        updateSegmentTree(maxSegmentTree, rootNode, 0, N - 1, B, oldDvdOfA, Integer::max);
                        break;
                    case 1:
                        int minimum = getResult(minSegmentTree, rootNode, 0, N - 1, A, B, Integer::min, Integer.MAX_VALUE);
                        int maximum = getResult(maxSegmentTree, rootNode, 0, N - 1, A, B, Integer::max, Integer.MIN_VALUE);

                        if (minimum == A && maximum == B) {
                            bw.write("YES\n");
                        } else {
                            bw.write("NO\n");
                        }
                        break;
                }
            }
        }
        bw.flush();
    }

    private static void initSegmentTree(int[] segmentTree, int node, int[] array, int left, int right, ToIntBiFunction<Integer, Integer> compareFunction) {
        if (left == right) {
            segmentTree[node] = array[left];
            return;
        }

        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;

        int mid = (left + right) / 2;
        initSegmentTree(segmentTree, leftChildNode, array, left, mid, compareFunction);
        initSegmentTree(segmentTree, rightChildNode, array, mid + 1, right, compareFunction);

        segmentTree[node] = compareFunction.applyAsInt(segmentTree[leftChildNode], segmentTree[rightChildNode]);
    }

    private static int getResult(int[] segmentTree, int node, int left, int right, int start, int end, ToIntBiFunction<Integer, Integer> compareFunction, int defaultValue) {
        if (end < left || right < start) {
            return defaultValue;
        }

        if (start <= left && right <= end) {
            return segmentTree[node];
        }

        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;

        int mid = (left + right) / 2;
        int resultOfLeftChild = getResult(segmentTree, leftChildNode, left, mid, start, end, compareFunction, defaultValue);
        int resultOfRightChild = getResult(segmentTree, rightChildNode, mid + 1, right, start, end, compareFunction, defaultValue);

        return compareFunction.applyAsInt(resultOfLeftChild, resultOfRightChild);
    }

    private static void updateSegmentTree(int[] segmentTree, int node, int left, int right, int index, int newValue, ToIntBiFunction<Integer, Integer> compareFunction) {
        if (left == right && left == index) {
            segmentTree[node] = newValue;
            return;
        }

        if (index < left || right < index) {
            return;
        }

        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;

        int mid = (left + right) / 2;

        updateSegmentTree(segmentTree, leftChildNode, left, mid, index, newValue, compareFunction);
        updateSegmentTree(segmentTree, rightChildNode, mid + 1, right, index, newValue, compareFunction);

        segmentTree[node] = compareFunction.applyAsInt(segmentTree[leftChildNode], segmentTree[rightChildNode]);
    }
}