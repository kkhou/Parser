import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Parser<idTable> {
    //TODO implement Parser class here
    public Parser(){
        lexer = new Lexer("test.txt");
        tokenList = lexer.getAllTokens();
    }

    // Data members
    public Lexer lexer;
    public ArrayList<Token> tokenList;
    public IdTable idTable;
    private Integer index = 0;

    // member methods

    public Token nextToken(){
        Token token = tokenList.get(index);
        index = index+1;
        return token;
    }

    public int putToken() {
        index = index - 1;
        if (index < 0){
            index = 0;
        }
        return index;
    }

    private boolean parseID(){
        // Expect an ID TOKEN
        Token token = nextToken();

        if (token.type.equals(Lexer.IDTOKEN)) {
            return true;
        } else{
            System.out.println("Expecting identifier!");
            return false;
        }
    }
    private boolean parseAssignOp(){
        // Expect an ASSGN TOKEN
        Token token = nextToken();

        if (token.type.equals(Lexer.ASSMTTOKEN)) {
            return true;
        } else{
            System.out.println("Expecting assignment operator!");
            return false;
        }
    }

    public boolean parseAssignment() {

        while (index < tokenList.size()) {
            if (parseID() != true){
                return false;
            }
            if (parseAssignOp() != true){
                return false;
            }
            if (parseExpression() != true){
                return false;
            }
        }
        return true;

    }

    private boolean parseExpression(){
        // Expect INTTOKEN or IDTOKEN
        Token token = nextToken();
        if ((token.type.equals(Lexer.INTTOKEN) != true) && (token.type.equals(Lexer.IDTOKEN) != true)){
            System.out.println("Expecting identifier or integer!");
            return false;
        }

        // Expect IDTOKEN or PLUSTOKEN
        token = nextToken();
        if (token.type == Lexer.PLUSTOKEN) {
            return parseExpression();
        } else if (token.type == Lexer.IDTOKEN) {
            //current expression has ended. Back up one token and leave
            putToken();
            return true;
        }
    }

    public boolean parseProgram() {

        index = 0;
        Token token = nextToken();
        while (token.type != Lexer.EOFTOKEN ){
            if (parseAssignment() != true) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i=0; i<tokenList.size(); i++) {
            str.append("Token " + i + " : " + tokenList.get(i).toString() + "\n");
        }

        str.append(idTable.toString() + "\n");
        return str.toString();
    }

    public static void main(String[] args) {

        // Parser will decode the file to a TokenArray
        Parser pp = new Parser();

        // Print out all tokens first
        Token token = pp.nextToken();
        while (token != null){
            System.out.println("Token " + ":" + token.toString());
            token = pp.nextToken();
        }

        //TODO: add a token to idTable if it is before an identifier
        //for(int i=0; i < pp.tokenList.size(); i++){
        //    putToken();
        //}

        idTable = new IdTable();
        token = pp.nextToken();
        while (token != null){
            if (token.type == Lexer.ASSMTTOKEN) {
                putToken();
                putToken();
                token = nextToken();
                if (token.type == Lexer.IDTOKEN) {
                    idTable.add(token.value);
                }
            }
            token = nextToken();
            token = nextToken();
        }

        idTable.toString();

        //parseProgram();
    }

}

