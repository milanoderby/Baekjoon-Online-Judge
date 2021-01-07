import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    private static final int MAX_NUM_OF_DIGIT = 2;
    private static final int MAX_NUM_OF_BIT = 32;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int T = Integer.parseInt(br.readLine());
            for (int i = 0; i < T; i++) {
                int N = Integer.parseInt(br.readLine());
                Set<String> xorSumString = new HashSet<>();
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                int xorSum = 0;
                int answer = 0;
                Trie trie = new Trie();
                for (int j = 0; j < N; j++) {
                    xorSum ^= Integer.parseInt(tokenizer.nextToken());
                    answer = Math.max(answer, xorSum);
                    String completeBinaryString = convertIntToCompleteBinaryString(xorSum);
                    trie.insert(completeBinaryString);
                    xorSumString.add(completeBinaryString);
                }

                for (String str : xorSumString) {
                    int maxXorResult = trie.findMaxXorResult(str);
                    answer = Math.max(answer, maxXorResult);
                }
                System.out.println(answer);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static String convertIntToCompleteBinaryString(int binaryNumber) {
        String binaryString = Integer.toBinaryString(binaryNumber);
        int numOfZero = MAX_NUM_OF_BIT - binaryString.length();
        StringBuilder completeBinaryString = new StringBuilder();
        for (int i = 0; i < numOfZero; i++) {
            completeBinaryString.append("0");
        }
        completeBinaryString.append(binaryString);
        return completeBinaryString.toString();
    }

    private static class Trie {
        private Node rootNode;
        public Trie() {
            rootNode = new Node();
        }

        public void insert(String binaryNumber) {
            Node temp = this.rootNode;
            for (int i = 0; i < binaryNumber.length(); i++) {
                int digit = binaryNumber.charAt(i) - '0';
                if (temp.bit[digit] == null) {
                    temp.bit[digit] = new Node();
                }
                temp = temp.bit[digit];
            }
            temp.isComplete = true;
        }

        public int findMaxXorResult(String binaryNumber) {
            Node temp = this.rootNode;
            int maxXorResult = 0;
            double complier =  Math.pow(2, MAX_NUM_OF_BIT - 1);
            for (int i = 0; i < binaryNumber.length(); i++) {
                int digit = binaryNumber.charAt(i) - '0';
                if (temp.bit[1 - digit] != null) {
                    temp = temp.bit[1 - digit];
                    maxXorResult += complier;
                } else {
                    temp = temp.bit[digit];
                }
                complier /= 2;
            }
            return maxXorResult;
        }
    }

    private static class Node {
        private boolean isComplete;
        private Node[] bit;

        public Node() {
            isComplete = false;
            bit = new Node[MAX_NUM_OF_DIGIT];
        }
    }
}