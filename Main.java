import java.io.File;

public class Main {
    public static void main(String[] args) {
        String filePath = "/home/aniss/cs316/project_test/input.txt";
        File file = new File(filePath);

        System.out.println("Lexical Analysis:");
        LexicalAnalyzer.analyze(file);

        System.out.println("\nSyntax Analysis:");
        SyntaxAnalyzer.analyze(file);
    }
}
