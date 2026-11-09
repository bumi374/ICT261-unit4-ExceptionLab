import java.io.*;
import java.util.*;
import java.util.logging.*;

public class MarksImporter {

    private static final Logger logger =
            Logger.getLogger(MarksImporter.class.getName());

    static void importMarks(String filename) throws IOException {

        Set<String> seen = new HashSet<>();
       try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

    String line;

    while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");

if (parts.length != 2) {
    System.out.println("Invalid record format: " + line);
    continue;
}

String studentId = parts[0].trim();
String markText = parts[1].trim();
try {
    int mark = Integer.parseInt(markText);

    if (mark < 0 || mark > 100) {
        throw new IllegalArgumentException("Mark must be between 0 and 100");
    
}
if (!seen.add(studentId)) {

    throw new DuplicateStudentException(
        "Student ID already exists: " + studentId);

}
System.out.println("Accepted: " + studentId + " - " + mark);


} catch (NumberFormatException e) {
    System.out.println("Invalid numeric mark for " + studentId + ": " + markText);
    logger.warning("NumberFormatException: " + e.getMessage());
    continue;

} catch (DuplicateStudentException e) {
 System.out.println("Duplicate record skipped: " + e.getMessage());
 logger.warning("DuplicateStudentException: " + e.getMessage());
continue;

} catch (IllegalArgumentException e) {
    System.out.println("Invalid mark for " + studentId + ": " + e.getMessage());
    logger.warning("IllegalArgumentException: " + e.getMessage());
    continue;
}

        System.out.println("Reading: " + line);

    }

} 

    }
public static void main(String[] args) {

    try {
        importMarks("marks.txt");

    } catch (FileNotFoundException e) {

        System.out.println("Marks file not found; check the file name");
        logger.severe("FileNotFoundException: " + e.getMessage());

    } catch (IOException e) {

        System.out.println("An error occurred while reading the marks file");
        logger.severe("IOException: " + e.getMessage());

    } finally {

        System.out.println("Import attempt finished");
    }
}
}