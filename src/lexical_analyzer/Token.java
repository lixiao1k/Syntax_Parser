package lexical_analyzer;

public class Token {
    private String name;
    private String value;
    public Token(String name,String value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public String getValue(){
        return this.value;
    }
}
