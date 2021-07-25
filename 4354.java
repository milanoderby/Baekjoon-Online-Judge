import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            String s = br.readLine();
            if (s.equals(".")) {
                bw.flush();
                return;
            }

            int[] pi = getPiArray(s);
            if (pi[s.length() - 1] < s.length() / 2) {
                bw.append(1 + "\n");
                continue;
            }

            int lengthOfRepetitiveString = s.length() - pi[s.length() - 1];
            if (s.length() % lengthOfRepetitiveString == 0) {
                bw.append(s.length() / lengthOfRepetitiveString + "\n");
            } else {
                bw.append(1 + "\n");
            }
        }
    }

    private static int[] getPiArray(String str) {
        int[] pi = new int[str.length()];
        for (int i = 1, j = 0; i + j < str.length(); ) {
            if (str.charAt(i + j) == str.charAt(j)) {
                pi[i + j] = j + 1;
                j++;
            } else {
                if (j > 0) {
                    i += j - pi[j - 1];
                    j = pi[j - 1];
                } else {
                    i++;
                }
            }
        }
        return pi;
    }
}