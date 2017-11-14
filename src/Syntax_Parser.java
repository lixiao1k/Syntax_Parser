import java.io.*;
import java.util.Stack;

public class Syntax_Parser{

    private String input_path = "input.txt";//表达式输入文件

    private char[] input_char_list;//存放输入表达式的字符
    private Stack<Character> stack = new Stack<>();//文法符号序列栈

    private int[][] ppt =
            {      //+  ,  -  ,  *  ,  /  ,  (  ,  )  ,  i  ,  $
                    {3  ,  4  , -1  , -1  , -1  , -1  , -1  , -1},//A
                    {-1 , -1  ,  8  ,  9  , -1  , -1  , -1  , -1},//B
                    {1  ,  1  , -1  , -1  , -1  ,  2  , -1  ,  2},//C
                    {7  ,  7  ,  6  ,  6  , -1  ,  7  , -1  ,  7},//D
                    {-1 , -1  , -1  , -1  ,  0  , -1  ,  0  , -1},//E
                    {-1 , -1  , -1  , -1  , 10  , -1  , 11  , -1},//F
                    {-1 , -1  , -1  , -1  ,  5  , -1  ,  5  , -1}//T
            };//LL(1)语法的预测分析表

    private String[] generations = {
            "E->GC",
            "C->AC",
            "C->ε",
            "A->+G",
            "A->-G",
            "T->FD",
            "D->BD",
            "D->ε",
            "B->*F",
            "B->/F",
            "F->(E)",
            "F->i"
    };//维持产生式表

    private int getIndex(char ch){
        switch(ch){
            case 'A':return 0;
            case 'B':return 1;
            case 'C':return 2;
            case 'D':return 3;
            case 'E':return 4;
            case 'F':return 5;
            case 'G':return 6;
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
    }

    private boolean isTerminal(char ch){
        if((ch=='+')||(ch=='-')||(ch=='*')||(ch=='/')||(ch=='(')||(ch==')')||(ch=='i')||(ch=='$')){
            return true;
        }else {
            return false;
        }
    }

    private void initData() throws IOException {
        //读入输入文件，初始化输入字符列表
        File file = new File(input_path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s = "";
        String temp;
        while ((temp = bufferedReader.readLine())!=null){
            s+=temp;
        }
        s =  s + '$';//输入字符串尾部加结束符
        input_char_list = s.toCharArray();
        bufferedReader.close();
        //文法符号栈在初始化时栈底为'$'，在上一层为开始符'E'
        stack.push('$');
        stack.push('E');
    }

    public void parser(){

        int in_ptr = 0;//标记输入位置的指针
        char stack_head = stack.pop();//获取栈顶文法符号
        char in_ch = input_char_list[in_ptr];//获取输入的终结符
        boolean suceess = true;

        while (!(in_ch=='$' && stack_head == '$')){
            if(in_ch == stack_head){
                System.out.println("匹配："+in_ch);
                in_ch = input_char_list[++in_ptr];
                stack_head = stack.pop();
            }else if(isTerminal(stack_head)){
                error();
                suceess = false;
                break;
            }else if(ppt[getIndex(stack_head)][getIndex(in_ch)] == -1){
                error();
                suceess = false;
                break;
            }else if(ppt[getIndex(stack_head)][getIndex(in_ch)]!= -1){
                String generation_string = generations[ppt[getIndex(stack_head)][getIndex(in_ch)]];
                System.out.println(generation_string);
                char[] temp = generation_string.toCharArray();
                int length = temp.length;
                for(int i = length-1;i>2;i--){
                    if(temp[i]!='ε'){//如果时ε产生式，将产生式左部文法符弹出栈，即这里不做任何动作。
                        stack.push(temp[i]);
                    }
                }
                stack_head = stack.pop();
            }
        }
        if(suceess){
            System.out.println("解析成功！");
        }else {
            System.out.println("解析失败！");
        }
    }

    private void error(){
        System.out.println("error");
    }

    public static void main(String args[]){
        Syntax_Parser syntax_parser = new Syntax_Parser();
        syntax_parser.parser();
    }
}
