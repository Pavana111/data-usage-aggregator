# Data Usage Aggregator 

This Java project processes mobile data usage records from multiple input files. It aggregates usage for each mobile number across files (which can represent different towers or months), differentiates between 4G/5G and home/roaming usage, and calculates the total data usage. The program reads inputs, processes them, and prints aggregated results. JUnit is used for testing core functionalities.

##  Project Structure

- `src/model/` – Contains `UsageRecord.java` and `GuestData.java`
- `src/Processor.java` – Core logic for parsing and aggregating usage
- `Main.java` – Entry point for running the project
- `test/ProcessorTest.java` – JUnit tests for validating logic
- `input/` – Folder containing `.txt` files with input data
- `lib/` – JUnit library files
- `bin/` – Compiled `.class` files will be placed here

## ▶ How to Compile and Run Everything

Use the following commands to compile and run the full application along with tests:

javac -d bin src/model/*.java src/*.java


java -cp bin Main


javac -d bin -cp "lib/*;bin" test/ProcessorTest.java


java -cp "lib/*;bin" org.junit.platform.console.ConsoleLauncher --select-class ProcessorTest
