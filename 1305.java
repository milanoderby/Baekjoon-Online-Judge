import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static int L;
    private static String advertisement;
    private static int[] failFunc;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            L = Integer.parseInt(br.readLine());
            advertisement = br.readLine();
            failFunc = new int[advertisement.length()];
            makeFailFunc();

            System.out.println(L - failFunc[L - 1]);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void makeFailFunc() {
        for (int i = 1, j = 0; i + j < advertisement.length(); ) {
            if (advertisement.charAt(i + j) == advertisement.charAt(j)) {
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
