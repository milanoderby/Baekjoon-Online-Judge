import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static int t;
    private static final int MAX_DIGIT = 10;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            t = Integer.parseInt(br.readLine());

            for (int testCase = 0; testCase < t; testCase++) {
                Trie trie = new Trie();
                int n = Integer.parseInt(br.readLine());
                boolean isConsistentPhoneNumberList = true;
                for (int i = 0; i < n; i++) {
                    String phoneNumber = br.readLine();
                    isConsistentPhoneNumberList &= trie.insert(phoneNumber);
                }

                if (isConsistentPhoneNumberList) {
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

        public Trie() {
            rootNode = new Node();
        }

        public boolean insert(String phoneNumber) {
            boolean isConsistent = false;
            Node temp = this.rootNode;
            for (int i = 0; i < phoneNumber.length(); i++) {
                int num = phoneNumber.charAt(i) - '0';
                if (temp.nextNumber[num] == null) {
                    temp.nextNumber[num] = new Node();
                    isConsistent = true;
                }

                temp = temp.nextNumber[num];
                if (temp.isPresentPhoneNumber) {
                    return false;
                }
            }
            temp.isPresentPhoneNumber = true;
            return isConsistent;
        }
    }

    private static class Node {
        private boolean isPresentPhoneNumber;
        private Node[] nextNumber;

        public Node() {
            nextNumber = new Node[MAX_DIGIT];
        }
    }
}