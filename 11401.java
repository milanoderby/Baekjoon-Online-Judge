import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static long N, K;
    private static long DIVISOR = 1000000007;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            K = Integer.parseInt(tokenizer.nextToken());

            long A = getFactorial(N);
            long B = ((getFactorial(K) % DIVISOR) * getFactorial(N-K) % DIVISOR) % DIVISOR;
            long answer = ((A % DIVISOR) * (getPow(B, DIVISOR - 2) % DIVISOR)) % DIVISOR;
            System.out.println(answer);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static long getPow(long n, long k) {
        long answer = 1;
        long numberToMultiply = n;
        String binaryString = Long.toBinaryString(k);
        for (int i = binaryString.length() - 1; i >= 0; i--) {
            if(binaryString.charAt(i) == '1'){
                answer = ((answer % DIVISOR) * (numberToMultiply % DIVISOR)) %DIVISOR;
            }
            numberToMultiply = (numberToMultiply * numberToMultiply) % DIVISOR;
        }
        return answer;
    }

    private static long getFactorial(long n) {
        long answer = 1;
        for (int i = 2; i <= n; i++) {
            answer = ((answer % DIVISOR) * (i % DIVISOR)) % DIVISOR;
        }
        return answer;
    }
}
