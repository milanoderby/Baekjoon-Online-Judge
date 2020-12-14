import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static int N, Q;
    private static final int ALPHABET_KIND = 26;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());

            Trie trie = new Trie();
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                trie.insert(tokenizer.nextToken());
            }

            Q = Integer.parseInt(br.readLine());
            tokenizer = new StringTokenizer(br.readLine());
            for (int i = 0; i < Q; i++) {
                String q = tokenizer.nextToken();
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static class Trie {
        private Node rootNode;

        public Trie () {
            rootNode = new Node();
        }

        public void insert (String s) {
            Node temp = rootNode;
            for (int i = 0; i < s.length(); i++) {
                int charNum = s.charAt(i) - 'a';
                if (temp.nextCharacter[charNum] == null) {
                    temp.nextCharacter[charNum] = new Node();
                    if (i == s.length() - 1) {
                        temp.isCompletePattern = true;
                    }
                }
                temp = temp.nextCharacter[charNum];
            }
        }

        public void find (String q) {

        }

        public void makeFailureLink() {
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(rootNode);
            while (!nodeQueue.isEmpty()) {
                Node cur = nodeQueue.peek();
                nodeQueue.poll();

                for (int i = 0; i < ALPHABET_KIND; i++) {
                    if (cur.nextCharacter[i] == null) {
                        continue;
                    }

                    Node next = cur.nextCharacter[i];
                    if (cur == rootNode) {
                        next.failureLink = rootNode;
                        nodeQueue.add(next);
                        continue;
                    }

                    Node temp = cur.failureLink;
                    while (temp != null) {
                        if (temp.nextCharacter[i] != null) {
                            next.failureLink = temp.nextCharacter[i];
                            break;
                        }
                        temp = temp.failureLink;
                    }

                    temp = next.failureLink;
                    while (temp != null) {
                        if (temp.isCompletePattern) {
                            next.outputLink = temp;
                            break;
                        }
                        temp = temp.failureLink;
                    }
                    nodeQueue.add(next);
                }
            }
        }
    }

    private static class Node {
        private Node[] nextCharacter;
        private Node failureLink;
        private Node outputLink;
        private boolean isCompletePattern;

        public Node () {
            nextCharacter = new Node[ALPHABET_KIND];
        }
    }
}