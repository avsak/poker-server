package by.avsak.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PokerServer {

    private static final Logger log = LogManager.getRootLogger();
    private static final String INPUT_FILE_PATH = "./src/main/resources/input.txt";
    private static int counter = 0;

    public static void main(String[] args) throws IOException {
        log.info("Server is running");
        Parser parser = new Parser();
        Validator validator = new Validator();

        List<String> fileLines = scanFile(INPUT_FILE_PATH);
        fileLines.forEach(line -> {
            counter++;
            PokerRound pokerRound = parser.parsePokerRound(line);

            validator.checkCardsForDuplicates(pokerRound);
        });
    }

    private static List<String> scanFile(String inputFilePath) throws IOException {
        List<String> fileLines = new ArrayList<>();
        Files.lines(Paths.get(inputFilePath), StandardCharsets.UTF_8).forEach(line -> fileLines.add(line.trim()));
        log.info("File read successfully");
        return fileLines;
    }
}
