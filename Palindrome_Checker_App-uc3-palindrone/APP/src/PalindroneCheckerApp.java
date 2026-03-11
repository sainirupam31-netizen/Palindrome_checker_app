
public class PalindroneCheckerApp {

    public static void main(String[] args) {

        String input = "madam";
        String reversed = "";

        // Iterate from the last character to the first
        for (int i = input.length() - 1; i >= 0; i--) {
            reversed = reversed + input.charAt(i);
        }

        System.out.println("Original String : " + input);
        System.out.println("Reversed String : " + reversed);

        if (input.equals(reversed)) {
            System.out.println("Result : It is a Palindrome");
        } else {
            System.out.println("Result : It is NOT a Palindrome");
        }
    }
}