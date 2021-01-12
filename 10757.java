import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            String A = tokenizer.nextToken();
            String B = tokenizer.nextToken();

            StringBuilder result = new StringBuilder();
            int c = 0;

            int i, j;
            for (i = A.length() - 1, j = B.length() - 1; i >= 0 && j >= 0; i--, j--) {
                int a = A.charAt(i) - '0';
                int b = B.charAt(j) - '0';
                char ch = (char) (((a + b + c) % 10) + '0');
                result.append(ch);
                c = (a + b + c) / 10;
            }

            if (i < 0 && j < 0) {

            } else if (i < 0) {
                for ( ; j >= 0; j--) {
                    int b = B.charAt(j) - '0';
                    char ch = (char) (((b + c) % 10) + '0');
                    result.append(ch);
                    c = (b + c) / 10;
                }
            } else if (j < 0) {
                for ( ; i >= 0; i--) {
                    int a = A.charAt(i) - '0';
                    char ch = (char) (((a + c) % 10) + '0');
                    result.append(ch);
                    c = (a + c) / 10;
                }
            }
            if (c == 1) {
                result.append('1');
            }

            System.out.println(result.reverse().toString());
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}