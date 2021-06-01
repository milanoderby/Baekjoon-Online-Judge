import java.io.*;
import java.util.*;

public class Main {
    private static int N;
    private static Node[] inputNode;
    private static Vector<Node> lisVector;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        inputNode = new Node[N];
        lisVector = new Vector<>();
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int inputNum = Integer.parseInt(tokenizer.nextToken());
            inputNode[i] = new Node(inputNum);
            if (i == 0) {
                lisVector.add(inputNode[i]);
            } else {
                pushNode(inputNode[i]);
            }
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.append(lisVector.size() + "\n");

        Stack<Node> answerNode = new Stack<>();
        Node tempNode = lisVector.lastElement();
        while(tempNode != null) {
            answerNode.push(tempNode);
            tempNode = tempNode.prevNode;
        }

        while(!answerNode.isEmpty()){
            bw.append(answerNode.pop().num + " ");
        }
        bw.flush();
    }

    private static void pushNode(Node node) {
        if (lisVector.firstElement().num >= node.num) {
            lisVector.set(0, node);
            return;
        }

        if (lisVector.lastElement().num < node.num) {
            node.setPrevNode(lisVector.lastElement());
            lisVector.add(node);
            return;
        }

        int indexToReplace = getLowerBound(node.num);
        node.setPrevNode(lisVector.get(indexToReplace - 1));
        lisVector.set(indexToReplace, node);
    }

    // num 이상인 수 중에 가장 작은 수의 index(위치) 값
    private static int getLowerBound(int num) {
        int start = 0;
        int end = lisVector.size() - 1;
        while (start + 1 < end) {
            int mid = (start + end) / 2;

            if (lisVector.get(mid).num >= num) {
                end = mid;
            } else {
                start = mid;
            }
        }
        return end;
    }

    private static class Node {
        Node prevNode;
        int num;

        public Node (int num) {
            this.num = num;
        }

        public void setPrevNode(Node prevNode) {
            this.prevNode = prevNode;
        }
    }
}
