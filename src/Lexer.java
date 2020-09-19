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

    public ArrayList<Token> getAllTokens(){
        //TODO: place your code here for lexing file

        ArrayList<Token> tokenList = new ArrayList<Token>();

        String strBuffer = buffer;
        while (strBuffer.length() > 0) {

            strBuffer = strBuffer.trim();
            Token token = getNextToken(strBuffer);
            tokenList.add(token);

            strBuffer = strBuffer.substring(token.value.length());
        }

        // Add last token
        Token token = new Token(EOFTOKEN, "-");
        tokenList.add(token);

        return tokenList;
    }

    public Token getNextToken(String strBuffer){

        strBuffer = strBuffer.trim();
        if (strBuffer.length() == 0){
            Token token = new Token(EOFTOKEN, "-");
            return token;
        }

        char ch = strBuffer.charAt(0);
        if (ch == '=') {
            Token token = new Token(ASSMTTOKEN, "=");
            return token;
        } else if (ch == '+') {
            Token token = new Token(PLUSTOKEN, "+");
            return token;
        }

        // find the break
        for (int i=0; i<strBuffer.length(); i++) {
            ch = strBuffer.charAt(i);
            if ((ch == ' ') || (ch == '=') || (ch == '+') || (ch == '\n')) {
                strBuffer = strBuffer.substring(0, i);
                break;
            }
        }

        ch = strBuffer.charAt(0);
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
            Token token = new Token(IDTOKEN, strBuffer);
            return token;
        } else {
            Token token = new Token(INTTOKEN, strBuffer);
            return token;
        }
    }

    private String getIdentifier(Token token){
        return token.value;
    }

    private int getInteger(Token token){
        return Integer.parseInt(token.value);
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
        // System.out.println(lexer.buffer);
        // here is where you'll call getAllTokens

        ArrayList<Token> tokenList = new ArrayList<Token>();
        tokenList = lexer.getAllTokens();

        for (int i=0; i<tokenList.size(); i++) {
            System.out.println(tokenList.get(i).toString());
        }
    }
}
	