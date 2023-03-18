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
class ContainsQuery{
    String que;
    public ContainsQuery(String que)
    {
        this.que = que;
    }
    boolean matches(String s)
    {
        boolean actual = s.contains(que);
        return actual;
    }
}
class StringSearch{
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
        else if(args.length==2)
        {
            String[] allContent = FileHelper.getLines(args[0]);
            int indexQueries=10;
            int checkingNeg = 0;
            ContainsQuery[] queries = {new ContainsQuery("length="),new ContainsQuery("greater="),new ContainsQuery("less="),new ContainsQuery("contains="),new ContainsQuery("starts="),new ContainsQuery("ends=")};
            if(args[1].contains("not")==true)
            {
                checkingNeg = 1;
            }
            for(int i=0;i<queries.length;i++)
            {
                if(queries[i].matches(args[1]))
                {
                    indexQueries = i;
                    break;
                }
            }
            switch(indexQueries){
                case 0:
                {
                    if(checkingNeg==0){
                        int num = Integer.parseInt(args[1].substring(7,args[1].length()));
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(allContent[j].length()==num)
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                    else{
                        int num = Integer.parseInt(args[1].substring(11,args[1].length()-1));
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(allContent[j].length()!=num)
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                }
                case 1:
                {
                    if(checkingNeg==0)
                    {
                        int num = Integer.parseInt(args[1].substring(8,args[1].length()));
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(allContent[j].length()>num)
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                    else{
                        int num = Integer.parseInt(args[1].substring(12,args[1].length()-1));
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(allContent[j].length()<num)
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                }
                case 2:
                {
                    if(checkingNeg==0)
                    {
                        int num = Integer.parseInt(args[1].substring(5,args[1].length()));
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(allContent[j].length()<num)
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                    else{
                        int num = Integer.parseInt(args[1].substring(9,args[1].length()-1));
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(allContent[j].length()>num)
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                }
                case 3:
                {
                    if(checkingNeg==0)
                    {
                        String str = args[1].substring(10,args[1].length()-1);
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(allContent[j].contains(str))
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                    else{
                        String str = args[1].substring(14,args[1].length()-2);
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(!allContent[j].contains(str))
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                }
                case 4 :
                {
                    if(checkingNeg==0)
                    {
                        String str = args[1].substring(8,args[1].length()-1);
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(allContent[j].startsWith(str))
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                    else{
                        String str = args[1].substring(12,args[1].length()-2);
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(!allContent[j].startsWith(str))
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                }
                case 5:
                {
                    if(checkingNeg==0)
                    {
                        String str = args[1].substring(7,args[1].length()-1);
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(allContent[j].endsWith(str))
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                    else{
                        String str = args[1].substring(11,args[1].length()-2);
                        for(int j=0;j<allContent.length;j++)
                        {
                            if(!allContent[j].endsWith(str))
                            {
                                System.out.println(allContent[j]);
                            }
                        }
                        break;
                    }
                }
            }
            
        //}
        }
    }
}
