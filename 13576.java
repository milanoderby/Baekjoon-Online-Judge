import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Main {
    private static String S;
    private static int[] failFunction, myGroup, lcp;
    private static List<Integer> suffixArray;
    private static int d;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            S = br.readLine();

            failFunction = new int[S.length()];
            suffixArray = new ArrayList<>();
            myGroup = new int[S.length()];
            for (int i = 0; i < S.length(); i++) {
                suffixArray.add(i);
                myGroup[i] = S.charAt(i);
            }

            makeFailFunction();
            constructSa();
            constructLCP();

            List<SubString> answer = new ArrayList<>();
            answer.add(new SubString(S.length(), 1));

            int maxSubStringLength = failFunction[S.length() - 1];
            int K = 1;
            while (maxSubStringLength != 0) {
                int count = 0;
                for (int i = 1; i < S.length() - 1; i++) {
                    if (failFunction[i] == maxSubStringLength) {
                        count++;
                    }
                }
                answer.add(new SubString(maxSubStringLength, count + 2));
                maxSubStringLength = failFunction[maxSubStringLength - 1];
                K++;
            }
            Collections.sort(answer, (o1, o2) ->
                o1.length - o2.length
            );

            System.out.println(K);
            for (SubString subString : answer) {
                System.out.println(subString.length + " " + subString.count);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void makeFailFunction () {
        for (int i = 1, j = 0; i + j < S.length(); ) {
            if (S.charAt(i + j) == S.charAt(j)) {
                failFunction[i + j] = j + 1;
                j++;
            } else {
                if (j == 0) {
                    failFunction[i + j] = 0;
                    i++;
                } else {
                    i += j - failFunction[j - 1];
                    j = failFunction[j - 1];
                }
            }
        }
    }

    private static class SubString {
        int length;
        int count;

        public SubString (int length, int count) {
            this.length = length;
            this.count = count;
        }
    }

    private static void constructSa () {
        for (d = 1; ; d *= 2) {
            Collections.sort(suffixArray, new SuffixComparator());

            int groupNum = 0;
            int[] tempGroup = new int[S.length()];
            tempGroup[0] = groupNum;
            for (int i = 0; i < suffixArray.size() - 1; i++) {
                if (Objects.compare(suffixArray.get(i), suffixArray.get(i+1), new SuffixComparator()) != 0) {
                    groupNum++;
                }
                tempGroup[i + 1] = groupNum;
            }

            for (int i = 0; i < S.length(); i++) {
                myGroup[suffixArray.get(i)] = tempGroup[i];
            }

            if (groupNum == S.length() - 1) {
                break;
            }
        }
    }

    private static class SuffixComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer i, Integer j) {
            if (myGroup[i] != myGroup[j]) {
                return myGroup[i] - myGroup[j];
            }

            i += d;
            j += d;
            if (i < S.length() && j < S.length()) {
                return myGroup[i] - myGroup[j];
            }
            return j - i;
        }
    }

    private static void constructLCP() {
        for(int i=0, k=0; i<S.length(); i++, k=Math.max(k-1, 0)){
            // 마지막 접미사(길이 1)면 아무것도 안 함
            if(myGroup[i] == S.length()-1) continue;

            // 바로 아래 인접한 접미사와 비교하여 앞에서부터 몇 개의 글자가 일치하는지 센다
            for(int j=suffixArray.get(myGroup[i]+1); S.charAt(i+k)==S.charAt(j+k); k++);
            lcp[myGroup[i]] = k;
        }
    }
}