import java.nio.file.*;
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
interface Transform{
    String transform(String s);
}
class upperTrans implements Transform{
    upperTrans(){}
    @Override
    public String transform(String s) {
        return s.toUpperCase();
    }
    
}
class lowerTrans implements Transform{
    lowerTrans(){}
    @Override
    public String transform(String s) {
        return s.toLowerCase();
    }
}
class firstTrans implements Transform{
    int num;
    firstTrans(int num)
    {
        this.num = num;
    }
    @Override
    public String transform(String s) {
        if(s.length()<=num)
        {
            return s;
        }
        return s.substring(0,num);
    }
}
class lastTrans implements Transform{
    int num;
    lastTrans(int num){
        this.num = num;
    }
    @Override
    public String transform(String s) {
        if(s.length()<=num)
        {
            return s;
        }
        return s.substring(s.length()-num, s.length());
    }
}
class replaceTrans implements Transform{
    String beingreplacedstr;
    String replaceStr;
    replaceTrans(String beingreplString,String replaString)
    {
        this.beingreplacedstr = beingreplString;
        this.replaceStr = replaString;
    }
    @Override
    public String transform(String s) {
        return s.replace(beingreplacedstr, replaceStr);
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
        this.str = str.substring(1,str.length()-1);
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
        if(str==null){
        return null;}
        
        return this.str;
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
            String str = q.substring(7, q.length());
            return new startsquery(str);
        }
        if(q.contains("ends"))
        {
            String str = q.substring(6, q.length()-1);
            return new endsquery(str);
        }
        return null;
             
    }
    static Transform readTransform(String s)
    {
        if(s.contains("upper"))
        {
            return new upperTrans();
        }
        if(s.contains("lower"))
        {
            return new lowerTrans();
        }
        if(s.contains("first="))
        {
            return new firstTrans(Integer.parseInt(s.substring(6, s.length())));
        }
        if(s.contains("last="))
        {
            return new lastTrans(Integer.parseInt(s.substring(5, s.length())));
        }
        if(s.contains("replace="))
        {
            String[] str = s.split(";");
            String s1 = str[0].substring(9,str[0].length()-1);
            String s2 = str[1].substring(1,str[1].length()-1);
            return new replaceTrans(s1, s2);
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
    static boolean matchesAll(Query[]qs,String s){
        for(int i=0;i<qs.length;i++)
        {
            if(qs[i].matches(s)!=true)
            {
                return false;
            }
        }
        return true;
    }
    static String applyAll(Transform[]ts,String s){
        String str = s;
        for(int i=0;i<ts.length;i++){
            str = ts[i].transform(str);
        }
        return str;
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
                    {   if(allContent[i]!=null){
                        if(allContent[i].length()==queries[j].getNum()){
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //greater
                if(queries[j].getType().equals("greater")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i]!=null){
                        if(allContent[i].length()>queries[j].getNum())
                        {
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //less
                if(queries[j].getType().equals("less")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i]!=null)
                        {
                        if(allContent[i].length()<queries[j].getNum())
                        {
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //contains
                if(queries[j].getType().equals("contains")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i]!=null)
                        {
                        if(allContent[i].contains(queries[j].getStr()))
                        {
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //starts
                if(queries[j].getType().equals("starts")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i]!=null){
                        if(allContent[i].contains(queries[j].getStr()))
                        {
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //ends
                if(queries[j].getType().equals("ends")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i]!=null){
                        if(allContent[i].endsWith(queries[j].getStr()))
                        {
                            newCont[i] = allContent[i];
                        }
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
        if(args.length==3)
        {
            String[] allContent = FileHelper.getLines(args[0]);
            String[] que = args[1].split("&");
            String[]trans = args[2].split("&");
            Query[] queries = new Query[que.length];
            Transform[] tran = new Transform[trans.length];
            for(int i=0;i<que.length;i++)
            {
                queries[i] = readQuery(que[i]);
            }
            for(int i=0;i<trans.length;i++)
            {
                tran[i] = readTransform(trans[i]);
            }
            for(int j=0;j<queries.length;j++)
            {
                //length =
                if(queries[j].getType().equals("length")){
                    String [] newCont = new String[allContent.length];
                    for(int i =0;i<allContent.length;i++)
                    {
                        if(allContent[i]!=null){
                        if(allContent[i].length()==queries[j].getNum()){
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //greater
                else if(queries[j].getType().equals("greater")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i]!=null){
                        if(allContent[i].length()>queries[j].getNum())
                        {
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //less
                else if(queries[j].getType().equals("less")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i]!=null){
                        if(allContent[i].length()<queries[j].getNum())
                        {
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //contains
                else if(queries[j].getType().equals("contains")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i]!=null){
                        if(allContent[i].contains(queries[j].getStr())==true)
                        {
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //starts
                else if(queries[j].getType().equals("starts")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i]!=null){
                        if(allContent[i].startsWith(queries[j].getStr()))
                        {
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //ends
                else if(queries[j].getType().equals("ends")){
                    String [] newCont = new String[allContent.length];
                    for(int i=0;i<allContent.length;i++){
                        if(allContent[i]!=null){
                        if(allContent[i].endsWith(queries[j].getStr()))
                        {
                            newCont[i] = allContent[i];
                        }
                        }
                    }
                    allContent = newCont;
                }
                //not
                else{
                    allContent=helperForNotFunction(queries[j].getSecondType(), allContent, queries[j].getNum(), queries[j].getStr());
                }
            }
            for(int i=0;i<allContent.length;i++)
            {
                for(int j=0;j<tran.length;j++)
                {
                    if(allContent[i]!=null)
                    {
                        allContent[i] = tran[j].transform(allContent[i]);
                    }
                }
            }
            for(int i=0;i<allContent.length;i++)
            {
                if(allContent[i]!=null)
                {
                    System.out.println(allContent[i]);
                }
            }
        }  
            
    }
}