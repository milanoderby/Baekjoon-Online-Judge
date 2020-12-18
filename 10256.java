import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static int T, n, m;
    private static String DNA, marker;
    private static final int DNA_KIND = 4;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            T = Integer.parseInt(br.readLine());
            for (int testCase = 0; testCase < T; testCase++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                n = Integer.parseInt(tokenizer.nextToken());
                m = Integer.parseInt(tokenizer.nextToken());

                DNA = br.readLine();
                marker = br.readLine();

                Trie trie = new Trie();
                trie.insert(marker);
                for (int i = 0; i < marker.length(); i++) {
                    StringBuilder stringBuilder = new StringBuilder(marker);
                    for (int j = i + 1; j < marker.length(); j++) {
                        char ch = stringBuilder.charAt(j);
                        stringBuilder.deleteCharAt(j)
                                     .insert(i, ch);
                        trie.insert(stringBuilder.toString());
                    }
                }
                trie.makeFailureLink();
                int answer = trie.find(DNA);
                System.out.println(answer);
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

        private int convertDnaToNum (char dna) {
            int num;
            switch (dna) {
                case 'A':
                    num = 0;
                    break;
                case 'C':
                    num = 1;
                    break;
                case 'G':
                    num = 2;
                    break;
                case 'T':
                default:
                    num = 3;
                    break;
            }
            return num;
        }

        public void insert (String s) {
            Node temp = rootNode;
            for (int i = 0; i < s.length(); i++) {
                int charNum = convertDnaToNum(s.charAt(i));
                if (temp.nextCharacter[charNum] == null) {
                    temp.nextCharacter[charNum] = new Node();
                }
                temp = temp.nextCharacter[charNum];

                if (i == s.length() - 1) {
                    temp.outputCnt = 1;
                }
            }
        }

        public int find (String q) {
            int findCount = 0;
            Node temp = rootNode;
            for (int i = 0; i < q.length(); i++) {
                int charNum = convertDnaToNum(q.charAt(i));
                while (temp != rootNode && temp.nextCharacter[charNum] == null) {
                    temp = temp.failureLink;
                }

                if (temp.nextCharacter[charNum] != null) {
                    temp = temp.nextCharacter[charNum];
                }
                findCount += temp.outputCnt;
            }
            return findCount;
        }

        public void makeFailureLink() {
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(rootNode);

            while (!nodeQueue.isEmpty()) {
                Node cur = nodeQueue.peek();
                nodeQueue.poll();

                for (int i = 0; i < DNA_KIND; i++) {
                    Node next = cur.nextCharacter[i];
                    if (next == null) {
                        continue;
                    }

                    if (cur == rootNode) {
                        next.failureLink = rootNode;
                    } else {
                        Node temp = cur.failureLink;
                        while (temp != rootNode && temp.nextCharacter[i] == null) {
                            temp = temp.failureLink;
                        }

                        if (temp.nextCharacter[i] != null) {
                            temp = temp.nextCharacter[i];
                        }
                        next.failureLink = temp;
                    }
                    next.outputCnt += next.failureLink.outputCnt;
                    nodeQueue.add(next);
                }
            }
        }
    }

    private static class Node {
        private Node[] nextCharacter;
        private Node failureLink;
        private int outputCnt;

        public Node () {
            nextCharacter = new Node[DNA_KIND];
            outputCnt = 0;
        }
    }
}