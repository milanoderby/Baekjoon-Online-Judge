import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int K = Integer.parseInt(tokenizer.nextToken());
        bw.append(solution2(N, K) + " ");
        bw.flush();
    }

    public static int solution2(int N, int K) {
        long minimumTotalCountOfDigitNumber = 1;
        long maximumTotalCountOfDigitNumber = 1;
        int totalCountOfNumber = 1;
        int digit = 1;

        int answer = 0;
        while (true) {
            long countOfNumber = (long) Math.pow(10, digit) - (long) Math.pow(10, digit - 1);
            maximumTotalCountOfDigitNumber += digit * countOfNumber;

            if (minimumTotalCountOfDigitNumber <= K && K < maximumTotalCountOfDigitNumber) {
                long offset = K - minimumTotalCountOfDigitNumber;
                long nThNumber = offset / digit;
                long nThDigit = offset % digit;

                long number = totalCountOfNumber + nThNumber;
                if (number > N) {
                    answer = -1;
                    break;
                }
                String numberStr = Long.toString(number);
                answer = numberStr.charAt((int) nThDigit) - '0';
                break;
            }

            totalCountOfNumber += countOfNumber;
            minimumTotalCountOfDigitNumber = maximumTotalCountOfDigitNumber;
            digit++;
        }

        return answer;
    }
}