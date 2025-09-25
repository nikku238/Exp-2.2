import java.util.ArrayList;
import java.util.Scanner;

public class SumUsingAutoboxing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter numbers (comma-separated): ");
        String input = sc.nextLine();

        // Split input string by commas
        String[] numbers = input.split(",");

        // ArrayList with autoboxing
        ArrayList<Integer> list = new ArrayList<>();

        // Parse strings to Integer using parseInt (autoboxing happens automatically)
        for (String num : numbers) {
            list.add(Integer.parseInt(num.trim())); 
        }

        int sum = 0;
        // Unboxing happens here automatically when retrieving values
        for (Integer val : list) {
            sum += val; 
        }

        System.out.println("Sum of numbers = " + sum);
        sc.close();
    }
}
