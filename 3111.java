import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static String T, A, reverseA;
    private static StringBuilder frontString, backString;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            A = br.readLine();
            T = br.readLine();
            reverseA = new StringBuilder(A).reverse().toString();

            frontString = new StringBuilder();
            backString = new StringBuilder();
            for (int i = 0, j = T.length() - 1; i <= j; ) {
                while (i <= j) {
                    frontString.append(T.charAt(i));
                    i++;
                    int fLen = frontString.length();
                    int aLen = A.length();
                    if (fLen >= aLen) {
                        if (frontString.substring(fLen - aLen).equals(A)) {
                            frontString.delete(fLen - aLen, fLen);
                            break;
                        }
                    }
                }

                while (i <= j) {
                    backString.append(T.charAt(j));
                    j--;
                    int bLen = backString.length();
                    int aLen = reverseA.length();
                    if (bLen >= aLen) {
                        if (backString.substring(bLen - aLen).equals(reverseA)) {
                            backString.delete(bLen - aLen, bLen);
                            break;
                        }
                    }
                }
            }

            while (backString.length() > 0) {
                frontString.append(backString.charAt(backString.length() - 1));
                backString.deleteCharAt(backString.length() - 1);

                int fLen = frontString.length();
                int aLen = A.length();
                if (fLen >= aLen) {
                    if (frontString.substring(fLen - aLen).equals(A)) {
                        frontString.delete(fLen - aLen, fLen);
                    }
                }
            }

            System.out.println(frontString);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
