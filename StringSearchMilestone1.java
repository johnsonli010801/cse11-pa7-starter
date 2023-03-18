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
class StringSearch{
    public static void main(String[]args)
    {
        if(args.length<=0)
        {
            return;
        }
        String[] allContent = FileHelper.getLines(args[0]);
        for(int i=0;i<allContent.length;i++)
        {
            System.out.println(allContent[i]);
        }
    }
}
