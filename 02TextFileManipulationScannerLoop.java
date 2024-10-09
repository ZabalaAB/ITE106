import java.io.File;
import java.util.Scanner;

public class ScannerTextFile {
    public static void main(String[] args) throws Exception {
        File myFile = new File("MyFile.txt");
        Scanner get = new Scanner(myFile);
        while(get.hasNextLine())
        {
            System.out.println(get.nextLine());
        }
    }
}