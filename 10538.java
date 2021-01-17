import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static int MAX_NUM_OF_PIXEL = 2;

    private static int heightOfPark;
    private static int widthOfPark;
    private static int[] patternOfPark;

    private static int heightOfMaster;
    private static int widthOfMaster;
    private static int[][] patternOfMaster;

    private static int[] failFuncOfKMP;
    private static int answer;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());

            heightOfPark = Integer.parseInt(tokenizer.nextToken());
            widthOfPark = Integer.parseInt(tokenizer.nextToken());

            failFuncOfKMP = new int[heightOfPark];
            patternOfPark = new int[heightOfPark];

            heightOfMaster = Integer.parseInt(tokenizer.nextToken());
            widthOfMaster = Integer.parseInt(tokenizer.nextToken());
            patternOfMaster = new int [heightOfMaster][widthOfMaster];

            Trie trie = new Trie();
            Map<String, Integer> picturePatternMap = new HashMap<>();
            int patternNum = 1;
            for (int i = 0; i < heightOfPark; i++) {
                String pattern = br.readLine();
                if (!picturePatternMap.containsKey(pattern)) {
                    picturePatternMap.put(pattern, patternNum);
                    patternNum++;
                }

                int patternNumber = picturePatternMap.get(pattern);
                patternOfPark[i] = patternNumber;
                trie.insert(pattern, patternNumber);
            }
            trie.makeFailureLink();

            for (int i = 0; i < heightOfMaster; i++) {
                String pattern = br.readLine();
                trie.find(i, pattern);
            }

            makeFailFuncOfKMP();
            findPatternOfParkInPatternOfMaster();
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
            rootNode.failureLink = rootNode;
        }

        public void insert(String pattern, int patternNumber) {
            Node temp = rootNode;
            for (int i = 0; i < pattern.length(); i++) {
                int next = pattern.charAt(i) == 'o' ? 1 : 0;
                if (temp.pixel[next] == null) {
                    temp.pixel[next] = new Node();
                }
                temp = temp.pixel[next];
            }
            temp.findPattern = true;
            temp.patternNumber = patternNumber;
        }

        public void makeFailureLink() {
            Queue<Node> myQ = new LinkedList<>();
            myQ.add(rootNode);

            while (!myQ.isEmpty()) {
                Node cur = myQ.poll();
                for (int i = 0; i < MAX_NUM_OF_PIXEL; i++) {
                    if (cur.pixel[i] == null) {
                        continue;
                    }

                    Node next = cur.pixel[i];
                    if (cur == rootNode) {
                        next.failureLink = rootNode;
                    } else {
                        Node temp = cur.failureLink;
                        while (temp != rootNode && temp.pixel[i] == null) {
                            temp = temp.failureLink;
                        }

                        if (temp.pixel[i] != null) {
                            temp = temp.pixel[i];
                        }
                        next.failureLink = temp;
                    }
                    myQ.add(next);
                }
            }
        }

        public void find(int lineNum, String pattern) {
            Node cur = rootNode;
            for (int i = 0; i < pattern.length(); i++) {
                int next = pattern.charAt(i) == 'o' ? 1 : 0;
                if (cur.pixel[next] != null) {
                    cur = cur.pixel[next];
                } else {
                    Node temp = cur.failureLink;
                    while (temp != rootNode && temp.pixel[next] == null) {
                        temp = temp.failureLink;
                    }

                    if (temp.pixel[next] != null) {
                        temp = temp.pixel[next];
                    }
                    cur = temp;
                }

                if (cur.findPattern) {
                    patternOfMaster[lineNum][i - widthOfPark + 1] = cur.patternNumber;
                }
            }
        }
    }

    private static class Node {
        private Node[] pixel;
        private Node failureLink;
        private boolean findPattern;
        private int patternNumber;

        public Node() {
            pixel = new Node[MAX_NUM_OF_PIXEL];
            findPattern = false;
        }
    }

    private static void makeFailFuncOfKMP() {
        for (int i = 1, j = 0; i + j < heightOfPark; ) {
            if (patternOfPark[i + j] == patternOfPark[j]) {
                failFuncOfKMP[i + j] = j + 1;
                j++;
            } else {
                if (j == 0) {
                    i++;
                } else {
                    i += j - failFuncOfKMP[j - 1];
                    j = failFuncOfKMP[j - 1];
                }
            }
        }
    }

    private static void findPatternOfParkInPatternOfMaster() {
        for (int j = 0; j < widthOfMaster; j++) {
            for (int i = 0, k = 0; i + heightOfPark <= heightOfMaster; ) {
                if (k < heightOfPark && patternOfMaster[i + k][j] == patternOfPark[k]) {
                    k++;
                    if (k == heightOfPark) {
                        answer++;
                    }
                } else {
                    if (k == 0) {
                        i++;
                    } else {
                        i += k - failFuncOfKMP[k - 1];
                        k = failFuncOfKMP[k - 1];
                    }
                }
            }
        }
    }
}