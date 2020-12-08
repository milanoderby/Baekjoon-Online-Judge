import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    private static int[] cardAbility;
    private static int[] reverseCardAbility;
    private static int[] leftExtentOfMaxAbility, rightExtentOfMaxAbility;
    private static int[] leftExtentOfMinAbility, rightExtentOfMinAbility;

    private static Stack<Card> maxAbilityCard, minAbilityCard;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());
            cardAbility = new int[N];
            reverseCardAbility = new int[N];
            leftExtentOfMaxAbility = new int[N];
            rightExtentOfMaxAbility = new int[N];

            leftExtentOfMinAbility = new int[N];
            rightExtentOfMinAbility = new int[N];

            maxAbilityCard = new Stack<>();
            minAbilityCard = new Stack<>();
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                cardAbility[i] = Integer.parseInt(tokenizer.nextToken());
                reverseCardAbility[N - 1 - i] = cardAbility[i];
            }
            System.out.println(getMaxAbility() - getMinAbility());
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static long getMaxAbility() {
        for (int i = 0; i < N; i++) {
            while (!maxAbilityCard.isEmpty()) {
                if (maxAbilityCard.peek().ability >= cardAbility[i]) {
                    break;
                }
                maxAbilityCard.pop();
            }

            if (maxAbilityCard.isEmpty()) {
                leftExtentOfMaxAbility[i] = i + 1;
            } else {
                leftExtentOfMaxAbility[i] = Math.abs(maxAbilityCard.peek().index - i);
            }
            maxAbilityCard.push(new Card(i, cardAbility[i]));
        }

        maxAbilityCard = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!maxAbilityCard.isEmpty()) {
                if (maxAbilityCard.peek().ability > reverseCardAbility[i]) {
                    break;
                }
                maxAbilityCard.pop();
            }

            if (maxAbilityCard.isEmpty()) {
                rightExtentOfMaxAbility[N - 1 - i] = i + 1;
            } else {
                rightExtentOfMaxAbility[N - 1 - i] = Math.abs(maxAbilityCard.peek().index - i);
            }
            maxAbilityCard.push(new Card(i, reverseCardAbility[i]));
        }

        long answer = 0;
        for (int i = 0; i < N; i++) {
            answer += (long)leftExtentOfMaxAbility[i] * rightExtentOfMaxAbility[i] * cardAbility[i];
        }
        return answer;
    }

    private static long getMinAbility() {
        for (int i = 0; i < N; i++) {
            while (!minAbilityCard.isEmpty()) {
                if (minAbilityCard.peek().ability <= cardAbility[i]) {
                    break;
                }
                minAbilityCard.pop();
            }

            if (minAbilityCard.isEmpty()) {
                leftExtentOfMinAbility[i] = i + 1;
            } else {
                leftExtentOfMinAbility[i] = Math.abs(minAbilityCard.peek().index - i);
            }
            minAbilityCard.push(new Card(i, cardAbility[i]));
        }

        minAbilityCard = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!minAbilityCard.isEmpty()) {
                if (minAbilityCard.peek().ability < reverseCardAbility[i]) {
                    break;
                }
                minAbilityCard.pop();
            }

            if (minAbilityCard.isEmpty()) {
                rightExtentOfMinAbility[N - 1 - i] = i + 1;
            } else {
                rightExtentOfMinAbility[N - 1 - i] = Math.abs(minAbilityCard.peek().index - i);
            }
            minAbilityCard.push(new Card(i, reverseCardAbility[i]));
        }

        long answer = 0;
        for (int i = 0; i < N; i++) {
            answer += (long)leftExtentOfMinAbility[i] * rightExtentOfMinAbility[i] * cardAbility[i];
        }
        return answer;
    }

    private static class Card {
        int index, ability;
        public Card (int index, int ability) {
            this.index = index;
            this.ability = ability;
        }
    }
}
