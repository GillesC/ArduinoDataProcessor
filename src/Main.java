import java.io.IOException;

public class Main {

    public static void main(String[] arguments) throws IOException,
            InterruptedException {

        // 1. start pulling data from Arduino


        // 2. parse collected data to
        //      2.a data-temp
        //      2.b data-vent
        //      2.c data-light

        ProcessBuilder b = new ProcessBuilder("python",
                "grabserial");
        Terminal.exec(b);
        new Processor().run();
    }
}