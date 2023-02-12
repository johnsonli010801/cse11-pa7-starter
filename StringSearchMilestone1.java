import java.nio.file.*;
import java.io.IOException;
class FileHelper {
    // Takes a path to a file as input
    // Returns all of the lines in the file as an array of Strings
    // Prints an error message if it failed
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
}
