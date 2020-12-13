import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static String S;
    private static int[] failFunc;
    private static int answer = 0;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            S = br.readLine();

            failFunc = new int[S.length()];

            for (int i = 0; i < S.length(); i++) {
                for (int j = 0; j < failFunc.length; j++) {
                    failFunc[j] = 0;
                }
                String pattern = S.substring(i);
                makeFailFunc(pattern);
                for (int j = 0; j < failFunc.length; j++) {
                    if (answer < failFunc[j]) {
                        answer = failFunc[j];
                    }
                }
            }
            System.out.println(answer);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void makeFailFunc (String pattern) {
        for (int i = 1, j = 0; i + j < pattern.length(); ) {
            if (pattern.charAt(i + j) == pattern.charAt(j)) {
                failFunc[i + j] = j + 1;
                j++;
            } else {
                if (j <= 0) {
                    failFunc[i + j] = 0;
                    i++;
                } else {
                    i += j - failFunc[j - 1];
                    j = failFunc[j - 1];
                }
            }
        }
    }
}
