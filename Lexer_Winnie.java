import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Class to build an array of Tokens from an input file
 * @author wolberd
 * @see Token
 * @see Parser
 */
public class Lexer {

    String buffer;
    int index = 0;
    public static final String INTTOKEN="INT";
    public static final String IDTOKEN="ID";
    public static final String ASSMTTOKEN="ASSMT";
    public static final String PLUSTOKEN="PLUS";
    public static final String EOFTOKEN="EOF";

    /**
     * call getInput to get the file data into our buffer
     * @param fileName the file we open
     */
    public Lexer(String fileName) {

        getInput(fileName);

        // 1:
    }

    /**
     * Reads given file into the data member buffer
     * @param fileName name of file to parse
    */
    private void getInput(String fileName)  {
        try {
            Path filePath = Paths.get(fileName);
            byte[] allBytes = Files.readAllBytes(filePath);
            buffer = new String (allBytes);
        } catch (IOException e) {
            System.out.println ("You did not enter a valid file name in the run arguments.");
            System.out.println ("Please enter a string to be parsed:");
            Scanner scanner = new Scanner(System.in);
            buffer=scanner.nextLine();
        }
    }

    /**
     * Return all the token in the file
     * @return ArrayList of Token
     */

    private String recogType (String value) {
        char c = value.charAt(0);
        if (c == '=') {
            return ASSMTTOKEN;
        } else if (c == '+') {
            return PLUSTOKEN;
        } else if (c >= '1' && c <= '9') {
            return INTTOKEN;
        } else {
            return IDTOKEN;
        }
    }

    public ArrayList<Token> getAllTokens(){
        //TODO: place your code here for lexing file

        // Kevin 4:
        ArrayList<Token> tokenList = new ArrayList<Token>();

        int iStart = 0;
        for (int i = 0; i < buffer.length(); i++){
            char ch = buffer.charAt(i);
            // look for breaker
            if (i == buffer.length()-1 || ch == '\n' || ch == ' ' || ch == '+' || ch == '=') {
                String value = buffer.substring(iStart, i);
                value.trim();
                if (value.length() > 0){
                    Token token1 = new Token(recogType(value), value);
                    tokenList.add(token1);
                }
                // Record the operator
                if (c == '+' || c == '=') {
                    String value2 = buffer.substring(i, i + 1);
                    Token token2 = new Token(recogType(value2), value2);
                    tokenList.add(token2);
                } else {
                    String value = buffer.substring(i, i+1);
                    
                }
                iStart = i+1;
            }
        }
        // for EndofFile
        Token token = new Token(EOFTOKEN,"-");
        tokenList.add(token);

        return tokenList; // don't forget to change the return statement
    }


    public Token getNextToken(){
        // Kevin 1:
        return null;
    }

    private String getIdentifier(){
        // Kevin 2:
        return null;
    }

    private int getInteger(){
        // Kevin 3:
        return 0;
    }


    /**
     * Before your run this starter code
     * Select Run | Edit Configurations from the main menu.
     * In Program arguments add the name of file you want to test (e.g., test.txt)
     * @param args args[0]
     */
    public static void main(String[] args) {
        String fileName="";
        if (args.length==0) {
            System.out.println("You can test a different file by adding as an argument");
            System.out.println("See comment above main");
            System.out.println("For this run, test.txt used");
            fileName="test.txt";
        } else {

            fileName=args[0];
        }
        Lexer lexer = new Lexer(fileName);
        // just print out the text from the file
        System.out.println(lexer.buffer);
        // here is where you'll call getAllTokens

        ArrayList<Token> tokenList = new ArrayList<Token>();
        tokenList = lexer.getAllTokens();

        System.out.println("****");
        for (Token t : tokenList) {
            System.out.println(t.toString());
        }

        IdTable id = new IdTable();

    }
}
	