import java.io.*;
import java.util.Stack;

public class Syntax_Parser{

    private String input_path = "input.txt";
    private char[] input_char_list;
    private Stack<Character> stack = new Stack<>();

    private int[][] ppt =
            {      //+  ,  -  ,  *  ,  /  ,  (  ,  )  ,  i  ,  $
                    {3  ,  4  , -1  , -1  , -1  , -1  , -1  , -1},//A
                    {-1 , -1  ,  8  ,  9  , -1  , -1  , -1  , -1},//B
                    {1  ,  1  , -1  , -1  , -1  ,  2  , -1  ,  2},//C
                    {7  ,  7  ,  6  ,  6  , -1  ,  7  , -1  ,  7},//D
                    {-1 , -1  , -1  , -1  ,  0  , -1  ,  0  , -1},//E
                    {-1 , -1  , -1  , -1  , 10  , -1  , 11  , -1}//F
            };
    private String[] generations = {
            "E->TC",
            "C->AC",
            "C->ε",
            "A->+T",
            "A->-T",
            "T->FD",
            "D->BD",
            "D->ε",
            "B->*F",
            "B->/F",
            "F->(E)",
            "F->i"
    };

    private int getIndex(char ch){
        switch(ch){
            case 'A':return 0;
            case 'B':return 1;
            case 'C':return 2;
            case 'D':return 3;
            case 'E':return 4;
            case 'F':return 5;
            case '+':return 0;
            case '-':return 1;
            case '*':return 2;
            case '/':return 3;
            case '(':return 4;
            case ')':return 5;
            case 'i':return 6;
            case '$':return 7;
            default:return -1;
        }
    }
    public Syntax_Parser(){
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        print();
    }

    private boolean isTerminal(char ch){
        if((ch=='+')||(ch=='-')||(ch=='*')||(ch=='/')||(ch=='(')||(ch==')')||(ch=='i')||(ch=='$')){
            return true;
        }else {
            return false;
        }
    }

    private void initData() throws IOException {
        File file = new File(input_path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s = "";
        String temp;
        while ((temp = bufferedReader.readLine())!=null){
            s+=temp;
        }
        input_char_list = s.toCharArray();
        bufferedReader.close();

        stack.push('$');
        stack.push('E');
    }

    private void parser(){

    }


    private void print(){
        for(int i=0;i<input_char_list.length;i++){
            System.out.print(input_char_list[i]);
        }
    }

    public static void main(String args[]){
        Syntax_Parser syntax_parser = new Syntax_Parser();

    }





}
