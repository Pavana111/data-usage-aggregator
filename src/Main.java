import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> inputFiles = Arrays.asList(
            "input/input1.txt",
            "input/input2.txt"
        );

        Processor processors = new Processor();
        processors.processFiles(inputFiles);

        // Add this line to print the final aggregated report
        processors.printReport();
    }
}
