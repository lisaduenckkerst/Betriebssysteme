import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileTest {

    public static void main(String[] args) {

        try {
            RandomAccessFile raf = new RandomAccessFile("/Users/lisadunck-kerst/intellij_projects/Git/Betriebssysteme/uebung10/src/testfile.txt", "rw");

            // read
            raf.seek(1);
            System.out.println("Daten an Stelle 1: " + raf.read());

            raf.seek(4);
            System.out.println("Daten an Stelle 4: " + raf.read());

            raf.seek(120);
            System.out.println("Daten an Stelle 120: " + raf.read());


            // write
            raf.seek(0);
            raf.writeUTF("Test ab 0\n");

            byte[] bytes = "Hello World\n".getBytes("UTF-8");
            raf.write(bytes, 0, 12);

            raf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}