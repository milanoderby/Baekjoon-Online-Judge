import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    private static final int MAX_NUM_OF_ALPHABET = 26;
    private static boolean[] canChangeTile;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int N = Integer.parseInt(br.readLine());
            canChangeTile = new boolean[N];
            String asciiRoad = br.readLine();

            int M = Integer.parseInt(br.readLine());
            Trie trie = new Trie();
            for (int i = 0; i < M; i++) {
                trie.insert(br.readLine());
            }
            trie.makeFailureLink();
            trie.find(asciiRoad);

            int answer = 0;
            for (int i = 0; i < N; i++) {
                if (!canChangeTile[i]) {
                    answer++;
                }
            }
            System.out.println(answer);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static class Trie {
        private Node rootNode;

        public Trie() {
            rootNode = new Node();
            rootNode.failureLink = this.rootNode;
        }

        public void insert(String word) {
            Node temp = this.rootNode;
            for (int i = 0; i < word.length(); i++) {
                int next = word.charAt(i) - 'a';
                if (temp.character[next] == null) {
                    temp.character[next] = new Node();
                }
                temp = temp.character[next];
                temp.lengthOfWord = i + 1;
            }
            temp.isCompleteWord = true;
            temp.outputLink = true;
        }

        public void find(String s) {
            Node temp = this.rootNode;
            for (int i = 0; i < s.length(); i++) {
                int next = s.charAt(i) - 'a';
                while (temp != rootNode && temp.character[next] == null) {
                    temp = temp.failureLink;
                }

                if (temp.character[next] != null) {
                    temp = temp.character[next];
                    findCompleteWord(temp, i);
                }
            }
        }

        private void findCompleteWord(Node temp, int index) {
            if (temp.outputLink) {
                Node temp2 = temp;
                while (!temp2.isCompleteWord) {
                    temp2 = temp2.failureLink;
                }
                for (int j = 0; j < temp2.lengthOfWord; j++) {
                    canChangeTile[index - j] = true;
                }
            }
        }

        public void makeFailureLink() {
            Queue<Node> myQ = new LinkedList<>();
            myQ.add(rootNode);

            while(!myQ.isEmpty()) {
                Node cur = myQ.poll();
                for (int i = 0; i < MAX_NUM_OF_ALPHABET; i++) {
                    if (cur.character[i] != null) {
                        Node next = cur.character[i];
                        if (cur == this.rootNode) {
                            next.failureLink = this.rootNode;
                        } else{
                            Node temp = cur.failureLink;
                            while (temp.character[i] == null && temp != rootNode) {
                                temp = temp.failureLink;
                            }
                            if (temp.character[i] != null) {
                                temp = temp.character[i];
                            }
                            next.failureLink = temp;
                        }
                        if (next.failureLink.outputLink) {
                            next.outputLink = true;
                        }
                        myQ.add(next);
                    }
                }
            }
        }
    }

    private static class Node {
        private boolean isCompleteWord;
        boolean outputLink;
        private int lengthOfWord;
        private Node[] character;
        private Node failureLink;

        public Node() {
            isCompleteWord = false;
            outputLink = false;
            lengthOfWord = 0;
            character = new Node[MAX_NUM_OF_ALPHABET];
        }
    }
}