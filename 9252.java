import java.io.*;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String s1 = br.readLine();
        String s2 = br.readLine();

        int[][] lengthOfLCS = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    lengthOfLCS[i][j] = lengthOfLCS[i - 1][j - 1] + 1;
                } else {
                    lengthOfLCS[i][j] = Math.max(lengthOfLCS[i - 1][j], lengthOfLCS[i][j - 1]);
                }
            }
        }

        int i = s1.length();
        int j = s2.length();
        Stack<Character> LCS = new Stack<>();
        while (lengthOfLCS[i][j] > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                LCS.add(s1.charAt(i - 1));
                i--;
                j--;
            } else if (lengthOfLCS[i][j] == lengthOfLCS[i -1][j]) {
                i--;
            } else if(lengthOfLCS[i][j] == lengthOfLCS[i][j - 1]) {
                j--;
            }
        }

        bw.append(lengthOfLCS[s1.length()][s2.length()] + "\n");
        while (!LCS.isEmpty()) {
            bw.append(LCS.pop());
        }
        bw.flush();
    }
}