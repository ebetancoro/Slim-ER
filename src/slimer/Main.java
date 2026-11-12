package slimer;

public class Main {
    public static void main(String[] args) {
        String source = """
            fn double(x: Int) -> Int {
                x * 2
            }

            run {
                let result = double(10);
                print(result);
            }
            """;

        Lexer lexer = new Lexer(source);

        for (Token token : lexer.tokenize()) {
            System.out.println(token);
        }
    }
}
