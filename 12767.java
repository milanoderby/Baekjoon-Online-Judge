import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    private static int N, K;
    private static Tree tree;
    private static List<Set<Integer>> treeShapeList;
    private static Set<Integer> treeShape;
    private static int[][] inputValue;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            K = Integer.parseInt(tokenizer.nextToken());
            inputValue = new int[N][K];


            for (int i = 0; i < N; i++) {
                tokenizer = new StringTokenizer(br.readLine());
                for (int j = 0; j < K; j++) {
                    inputValue[i][j] = Integer.parseInt(tokenizer.nextToken());
                }
            }

            treeShapeList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                tree = new Tree();
                treeShape = new HashSet<>();
                for (int j = 0; j < K; j++) {
                    int position = tree.addNode(inputValue[i][j]);
                    treeShape.add(position);
                }

                boolean findSameTree = false;
                for (Set<Integer> prevTreeShape : treeShapeList) {
                    if (prevTreeShape.containsAll(treeShape)) {
                        findSameTree = true;
                        break;
                    }
                }

                if (!findSameTree) {
                    treeShapeList.add(treeShape);
                }
            }

            System.out.println(treeShapeList.size());
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static class Tree {
        private Node rootNode;

        public int addNode(int value) {
            int pos = 1;
            if (this.rootNode == null) {
                rootNode = new Node(value);
                return pos;
            }

            Node currentNode = this.rootNode;
            while (true) {
                if (value < currentNode.value) {
                    pos = 2 * pos;
                    if (currentNode.left == null) {
                        currentNode.setLeft(new Node(value));
                        return pos;
                    }
                    currentNode = currentNode.left;
                } else {
                    pos = 2 * pos + 1;
                    if (currentNode.right == null) {
                        currentNode.setRight(new Node(value));
                        return pos;
                    }
                    currentNode = currentNode.right;
                }
            }
        }
    }

    private static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node (int value) {
            this.value = value;
        }

        public void setLeft (Node left) {
            this.left = left;
        }

        public void setRight (Node right) {
            this.right = right;
        }
    }
}
