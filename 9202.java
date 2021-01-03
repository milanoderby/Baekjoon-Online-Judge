import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Main {
    private static int numOfChar = 26, sizeOfBoard = 4, numOfDir = 8, maxLengthOfWord = 8;
    private static char[][] board;
    private static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
    private static int[] dx = {1, 1, 0, -1, -1, -1, 0, 1};
    private static int[] wordScore = {0, 0, 0, 1, 1, 2, 3, 5, 11};
    private static Set<String> wordSet, foundWordSet;
    private static int maxScore, wordCount;
    private static String maxLengthsString;
    private static WordComparator wordComparator;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            wordComparator = new WordComparator();
            Trie trie = new Trie();
            int w = Integer.parseInt(br.readLine());
            for (int i = 0; i < w; i++) {
                trie.insert(br.readLine());
            }
            br.readLine();

            int b = Integer.parseInt(br.readLine());
            for (int i = 0; i < b; i++) {
                board = new char[sizeOfBoard][sizeOfBoard];
                for (int j = 0; j < sizeOfBoard; j++) {
                    String tmp = br.readLine();
                    for (int k = 0; k < sizeOfBoard; k++) {
                        board[j][k] = tmp.charAt(k);
                    }
                }

                wordSet = new HashSet<>();
                for (int j = 0; j < sizeOfBoard; j++) {
                    for (int k = 0; k < sizeOfBoard; k++) {
                        List<Point> pointList = new ArrayList<>();
                        pointList.add(new Point(j, k));
                        StringBuilder word = new StringBuilder();
                        word.append(board[j][k]);
                        findPossibleWord(j, k, pointList, word);
                    }
                }


                maxLengthsString = "";
                foundWordSet = new HashSet<>();
                for (String word : wordSet) {
                    trie.find(word);
                }
                wordCount = foundWordSet.size();
                maxScore = 0;
                for (String foundWord : foundWordSet) {
                    maxScore += wordScore[foundWord.length()];
                }

                System.out.println(maxScore + " " + maxLengthsString + " " + wordCount);

                if (i != b-1) {
                    br.readLine();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void findPossibleWord(int y, int x, List<Point> pointList, StringBuilder word) {
        wordSet.add(word.toString());
        if (pointList.size() >= maxLengthOfWord) {
            return;
        }

        for (int i = 0; i < numOfDir; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            boolean visited = false;
            for (Point point : pointList) {
                if (point.y == ny && point.x == nx) {
                    visited = true;
                    break;
                }
            }

            if (visited) {
                continue;
            }

            if (0 <= ny && ny < sizeOfBoard && 0 <= nx && nx <sizeOfBoard) {
                pointList.add(new Point(ny, nx));
                word.append(board[ny][nx]);
                findPossibleWord(ny, nx, pointList, word);
                pointList.remove(pointList.size() - 1);
                word.deleteCharAt(word.length() - 1);
            }
        }
    }

    private static class Trie {
        private Node rootNode;

        public Trie () {
            rootNode = new Node();
        }

        public void insert(String str) {
            Node temp = rootNode;
            for (int i = 0; i < str.length(); i++) {
                int ch = str.charAt(i) - 'A';
                if (temp.nextChar[ch] == null) {
                    temp.nextChar[ch] = new Node();
                }
                temp = temp.nextChar[ch];
            }
            temp.isWord = true;
        }

        public void find(String str) {
            Node temp = rootNode;
            for (int i = 0; i < str.length(); i++) {
                int ch = str.charAt(i) - 'A';
                if (temp.nextChar[ch] == null) {
                    break;
                }

                temp = temp.nextChar[ch];
                if (temp.isWord) {
                    String foundString = str.substring(0, i + 1);
                    foundWordSet.add(foundString);
                    if (Objects.compare(maxLengthsString, foundString, wordComparator) < 0) {
                        maxLengthsString = foundString;
                    }
                }
            }
        }
    }

    private static class Node {
        private Node[] nextChar;
        private boolean isWord;

        public Node() {
            nextChar = new Node[numOfChar];
        }
    }

    private static class Point {
        int y, x;
        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    private static class WordComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            if (o1.length() == o2.length()) {
                return o2.compareTo(o1);
            }
            return o1.length() - o2.length();
        }
    }
}