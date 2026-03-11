public class PalindroneCheckerApp {

    public static void main(String[] args) {

        String input = "racecar";

        // Create service object
        PalindromeService service = new PalindromeService();

        // Call service method
        boolean isPalindrome = service.checkPalindrome(input);

        // Display result
        System.out.println("Input : " + input);
        System.out.println("Is Palindrome? : " + isPalindrome);
    }
}

/**
 * Service class that contains palindrome logic
 */
class PalindromeService {

    /**
     * Checks whether the given string is a palindrome
     * @param input input string
     * @return true if palindrome, false otherwise
     */
    public boolean checkPalindrome(String input) {

        int start = 0;
        int end = input.length() - 1;

        // Compare characters moving inward
        while (start < end) {

            if (input.charAt(start) != input.charAt(end)) {
                return false;
            }

            start++;
            end--;
        }

        return true;
    }
}