import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Gilles Callebaut on 14/04/2016.
 *
 */

public class Terminal {
    private static boolean pull = true;
    public static StringBuilder processOutput;


    public static String exec(ProcessBuilder processBuilder) throws IOException, InterruptedException {
        processBuilder.redirectErrorStream(true);

        final Process process = processBuilder.start();

        processOutput = new StringBuilder();

        try (
                BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()))
        ) {
            String readLine;

            while ((readLine = processOutputReader.readLine()) != null && pull) {
                System.out.println("\tProcessing line:\t"+readLine+System.lineSeparator());
                processArduinoOutput(readLine);
            }

            process.waitFor();
        }

        return processOutput.toString().trim();
    }

    private static void processArduinoOutput(String line) {
        Data data = new Data(line);
        Data.pushData(data);
    }
}
