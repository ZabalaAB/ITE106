import java.io.File;
import java.util.Scanner;

public class ScannerTextFileWOLoop {
    public static void main(String[] args) throws Exception {
        File myFile = new File("MyFile.txt");
        Scanner get = new Scanner(myFile);
        get.useDelimiter("\\Z");
        System.out.println(get.next());
    }
}
