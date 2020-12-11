import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static String S, P;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            S = br.readLine();
            P = br.readLine();

            for (int i = 0; i + P.length() <= S.length(); i++) {
                boolean isPartString = true;
                for (int j = 0; j < P.length(); j++) {
                    if (S.charAt(i + j) != P.charAt(j)) {
                        isPartString = false;
                        break;
                    }
                }

                if (isPartString) {
                    System.out.println(1);
                    return;
                }
            }
            System.out.println(0);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
