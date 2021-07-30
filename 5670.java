import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static int countOfClick;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String num;
        while ((num = br.readLine()) != null) {
            int N = Integer.parseInt(num);

            Trie trie = new Trie();
            countOfClick = 0;
            for (int i = 0; i < N; i++) {
                String word = br.readLine();
                trie.insert(word);
            }

            for (Character key : trie.getRoot().next.keySet()) {
                getCountOfClickingButton(trie.getRoot().next.get(key), 1);
            }
            double answer = Math.round(100 * countOfClick / (double) N) / 100.0;
            bw.append(String.format("%.2f\n", answer));
        }
        bw.flush();
        return;
    }

    private static class Trie {
        private Node root;

        public Trie() {
            this.root = new Node();
        }

        public Node getRoot() {
            return this.root;
        }

        public void insert(String word) {
            Node temp = root;
            for (int i = 0; i < word.length(); i++) {
                char key = word.charAt(i);
                if (!temp.next.containsKey(key)) {
                    temp.next.put(key, new Node());
                }
                temp = temp.next.get(key);

                if (i == word.length() - 1) {
                    temp.isFinished = true;
                }
            }
        }
    }

    private static void getCountOfClickingButton(Node cur, int cnt) {
        if (cur.isFinished) {
            countOfClick += cnt;
        }

        if (!cur.isFinished && cur.next.size() == 1) {
            for (Character key : cur.next.keySet()) {
                getCountOfClickingButton(cur.next.get(key), cnt);
            }
        } else {
            for (Character key : cur.next.keySet()) {
                getCountOfClickingButton(cur.next.get(key), cnt + 1);
            }
        }
    }

    private static class Node {
        private Map<Character, Node> next;
        private boolean isFinished;

        public Node() {
            this.next = new HashMap<>();
            this.isFinished = false;
        }
    }
}