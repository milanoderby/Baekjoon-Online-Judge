import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;
import org.w3c.dom.css.Rect;

public class Main {
    private static int N;
    private static int[] rectangleHeight;
    private static int[] rectangleWidth;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                N = Integer.parseInt(tokenizer.nextToken());

                if (N == 0) {
                    break;
                }

                rectangleHeight = new int[N];
                rectangleWidth = new int[N];
                for (int i = 0; i < N; i++) {
                    rectangleHeight[i] = Integer.parseInt(tokenizer.nextToken());
                }

                getWidth();

                long maxArea = 0;
                for (int i = 0; i < N; i++) {
                    long rectangleArea = (long)rectangleHeight[i] * rectangleWidth[i];
                    if(maxArea < rectangleArea){
                        maxArea = rectangleArea;
                    }
                }
                System.out.println(maxArea);
            }

        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void getWidth() {
        getLeftWidth();
        getRightWidth();

        for (int i = 0; i < N; i++) {
            rectangleWidth[i] -= 1;
        }
    }

    private static void getLeftWidth() {
        Stack<Rectangle> rectangles = new Stack<>();
        for (int i = 0; i < N; i++) {
            while(!rectangles.isEmpty()){
                if(rectangles.peek().height < rectangleHeight[i]) {
                    break;
                }
                rectangles.pop();
            }

            if(rectangles.isEmpty()){
                rectangleWidth[i] += i + 1;
            } else{
                rectangleWidth[i] += i - rectangles.peek().index;
            }
            rectangles.push(new Rectangle(i, rectangleHeight[i]));
        }
    }

    private static void getRightWidth() {
        Stack<Rectangle> rectangles = new Stack<>();
        for (int i = N - 1; i >= 0 ; i--) {
            while(!rectangles.isEmpty()){
                if(rectangles.peek().height < rectangleHeight[i]) {
                    break;
                }
                rectangles.pop();
            }

            if(rectangles.isEmpty()){
                rectangleWidth[i] += N - i;
            } else{
                rectangleWidth[i] += rectangles.peek().index - i;
            }
            rectangles.push(new Rectangle(i, rectangleHeight[i]));
        }
    }

    private static class Rectangle {
        int index;
        int height;

        public Rectangle(int index, int height) {
            this.index = index;
            this.height = height;
        }
    }
}
