import java.nio.file.*;
import java.io.Console;
import java.io.IOException;
class FileHelper {
    static String[] getLines(String path) {
        try {
            return Files.readAllLines(Paths.get(path)).toArray(String[]::new);
        }
        catch(IOException e) {
            System.err.println("Error reading file " + path + ": " + e);
            return new String[]{"Error reading file " + path + ": " + e};
        }
    }
}
interface Query{
    boolean matches(String s);
    String getType();
    String getStr();
    int getNum();
    String getSecondType();
    
}
class lengthquery implements Query{
    int num;
    lengthquery(int num)
    {
        this.num = num;
    }
    public boolean matches(String s)
    {
        if(s.contains("length="))
        {
            return true;
        }
        return false;
    } public String getType(){
        return "length";
    }
    public int getNum(){
        return num;
    }
    public String getStr(){
        return null;
    }
    public String getSecondType(){
        return null;
    }
    
}
class greaterquery implements Query{
    int num;
    greaterquery(int num)
    {
        this.num = num;
    }
    public boolean matches(String s)
    {
        if(s.contains("greater="))
        {
            return true;
        }
        return false;
    }
    public String getType(){
        return "greater";
    }
    public int getNum(){
        return this.num;
    }
    public String getStr(){
        return null;
    }
    public String getSecondType(){
        return null;
    }
}
class lessquery implements Query{
    int num;
    
    lessquery(int num)
    {
        this.num = num;
    }
    public boolean matches(String s)
    {
        if(s.contains("less="))
        {
            return true;
        }
        return false;
    }
    public String getType(){
        return "less";
    }
    public int getNum(){
        return num;
    }
    public String getStr(){
        return null;
    }
    public String getSecondType(){
        return null;
    } 
}
class ContainsQuery implements Query{
    String que;
    public ContainsQuery(String que)
    {
        this.que = que.substring(1, que.length()-1);
    }
    public boolean matches(String s)
    {
        boolean actual = s.contains(que);
        return actual;
    }
    public String getType(){
        return "contains";
    }
    public int getNum(){
        return 0;
    }
    public String getStr(){
        return que;
    }
    public String getSecondType(){
        return null;
    }
}

class startsquery implements Query{
    String str;
    startsquery(String str)
    {
        this.str = str;
    }
    public boolean matches(String s)
    {
        if(s.contains("starts="))
        {
            return true;
        }
        return false;
    }
    public String getType(){
        return "starts";
    }
    public int getNum(){
        return 0;
    }
    public String getStr(){
        return str;
    }
    public String getSecondType(){
        return null;
    } 
    
}
class endsquery implements Query{
    String str;
    endsquery(String str)
    {
        this.str = str;
    }
    public boolean matches(String s)
    {
        if(s.contains("ends="))
        {
            return true;
        }
        return false;
    }
    public String getType(){
        return "ends";
    }
    public int getNum(){
        return 0;
    }
    public String getStr(){
        return str;
    }
    public String getSecondType(){
        return null;
    }
    
}
class notquery implements Query{
    Query query;
    notquery(Query query)
    {
        this.query = query;
    }
    public boolean matches(String s)
    {
        if(s.contains("not")==true)
        {
            return true;
        }
        return false;
    }
    
    public String getType(){
        return "not";
    }
    public int getNum(){
        return query.getNum();
    }
    public String getStr(){
        return query.getStr();
    }
    public String getSecondType(){
        return query.getType();
    }
    
}

class StringSearch{
    static Query readQuery(String q){
        if(q.contains("not"))
        {
            String str = q.substring(4, q.length()-1);
            Query query1 = readQuery(str);
            return new notquery(query1); 
        }
        if(q.contains("length")){
            int num = Integer.parseInt(q.substring(7, q.length()));
            return new lengthquery(num);
        }
        if(q.contains("greater"))
        {
            int num = Integer.parseInt(q.substring(8, q.length()));
            return new greaterquery(num);
        }
        if(q.contains("less"))
        {
            int num = Integer.parseInt(q.substring(5, q.length()));
            return new lessquery(num);
        }
        if(q.contains("contains"))
        {
            
            String str = q.substring(9, q.length());
            return new ContainsQuery(str);
        }
        if(q.contains("starts"))
        {
            String str = q.substring(8, q.length()-1);
            return new startsquery(str);
        }
        if(q.contains("ends"))
        {
            String str = q.substring(6, q.length()-1);
            return new endsquery(str);
        }
        return null;
             
    }
    static String[] helperForNotFunction(String type,String[] content,int num,String str){
        if(type.equals("length"))
        {
            String[] newCont = new String[content.length];
            for(int i=0;i<content.length;i++)
            {
                if(content[i]!=null){
                if(content[i].length()!=num)
                {
                    newCont[i] = content[i];
                }
                }
            }
            return newCont;
        }
        if(type.equals("greater"))
        {
            String[] newCont = new String[content.length];
            for(int i=0;i<content.length;i++)
            {
                if(content[i]!=null){
                if(content[i].length()<=num)
                {
                    newCont[i] = content[i];
                }}
            }
            return newCont;
        }
        if(type.equals("less"))
        {
            String[] newCont = new String[content.length];
            for(int i=0;i<content.length;i++)
            {
                if(content[i]!=null){
                if(content[i].length()>=num)
                {
                    newCont[i] = content[i];
                }}
            }
            return newCont;
        }
        if(type.equals("contains"))
        {
            String[] newCont = new String[content.length];
            for(int i=0;i<content.length;i++)
            {
                if(content[i]!=null){
                if(!content[i].contains(str))
                {
                    newCont[i] = content[i];
                }
            }
            }
            return newCont;
        }
        if(type.equals("starts"))
        {
            String[] newCont = new String[content.length];
            for(int i=0;i<content.length;i++)
            {
                if(content[i]!=null){
                    if(!content[i].startsWith(str))
                    {
                        newCont[i] = content[i];
                    }
            }
            }
            return newCont;
        }
        if(type.equals("ends"))
        {
            String[] newCont = new String[content.length];
            for(int i=0;i<content.length;i++)
            {
                if(content[i]!=null){
                if(!content[i].endsWith(str))
                {
                    newCont[i] = content[i];
                }
            }
            }
            return newCont;
        }
        return null;
    }
    public static void main(String[]args)
    {
        if(args.length<=0)
        {
            return;
        }
        if(args.length==1)
        {
            String[] allContent = FileHelper.getLines(args[0]);
            for(int i=0;i<allContent.length;i++)
            {
                System.out.println(allContent[i]);
            }
        }
        
        if(args.length==2)
        {
            String[] allContent = FileHelper.getLines(args[0]);
            String[] que = args[1].split("&");
            Query[] queries = new Query[que.length];
            for(int i=0;i<que.length;i++)
            {
                queries[i] = readQuery(que[i]);
                
            }
            for(int j=0;j<queries.length;j++)
            {
                //length =
                if(queries[j].getType().equals("length")){
                    String [] newCont = new String[allContent.length];
                    for(int i =0;i<allContent.length;i++)
                    {
                        if(allContent[i].length()==queries[j].getNum()){
                            newCont[i] = allContent[i];
                        }
                    }
                    allContent = newCont;
                }
                //greater
                if(queries[j].getType().equals("greater")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i].length()>queries[j].getNum())
                        {
                            newCont[i] = allContent[i];
                        }
                    }
                    allContent = newCont;
                }
                //less
                if(queries[j].getType().equals("less")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i].length()<queries[j].getNum())
                        {
                            newCont[i] = allContent[i];
                        }
                    }
                    allContent = newCont;
                }
                //contains
                if(queries[j].getType().equals("contains")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i].contains(queries[j].getStr()))
                        {
                            newCont[i] = allContent[i];
                        }
                    }
                    allContent = newCont;
                }
                //starts
                if(queries[j].getType().equals("starts")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i].startsWith(queries[j].getStr()))
                        {
                            newCont[i] = allContent[i];
                        }
                    }
                    allContent = newCont;
                }
                //ends
                if(queries[j].getType().equals("ends")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i].endsWith(queries[j].getStr()))
                        {
                            newCont[i] = allContent[i];
                        }
                    }
                    allContent = newCont;
                }
                //not
                if(queries[j].getType().equals("not")){
                    
                    allContent=helperForNotFunction(queries[j].getSecondType(), allContent, queries[j].getNum(), queries[j].getStr());
                }
            }
            for(int i=0;i<allContent.length;i++)
            {
                if(allContent[i]!=null)
                    System.out.println(allContent[i]);
                
            }
        }
        
    }
}


