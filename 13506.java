import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static String S;
    private static int[] failFunction;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            S = br.readLine();
            if (S.length() == 1) {
                System.out.println(-1);
                return;
            }

            failFunction = new int[S.length()];
            makeFailFunction();

            int maxSubStringLength = failFunction[S.length() - 1];
            while (maxSubStringLength != 0) {
                for (int i = 1; i < S.length() - 1; i++) {
                    if (failFunction[i] == maxSubStringLength) {
                        System.out.println(S.substring(0, maxSubStringLength));
                        return;
                    }
                }
                maxSubStringLength = failFunction[maxSubStringLength - 1];
            }
            System.out.println(-1);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void makeFailFunction () {
        for (int i = 1, j = 0; i + j < S.length(); ) {
            if (S.charAt(i + j) == S.charAt(j)) {
                failFunction[i + j] = j + 1;
                j++;
            } else {
                if (j == 0) {
                    failFunction[i + j] = 0;
                    i++;
                } else {
                    i += j - failFunction[j - 1];
                    j = failFunction[j - 1];
                }
            }
        }
    }
}