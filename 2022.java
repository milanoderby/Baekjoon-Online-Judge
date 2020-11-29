import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static double x, y;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            x = Double.parseDouble(tokenizer.nextToken());
            y = Double.parseDouble(tokenizer.nextToken());
            double h = Double.parseDouble(tokenizer.nextToken());

            double low = 0;
            double high = Math.min(x, y);

            double mid = (low + high) / 2;
            while(Math.abs(getHeight(mid) - h) >= Math.pow(10, -3)) {
                if(getHeight(mid) > h) {
                    low = mid;
                } else{
                    high = mid;
                }
                mid = (low + high) / 2;
            }
            System.out.println(mid);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static double getHeight(double distance){
        double h1 = Math.sqrt(Math.pow(x, 2) - Math.pow(distance, 2));
        double h2 = Math.sqrt(Math.pow(y, 2) - Math.pow(distance, 2));
        return h1 * h2 / (h1 + h2);
    }
}
