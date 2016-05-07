import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Gilles Callebaut on 14/04/2016.
 *
 */

class Terminal {
    static String exec(ProcessBuilder processBuilder) throws IOException, InterruptedException {
        processBuilder.redirectErrorStream(true);

        final Process process = processBuilder.start();

        try (
                BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()))
        ) {
            String readLine;

            while ((readLine = processOutputReader.readLine()) != null) {
                System.out.println("\tReceived:\t"+readLine+System.lineSeparator());
                processArduinoOutput(readLine);
            }

            process.waitFor();
        }

        return "".trim();
    }

    private static void processArduinoOutput(String line) {
        Data data = null;
        try {
            if(line.charAt(0)=='X'){
                Data.pushNodeIsAlive(line.substring(1));
            }
            data = new Data(line);
            Data.pushData(data);
        } catch (Exception e) {
            System.out.println("Data hasn't been saved because of wrong format.");
            e.printStackTrace();
        }
    }
}
