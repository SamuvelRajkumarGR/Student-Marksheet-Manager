import java.util.*;
import java.io.*;

public class PDFTest {
    public static void main(String[] args) {
        try {
            MarksheetManager manager = new MarksheetManager();

            // Sample student
            int[] marks = {85, 78, 92, 88, 74};
            Student s = new Student(3, "Samuvel", marks);
            manager.addStudent(s);

            // Set class and exam name
            manager.setClassName("CSE - II Year");
            manager.setExamName("Midterm Exam");

            // Export to PDF
            manager.exportToPDF("test_marksheet.pdf");

            System.out.println("PDF generated successfully: test_marksheet.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}