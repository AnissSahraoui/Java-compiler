import java.io.*;
import java.util.StringTokenizer;

// Class responsible for performing lexical analysis on a file
public class LexicalAnalyzer {
    public static void analyze(File inputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            // Iterate over each line in the file
            while ((line = reader.readLine()) != null) {
                // Tokenize the line based on various operators and whitespace
                StringTokenizer tokenizer = new StringTokenizer(line, "+-*/()= \t\n\r\f", true);
                // Iterate over each token
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    // Classify and print each token based on its type
                    if (token.equals("=") || token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("(") || token.equals(")")) {
                        System.out.println("Next token is: OPERATOR Next lexeme is " + token);
                    } else if (token.equals("if") || token.equals("else")) {
                        System.out.println("Next token is: RESERVED_WORD Next lexeme is " + token);
                    } else if (isIdentifier(token)) {
                        System.out.println("Next token is: IDENTIFIER Next lexeme is " + token);
                    } else if (isInteger(token)) {
                        System.out.println("Next token is: INTEGER Next lexeme is " + token);
                    } else if (token.trim().isEmpty()) {
                        // Skip whitespaces
                    } else {
                        System.out.println("Next token is: UNKNOWN Next lexeme is " + token);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Checks if a given string is a valid Java identifier
    private static boolean isIdentifier(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        char[] chars = token.toCharArray();
        // Java identifiers must start with a letter, underscore, or dollar sign
        if (!Character.isLetter(chars[0]) && chars[0] != '_' && chars[0] != '$') {
            return false;
        }
        // Remaining characters can also be digits
        for (int i = 1; i < chars.length; i++) {
            if (!Character.isLetterOrDigit(chars[i]) && chars[i] != '_' && chars[i] != '$') {
                return false;
            }
        }
        return true;
    }

    // Checks if a given string is an integer
    private static boolean isInteger(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        // Checks if all characters are digits
        for (char c : token.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}


