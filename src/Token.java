public class Token {
    public String type;
    public String value;

    public Token(String type, String value) {
        this.type=type;
        this.value=value;
        //System.out.println(value);
    }

    public String toString(){

        return type+" "+value;
    }


}

