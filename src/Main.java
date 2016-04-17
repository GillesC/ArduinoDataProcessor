import java.io.IOException;

public class Main {

    public static void main(String[] arguments) throws IOException,
            InterruptedException {
        new Thread(new Processor()).start();
        ProcessBuilder b = new ProcessBuilder("python",
                "grabserial");
        Terminal.exec(b);

    }
}