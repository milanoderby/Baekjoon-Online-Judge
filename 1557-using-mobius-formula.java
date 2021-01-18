import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static int MAX = 42000;
    private static int[] mobiusFunction;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int K = Integer.parseInt(br.readLine());
            mobiusFunction = new int[MAX + 1];
            getMobiusFunctionList();

            long left = 0;
            long right = 1700000000;

            while (true) {
                long mid = (left + right) / 2;
                if (getCountOfSquareFreeNumber(mid) < K) {
                    left = mid;
                } else if (getCountOfSquareFreeNumber(mid) > K) {
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

    private static void getMobiusFunctionList() {
        mobiusFunction[1] = 1;
        for(int i = 1; i <= MAX; i++) {
            for(int j = 2 * i; j <= MAX; j += i) {
                mobiusFunction[j] -= mobiusFunction[i];
            }
        }
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