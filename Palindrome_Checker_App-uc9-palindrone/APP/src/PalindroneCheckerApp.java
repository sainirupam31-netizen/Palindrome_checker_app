public class PalindroneCheckerApp {

    public static void main(String[] args) {

        // Define the input string
        String input = "madam";

        // Call recursive function
        boolean isPalindrome = check(input, 0, input.length() - 1);

        // Display result
        System.out.println("Input : " + input);
        System.out.println("Is Palindrome? : " + isPalindrome);
    }

    /**
     * Recursively checks whether a string is palindrome
     * @param s     Input string
     * @param start Starting index
     * @param end   Ending index
     * @return true if palindrome, otherwise false
     */
    private static boolean check(String s, int start, int end) {

        // Base condition: all characters checked
        if (start >= end) {
            return true;
        }

        // If mismatch found
        if (s.charAt(start) != s.charAt(end)) {
            return false;
        }

        // Recursive call
        return check(s, start + 1, end - 1);
    }
}