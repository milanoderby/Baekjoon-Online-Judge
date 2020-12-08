import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static String string;
    private static String explosiveString;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            string = br.readLine();
            explosiveString = br.readLine();

            StringBuilder result = new StringBuilder();
            int lenOfExplosiveString = explosiveString.length();
            for (int i = 0; i < string.length(); i++) {
                result.append(string.charAt(i));
                if (result.length() >= lenOfExplosiveString){
                    int compareStartIndex = result.length() - lenOfExplosiveString;
                    if (result.substring(compareStartIndex).equals(explosiveString)) {
                        result.delete(compareStartIndex, compareStartIndex + lenOfExplosiveString);
                    }
                }
            }
            String answer = result.toString();
            if (answer.isEmpty()) {
                System.out.println("FRULA");
            }
            else {
                System.out.println(answer);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
