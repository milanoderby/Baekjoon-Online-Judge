import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static String T, P;
    private static int[] failFunc;
    private static int answer;
    private static List<Integer> patternIndexList;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            T = br.readLine();
            P = br.readLine();

            failFunc = new int[P.length()];
            makeFailFunc();

            patternIndexList = new ArrayList<>();
            kmpAlgorithm();

            System.out.println(answer);
            for (Integer index : patternIndexList) {
                System.out.println(index);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void kmpAlgorithm() {
        for (int i = 0, j = 0; i + P.length() <= T.length(); ) {
            if (T.charAt(i + j) == P.charAt(j)) {
                j++;
                if (j >= P.length()) {
                    answer++;
                    patternIndexList.add(i + 1);
                    i += j - failFunc[j - 1];
                    j = failFunc[j - 1];
                }
            } else {
                if (j <= 0) {
                    i++;
                } else {
                    i += j - failFunc[j - 1];
                    j = failFunc[j - 1];
                }
            }
        }
    }

    private static void makeFailFunc() {
        for (int i = 1, j = 0; i + j < P.length(); ) {
            if (P.charAt(i + j) == P.charAt(j)) {
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
