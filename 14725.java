import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {
        Node root = new Node("");
        root.childNodes = new HashMap<>();

        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(tokenizer.nextToken());
            Node temp = root;
            for (int j = 0; j < K; j++) {
                String food = tokenizer.nextToken();
                if (temp.childNodes.containsKey(food)) {
                    temp = temp.childNodes.get(food);
                } else {
                    Node newNode = new Node(food);
                    temp.childNodes.put(food, newNode);
                    temp = newNode;
                }
            }
        }

        List<String> foodList = root.childNodes.keySet().stream().sorted().collect(Collectors.toList());
        for (String food : foodList) {
            printNode(0, root.childNodes.get(food));
        }
        bw.flush();
    }

    private static void printNode(int depth, Node cur) throws IOException {
        for (int i = 0; i < depth; i++) {
            bw.append("--");
        }
        bw.append(cur.food + "\n");

        List<String> foodList = cur.childNodes.keySet().stream().sorted().collect(Collectors.toList());
        for (String food : foodList) {
            printNode(depth + 1, cur.childNodes.get(food));
        }
    }

    private static class Node {
        private String food;
        private Map<String, Node> childNodes;

        public Node(String food) {
            this.food = food;
            this.childNodes = new HashMap<>();
        }
    }
}