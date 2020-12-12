import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static String S, P;
    private static int[] failFunc;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            S = br.readLine();
            P = br.readLine();

            failFunc = new int[P.length()];

            makeFailFunc();

            for (int i = 0; i + P.length() <= S.length(); i++) {
                for (int j = 0; j < S.length(); j++) {
                    if (S.charAt(i + j) == P.charAt(j)) {
                        j++;
                        if (j >= P.length()) {
                            System.out.println(1);
                            return;
                        }
                    } else {
                        i = j - failFunc[j - 1];
                        j = failFunc[j - 1];
                    }
                }
            }

            System.out.println(0);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void makeFailFunc () {
        for (int i = 1, j = 0; i < S.length(); i++) {
            if (S.charAt(i + j) == P.charAt(j)) {
                j++;
            } else {
                j--;
            }

            if (j >= i) {
                failFunc[i] = j + 1;
                i++;
            } else if (j < 0) {
                failFunc[i] = 0;
                j = 0;
                i++;
            }
        }
    }
}
