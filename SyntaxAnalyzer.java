import java.io.*;
import java.util.ArrayList;

// Class responsible for performing syntax analysis on a file
public class SyntaxAnalyzer {
    public static void analyze(File inputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            // Boolean flags to keep track of current context in the code
            boolean insideMultiLineComment = false;
            boolean insideIfStatement = false;
            // Variables to store currently parsed multi-line comment or if statement
            StringBuilder currentIfStatement = new StringBuilder();
            StringBuilder currentComment = new StringBuilder();
            // Result lists
            ArrayList<String> ifStatements = new ArrayList<>();
            ArrayList<String> singleLineComments = new ArrayList<>();
            ArrayList<String> multiLineComments = new ArrayList<>();

            // Continue reading lines until end of file
            while ((line = reader.readLine()) != null) {
                char[] chars = line.toCharArray();

                for (int i = 0; i < chars.length; i++) {
                    // Case when not inside multi-line comment or if statement
                    if (!insideMultiLineComment && !insideIfStatement) {
                        // Check for single-line comments
                        if (i < chars.length - 1 && chars[i] == '/' && chars[i+1] == '/') {
                            singleLineComments.add(line.substring(i));
                            break;  // Skip the rest of the line
                        }

                        // Check for the start of an if statement
                        if (i < chars.length - 2 && chars[i] == 'i' && chars[i+1] == 'f' && (chars[i+2] == ' ' || chars[i+2] == '(')) {
                            insideIfStatement = true;
                            currentIfStatement.append("if");
                            i += 1;  // Skip the next character
                        }

                        // Check for the start of a multi-line comment
                        if (i < chars.length - 1 && chars[i] == '/' && chars[i+1] == '*') {
                            insideMultiLineComment = true;
                            currentComment.append("/*");
                            i++;  // Skip the next character
                        }
                    } else if (insideMultiLineComment) {
                        // If inside a multi-line comment
                        currentComment.append(chars[i]);

                        // Check for the end of the multi-line comment
                        if (i > 0 && chars[i-1] == '*' && chars[i] == '/') {
                            insideMultiLineComment = false;
                            multiLineComments.add(currentComment.toString());
                            currentComment = new StringBuilder();
                        }
                    } else if (insideIfStatement) {
                        // If inside an if statement
                        currentIfStatement.append(chars[i]);

                        // Check for the end of the if statement
                        if (chars[i] == '{') {
                            int openBracesCount = 1;
                            while (++i < chars.length && openBracesCount > 0) {
                                currentIfStatement.append(chars[i]);
                                if (chars[i] == '{') openBracesCount++;
                                if (chars[i] == '}') openBracesCount--;
                            }
                            if (openBracesCount == 0) {
                                insideIfStatement = false;
                                ifStatements.add(currentIfStatement.toString().trim());
                                currentIfStatement = new StringBuilder();
                            }
                        }
                    }
                }
            }
            // Print the results
            printResults("IF STATEMENTS", ifStatements);
            printResults("SINGLE LINE COMMENTS", singleLineComments);
            printResults("MULTI LINE COMMENTS", multiLineComments);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Prints the result of the analysis
    private static void printResults(String type, ArrayList<String> results) {
        if (results.isEmpty()) {
            System.out.println("No valid '" + type + "' found.");
        } else {
            System.out.println("Valid '" + type + "':");
            for (String result : results) {
                System.out.println(result);
            }
        }
    }
}


