import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    private static int SIZE_OF_ARRAY = 2000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int heightOfSegmentTree = (int) Math.ceil(Math.log(SIZE_OF_ARRAY) / Math.log(2)) + 1;
        int sizeOfSegmentTree = (int) Math.pow(2, heightOfSegmentTree);
        int[] segmentTree = new int[sizeOfSegmentTree];

        int rootNode = 1;
        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(tokenizer.nextToken());
            int X = Integer.parseInt(tokenizer.nextToken());

            switch (command) {
                case 1:
                    // X 추가
                    X--;
                    addX(segmentTree, rootNode, 0, SIZE_OF_ARRAY - 1, X);
                    break;
                case 2:
                    // X 번째로 작은 수 제거
                    int xthNumber = removeXthNumber(segmentTree, rootNode, 0, SIZE_OF_ARRAY - 1, X);
                    bw.write(xthNumber + "\n");
                    break;
            }
        }

        bw.flush();
    }

    private static void addX(int[] segmentTree, int node, int left, int right, int index) {
        if (left > index || index > right) {
            return;
        }

        if (left == right) {
            segmentTree[node]++;
            return;
        }

        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;

        int mid = (left + right) / 2;

        addX(segmentTree, leftChildNode, left, mid, index);
        addX(segmentTree, rightChildNode, mid + 1, right, index);

        segmentTree[node] = segmentTree[leftChildNode] + segmentTree[rightChildNode];
    }

    private static int removeXthNumber(int[] segmentTree, int node, int left, int right, int X) {
        if (left == right) {
            segmentTree[node]--;
            return left + 1;
        }

        int leftChildNode = 2 * node;
        int rightChildNode = 2 * node + 1;

        int mid = (left + right) / 2;

        segmentTree[node]--;
        // 왼쪽에는 X번째가 없음, 오른쪽으로 방문
        if (segmentTree[leftChildNode] < X) {
            // 왼쪽 자식노드의 개수를 제외
            return removeXthNumber(segmentTree, rightChildNode, mid + 1, right, X - segmentTree[leftChildNode]);
        }
        return removeXthNumber(segmentTree, leftChildNode, left, mid, X);
    }
}