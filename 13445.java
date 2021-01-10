import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static final int MAX_NUM_OF_DIGIT = 2;
    private static final int MAX_NUM_OF_BIT = 20;
    private static long answer = 0;
    private static String twentyBitBinaryNumOfK;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(tokenizer.nextToken());
            int K = Integer.parseInt(tokenizer.nextToken());
            twentyBitBinaryNumOfK = convertNumTo20BitBinaryNumber(K);

            Trie trie = new Trie();
            int xorResult = 0;
            tokenizer = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                int num = Integer.parseInt(tokenizer.nextToken());
                xorResult ^= num;
                if (xorResult < K) {
                    answer++;
                }
                String twentyBitBinaryNumber = convertNumTo20BitBinaryNumber(xorResult);
                trie.findXorResultSmallerThanK(twentyBitBinaryNumber);
                trie.insert(twentyBitBinaryNumber);
            }

            System.out.println(answer);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static String convertNumTo20BitBinaryNumber(int num) {
        String binaryString = Integer.toBinaryString(num);
        int zeroNumToAdd = MAX_NUM_OF_BIT - binaryString.length();
        StringBuilder twentyBitBinaryNumber = new StringBuilder();
        for (int i = 0; i < zeroNumToAdd; i++) {
            twentyBitBinaryNumber.append("0");
        }
        twentyBitBinaryNumber.append(binaryString);
        return twentyBitBinaryNumber.toString();
    }

    private static class Trie {
        private Node rootNode;

        public Trie() {
            rootNode = new Node();
        }

        public void insert(String xorResult) {
            Node temp = this.rootNode;
            for (int i = 0; i < xorResult.length(); i++) {
                int next = xorResult.charAt(i) - '0';
                if (temp.bit[next] == null) {
                    temp.bit[next] = new Node();
                }
                temp = temp.bit[next];
                temp.counter++;
            }
        }

        public void findXorResultSmallerThanK(String xorOperand) {
            Node temp = this.rootNode;
            for (int i = 0; i < xorOperand.length(); i++) {
                int bitOfK = twentyBitBinaryNumOfK.charAt(i) - '0';
                int next = xorOperand.charAt(i) - '0';
                if (bitOfK == 0) {
                    if (temp.bit[next] == null) {
                        break;
                    }
                    temp = temp.bit[next];
                } else {
                    if (temp.bit[next] != null) {
                        answer += temp.bit[next].counter;
                    }

                    if (temp.bit[1 - next] == null) {
                        break;
                    }
                    temp = temp.bit[1 - next];
                }
            }
        }
    }

    private static class Node {
        private Node[] bit;
        private int counter;

        public Node() {
            bit = new Node[MAX_NUM_OF_DIGIT];
            counter = 0;
        }
    }
}