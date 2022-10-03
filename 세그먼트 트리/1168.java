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
        int K = Integer.parseInt(tokenizer.nextToken());

        int heightOfSegmentTree = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
        int sizeOfSegmentTree = (int) Math.pow(2, heightOfSegmentTree);
        int[] segmentTree = new int[sizeOfSegmentTree];

        int rootNode = 1;
        for (int i = 0; i < N; i++) {
            addPerson(segmentTree, rootNode, 0, N - 1, i);
        }

        int remainPerson = N;
        int originalK = K;
        bw.write("<");
        for (int i = 0; i < N; i++) {
            int removedPersonNumber = removeKthPerson(segmentTree, rootNode, 0, N - 1, K);
            bw.write(removedPersonNumber + "");

            remainPerson--;
            if (remainPerson == 0) {
                break;
            }

            K = ((K - 1) + originalK) % remainPerson;
            if (K == 0) {
                K = remainPerson;
            }
            bw.write(", ");
        }
        bw.write(">");
        bw.flush();
    }

    // 1 2 3 4 5 6 7
    // 3 % 7 = 3

    // 1 2 4 5 6 7
    // ((3 - 1) + 3) % (7 - 1) = 5

    // 1 2 4 5 7
    // ((5 - 1) + 3) % (6 - 1) = 2

    // 1 4 5 7
    // ((2 - 1) + 3) % (5 - 1) = 4

    private static void addPerson(int[] segmentTree, int node, int left, int right, int personNumber) {
        if (left > personNumber || personNumber > right) {
            return;
        }

        if (left == right) {
            segmentTree[node]++;
            return;
        }

        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;

        int mid = (left + right) / 2;

        addPerson(segmentTree, leftChildNode, left, mid, personNumber);
        addPerson(segmentTree, rightChildNode, mid + 1, right, personNumber);

        segmentTree[node] = segmentTree[leftChildNode] + segmentTree[rightChildNode];
    }

    private static int removeKthPerson(int[] segmentTree, int node, int left, int right, int K) {
        if (left == right) {
            segmentTree[node]--;
            return left + 1;
        }

        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;

        int mid = (left + right) / 2;

        segmentTree[node]--;
        if (segmentTree[leftChildNode] < K) {
            return removeKthPerson(segmentTree, rightChildNode, mid + 1, right, K - segmentTree[leftChildNode]);
        }
        return removeKthPerson(segmentTree, leftChildNode, left, mid, K);
    }
}