
import java.util.*;

public class PalindroneCheckerApp {

    public static void main(String[] args) {

        // Define the input string
        String input = "civic";

        // Create Queue (FIFO)
        Queue<Character> queue = new LinkedList<>();

        // Create Stack (LIFO)
        Stack<Character> stack = new Stack<>();

        // Insert characters into both queue and stack
        for (char c : input.toCharArray()) {
            queue.add(c);     // enqueue
            stack.push(c);    // push
        }

        // Flag to track palindrome status
        boolean isPalindrome = true;

        // Compare dequeue and pop
        while (!queue.isEmpty()) {
            if (!queue.remove().equals(stack.pop())) {
                isPalindrome = false;
                break;
            }
        }

        // Output result
        System.out.println("Input : " + input);
        System.out.println("Is Palindrome? : " + isPalindrome);
    }
}
