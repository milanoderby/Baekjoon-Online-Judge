import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    private static long MAX = 1000000;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            long min = Long.parseLong(tokenizer.nextToken());
            long max = Long.parseLong(tokenizer.nextToken());

            Set<Long> numbers = new HashSet<>();
            for (long i = min; i <= max; i++) {
                numbers.add(i);
            }

            for (long i = 2; i <= MAX; i++) {
                long primeNumber = i * i;
                long begin = min / primeNumber;
                long end = max / primeNumber;
                for (long j = begin; j <= end; j++) {
                    numbers.remove(j * primeNumber);
                }
            }

            System.out.println(numbers.size());
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}