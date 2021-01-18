import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static int MAX = 42000;
    private static List<Integer> primeNumber;
    private static boolean[] isNotPrime;
    private static int[] mobiusFunction;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int K = Integer.parseInt(br.readLine());
            primeNumber = new ArrayList<>();
            isNotPrime = new boolean[MAX + 1];
            mobiusFunction = new int[MAX + 1];

            getPrimeNumberList();
            getMobiusFunctionList();

            long left = 0;
            long right = 1700000000;

            while (true) {
                long mid = (left + right) / 2;
                int squareFreeNumber = getCountOfSquareFreeNumber(mid);
                if (squareFreeNumber < K) {
                    left = mid;
                } else if (squareFreeNumber > K) {
                    right = mid;
                } else {
                    if (isMultipleOfSquareNumber(mid)) {
                        right = mid;
                    } else {
                        System.out.println(mid);
                        break;
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void getPrimeNumberList() {
        isNotPrime[0] = true;
        isNotPrime[1] = true;
        for (int i = 2; i <= MAX; i++) {
            if (isNotPrime[i]) {
                continue;
            }
            primeNumber.add(i);
            for (int j = 2 * i; j <= MAX; j += i) {
                isNotPrime[j] = true;
            }
        }
    }

    private static void getMobiusFunctionList() {
        mobiusFunction[1] = 1;
        for (int i = 2; i <= MAX; i++) {
            mobiusFunction[i] = getMobiusFunction(i);
        }
    }

    private static int getMobiusFunction(int number) {
        Set<Integer> factor = new HashSet<>();
        int temp = number;
        for (int i = 0; i < primeNumber.size() && temp != 1; ) {
            int prime = primeNumber.get(i);
            if (temp % prime == 0) {
                if (factor.contains(prime)) {
                    return 0;
                } else {
                    factor.add(prime);
                }
                temp /= prime;
            } else {
                i++;
            }
        }
        return factor.size() % 2 == 1 ? -1 : 1;
    }

    private static int getCountOfSquareFreeNumber(long x) {
        int countOfSquareFreeNumber = 0;
        for (int i = 1; i <= Math.sqrt(x); i++) {
            countOfSquareFreeNumber += (x / (i * i)) * mobiusFunction[i];
        }
        return countOfSquareFreeNumber;
    }

    private static boolean isMultipleOfSquareNumber(long x) {
        for (int i = 2; i <= Math.sqrt(x); i++) {
            if (x % (i * i) == 0) {
                return true;
            }
        }
        return false;
    }
}