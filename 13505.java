import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    private static final int BIT_KIND = 2;
    private static int[] inputNum;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());

            inputNum = new int[N];
            Trie trie = new Trie();
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                inputNum[i] = Integer.parseInt(tokenizer.nextToken());
                trie.insert(inputNum[i]);
            }

            int answer = 0;
            for (int i = 0; i < N; i++) {
                int maxXorResult = trie.findMaxXorResult(inputNum[i]);
                if (answer < maxXorResult) {
                    answer = maxXorResult;
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

        public Trie () {
            rootNode = new Node();
        }

        public void insert (int num) {
            Node temp = rootNode;
            String binaryString = getEntireBinaryString(num);
            for (int i = 0; i < binaryString.length(); i++) {
                int bitNum = binaryString.charAt(i) - '0';
                if (temp.hasChildNode[bitNum] == null) {
                    temp.hasChildNode[bitNum] = new Node();
                }
                temp = temp.hasChildNode[bitNum];
            }
        }

        public int findMaxXorResult(int num) {
            int multiplier = (int) Math.pow(2, 30);
            int answer = 0;
            Node temp = rootNode;
            String binaryString = getEntireBinaryString(num);
            for (int i = 0; i < binaryString.length(); i++) {
                int bitNum = binaryString.charAt(i) - '0';
                if (temp.hasChildNode[1 - bitNum] != null) {
                    temp = temp.hasChildNode[1 - bitNum];
                    answer += multiplier;
                } else {
                    temp = temp.hasChildNode[bitNum];
                }
                multiplier /= 2;
            }
            return answer;
        }
    }

    private static class Node {
        private Node[] hasChildNode;

        public Node () {
            hasChildNode = new Node[BIT_KIND];
        }
    }

    private static String getEntireBinaryString (int num) {
        String binaryStr = Integer.toBinaryString(num);
        StringBuilder entireBinaryStr = new StringBuilder();
        for (int i = 0; i < 31 - binaryStr.length(); i++) {
            entireBinaryStr.append('0');
        }
        entireBinaryStr.append(binaryStr);
        return entireBinaryStr.toString();
    }
}
