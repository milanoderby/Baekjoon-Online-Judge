import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

public class Main {
    private static int N;
    private static Vector<Integer> lisVector;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        lisVector = new Vector<>();
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int inputNum = Integer.parseInt(tokenizer.nextToken());
            if (i == 0) {
                lisVector.add(inputNum);
            } else {
                pushNum(inputNum);
            }
        }
        System.out.println(lisVector.size());
    }

    private static void pushNum(int num) {
        if (lisVector.firstElement() >= num) {
            lisVector.set(0, num);
            return;
        }

        if (lisVector.lastElement() < num) {
            lisVector.add(num);
            return;
        }

        lisVector.set(getLowerBound(num), num);
    }

    // num 이상인 수 중에 가장 작은 수의 index(위치) 값
    private static int getLowerBound(int num) {
        int start = 0;
        int end = lisVector.size() - 1;
        while (start + 1 < end) {
            int mid = (start + end) / 2;

            if (lisVector.get(mid) >= num) {
                end = mid;
            } else {
                start = mid;
            }
        }
        return end;
    }
}
