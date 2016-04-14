import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] arguments) throws IOException,
            InterruptedException {
        System.out.println(getProcessOutput());
    }

    private static String getProcessOutput() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("java",
                "-version");

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        StringBuilder processOutput = new StringBuilder();

        try (
                BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()))
        ) {
            String readLine;

            while ((readLine = processOutputReader.readLine()) != null) {
                processOutput.append(readLine).append(System.lineSeparator());
            }

            process.waitFor();
        }

        return processOutput.toString().trim();
    }
}