import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int N = Integer.parseInt(br.readLine());
            boolean[] canWin = new boolean[N + 1];
            canWin[0] = true;
            for (int i = 1; i <= N; i++) {
                if (i - 1 >= 0) {
                    if (!canWin[i - 1]) {
                        canWin[i] = true;
                        continue;
                    }
                }

                if (i - 3 >= 0) {
                    if (!canWin[i - 3]) {
                        canWin[i] = true;
                        continue;
                    }
                }

                if (i - 4 >= 0) {
                    if (!canWin[i - 4]) {
                        canWin[i] = true;
                        continue;
                    }
                }
            }

            if (canWin[N]) {
                System.out.println("SK");
            } else {
                System.out.println("CY");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}