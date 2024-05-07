package orishop.util;

public class InputSanitizer {
	// Method to sanitize input by removing CR and LF characters
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // Replace both \r and \n with an empty string
        return input.replaceAll("[\\r\\n]", "");
    }

    // Method to sanitize input by removing non-alphanumeric characters
    public static String sanitizeNonAlphaNumeric(String input) {
        if (input == null) {
            return null;
        }
        // Replace all non-alphanumeric characters with an empty string
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }
}
