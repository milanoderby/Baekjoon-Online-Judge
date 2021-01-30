import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
    private static int SIZE = 46;
    private static int[] fibonacci;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(br.readLine());
            fibonacci = new int[SIZE];
            fibonacci[1] = 1;

            if (n <= 1) {
                System.out.println(fibonacci[n]);
                return;
            }

            for (int i = 2; i <= n; i++) {
                fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
            }
            System.out.println(fibonacci[n]);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}