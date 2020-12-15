import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    private static int N, Q;
    private static final int ALPHABET_KIND = 26;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());

            Trie trie = new Trie();
            for (int i = 0; i < N; i++) {
                String s = br.readLine();
                trie.insert(s);
            }
            trie.makeFailureLink();

            Q = Integer.parseInt(br.readLine());
            for (int i = 0; i < Q; i++) {
                if (trie.find(br.readLine())){
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
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
            rootNode.failureLink = rootNode;
            rootNode.isCompletePattern = false;
        }

        public void insert (String s) {
            Node temp = rootNode;
            for (int i = 0; i < s.length(); i++) {
                int charNum = s.charAt(i) - 'a';
                if (temp.nextCharacter[charNum] == null) {
                    temp.nextCharacter[charNum] = new Node();
                }
                temp = temp.nextCharacter[charNum];

                if (i == s.length() - 1) {
                    temp.isCompletePattern = true;
                }
            }
        }

        public boolean find (String q) {
            Node temp = rootNode;
            boolean result = false;
            for (int i = 0; i < q.length(); i++) {
                int charNum = q.charAt(i) - 'a';
                while (temp != rootNode && temp.nextCharacter[charNum] != null){
                    temp = temp.failureLink;
                }

                if (temp.nextCharacter[charNum] != null) {
                    temp = temp.nextCharacter[charNum];
                }

                if (temp.isCompletePattern) {
                    result = true;
                    break;
                }
            }
            return result;
        }

        public void makeFailureLink() {
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(rootNode);
            while (!nodeQueue.isEmpty()) {
                Node cur = nodeQueue.peek();
                nodeQueue.poll();

                for (int i = 0; i < ALPHABET_KIND; i++) {
                    Node next = cur.nextCharacter[i];
                    if (next == null) {
                        continue;
                    }

                    if (cur == rootNode) {
                        next.failureLink = rootNode;
                        nodeQueue.add(next);
                        continue;
                    }

                    Node temp = cur;
                    while (temp != rootNode && temp.nextCharacter[i] == null) {
                        temp = temp.failureLink;
                    }
                    if (temp.nextCharacter[i] != null) {
                        temp = temp.nextCharacter[i];
                    }
                    next.failureLink = temp;

                    if (next.failureLink.isCompletePattern) {
                        next.isCompletePattern = true;
                    }
                    nodeQueue.add(next);
                }
            }
        }
    }

    private static class Node {
        private Node[] nextCharacter;
        private Node failureLink;
        private boolean isCompletePattern = false;

        public Node () {
            nextCharacter = new Node[ALPHABET_KIND];
        }
    }
}