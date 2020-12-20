import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static String A, B;
    private static int[] failFunction;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            A = br.readLine();
            B = br.readLine();

            failFunction = new int[A.length()];
            makeFailFunction();

            int answer = 0;
            String circularB = B.concat(B.substring(0, B.length() - 1));
            for (int i = 0, j = 0; i + j < circularB.length(); ) {
                if (circularB.charAt(i + j) == A.charAt(j)) {
                    j++;
                    if (j == A.length()) {
                        i += j - failFunction[j - 1];
                        j = failFunction[j - 1];
                        answer++;
                    }
                } else {
                    if (j == 0){
                        i++;
                    } else {
                        i += j - failFunction[j - 1];
                        j = failFunction[j - 1];
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

    private static void makeFailFunction() {
        for (int i = 1, j = 0; i < A.length(); ) {
            if (A.charAt(i) == A.charAt(j)) {
                failFunction[i] = j + 1;
                i++;
                j++;
            } else {
                if (j == 0) {
                    failFunction[i] = 0;
                    i++;
                } else {
                    j = failFunction[j - 1];
                }
            }
        }
    }
}