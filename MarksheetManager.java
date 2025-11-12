import java.io.*;
import java.util.*;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.util.Properties;

public class MarksheetManager {
    private java.util.List<Student> students = new java.util.ArrayList<>();
    private String className = "Class";
private String examName = "Exam";

public void setClassName(String className) {
    this.className = className;
}

public void setExamName(String examName) {
    this.examName = examName;
}

    public void addStudent(Student s) {
        students.add(s);
    }

    public java.util.List<Student> getAllStudents() {
        return students;
    }

    public Student searchByRoll(int rollNo) {
        for (Student s : students) {
            if (s.getRollNo() == rollNo) return s;
        }
        return null;
    }
public void importFromCSV(String filename) throws IOException {
    students.clear();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line = reader.readLine(); // Skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int rollNo = Integer.parseInt(parts[0]);
            String name = parts[1];
            int[] marks = new int[5];
            for (int i = 0; i < 5; i++) {
                marks[i] = Integer.parseInt(parts[2 + i]);
            }
            Student s = new Student(rollNo, name, marks);
            students.add(s);
        }
    }
}

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(students);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            students = (java.util.List<Student>) in.readObject();
        }
    }

    public void exportToCSV(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Roll No,Name,Mark1,Mark2,Mark3,Mark4,Mark5,Total,Percentage,Grade,Date Added");
            for (Student s : students) {
                int[] m = s.getMarks();
                writer.printf("%d,%s,%d,%d,%d,%d,%d,%d,%.2f,%s,%s%n",
                    s.getRollNo(), s.getName(),
                    m[0], m[1], m[2], m[3], m[4],
                    s.getTotal(), s.getPercentage(), s.getGrade(), s.getDateAdded());
            }
        }
    }

    public double getAveragePercentage() {
        if (students.isEmpty()) return 0;
        double total = 0;
        for (Student s : students) total += s.getPercentage();
        return total / students.size();
    }

    public Student getTopScorer() {
        if (students.isEmpty()) return null;
        Student top = students.get(0);
        for (Student s : students) {
            if (s.getTotal() > top.getTotal()) top = s;
        }
        return top;
    }

    public Map<String, Integer> getGradeDistribution() {
        Map<String, Integer> map = new HashMap<>();
        for (Student s : students) {
            String grade = s.getGrade();
            map.put(grade, map.getOrDefault(grade, 0) + 1);
        }
        return map;
    }
public java.util.List<Student> searchByName(String keyword) {
    java.util.List<Student> result = new java.util.ArrayList<>();
    for (Student s : students) {
        if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
            result.add(s);
        }
    }
    return result;
}

    public java.util.List<Student> filterByGrade(String grade) {
        java.util.List<Student> result = new java.util.ArrayList<>();
        for (Student s : students) {
            if (s.getGrade().equals(grade)) result.add(s);
        }
        return result;
    }
public boolean updateStudent(int oldRollNo, int newRollNo, String newName, int[] newMarks) {
    Student s = searchByRoll(oldRollNo);
    if (s != null) {
        students.remove(s);
        students.add(new Student(newRollNo, newName, newMarks));
        return true;
    }
    return false;
}
 	
public void exportToTXT(String filename) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
        for (Student s : students) {
            writer.println(s.getPerformanceSummary());
            writer.println("----------------------------");
        }
    }
}

public void exportToPDF(String filename) throws Exception {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(filename));
    document.open();

    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

    // Logo
    Image logo = Image.getInstance("eec_logo.jpg");
    logo.scaleAbsolute(80f, 80f);
    logo.setAlignment(Image.ALIGN_CENTER);
    document.add(logo);

    // College Name
    Paragraph collegeName = new Paragraph("EASWARI ENGINEERING COLLEGE", titleFont);
    collegeName.setAlignment(Element.ALIGN_CENTER);
    document.add(collegeName);
    document.add(new Paragraph(" "));

    for (Student s : students) {
        // Header Info
        document.add(new Paragraph("Result Of: " + examName, labelFont));
        document.add(new Paragraph("Class: " + className, cellFont));
        document.add(new Paragraph("Roll No.: " + s.getRollNo(), cellFont));
        document.add(new Paragraph("Name: " + s.getName(), cellFont));
        document.add(new Paragraph("Date Added: " + s.getDateAdded(), cellFont));
        document.add(new Paragraph(" "));

        // Marks Table
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        String[] headers = {"Subjects", "Total Marks", "Marks Obtained", "Grade", "Remarks"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, labelFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        int[] marks = s.getMarks();
        for (int i = 0; i < marks.length; i++) {
            table.addCell(new Phrase("Sub-" + (i + 1), cellFont));
            table.addCell(new Phrase("100", cellFont));
            table.addCell(new Phrase(String.valueOf(marks[i]), cellFont));
            table.addCell(new Phrase(s.getGrade(), cellFont));
            table.addCell(new Phrase("Very Good", cellFont));
        }

        // Total Row
        int totalMarks = marks.length * 100;
        table.addCell(new Phrase("Total", labelFont));
        table.addCell(new Phrase(String.valueOf(totalMarks), cellFont));
        table.addCell(new Phrase(String.valueOf(s.getTotal()), cellFont));
        table.addCell(new Phrase("", cellFont));
        table.addCell(new Phrase("", cellFont));

        document.add(table);

        // Signature
        Paragraph signature = new Paragraph("Signature", labelFont);
        signature.setAlignment(Element.ALIGN_RIGHT);
        signature.setSpacingBefore(20f);
        document.add(signature);

        document.add(new Paragraph("----------------------------"));
    }

    document.close();
}
public void exportStudentToPDF(Student s, String filename) throws Exception {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(filename));
    document.open();

    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

    Image logo = Image.getInstance("eec_logo.jpg");
    logo.scaleAbsolute(80f, 80f);
    logo.setAlignment(Image.ALIGN_CENTER);
    document.add(logo);

    Paragraph collegeName = new Paragraph("EASWARI ENGINEERING COLLEGE", titleFont);
    collegeName.setAlignment(Element.ALIGN_CENTER);
    document.add(collegeName);
    document.add(new Paragraph(" "));

    document.add(new Paragraph("Result Of: " + examName, labelFont));
    document.add(new Paragraph("Class: " + className, cellFont));
    document.add(new Paragraph("Roll No.: " + s.getRollNo(), cellFont));
    document.add(new Paragraph("Name: " + s.getName(), cellFont));
    document.add(new Paragraph("Date Added: " + s.getDateAdded(), cellFont));
    document.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100);
    table.setSpacingBefore(10f);
    table.setSpacingAfter(10f);

    String[] headers = {"Subjects", "Total Marks", "Marks Obtained", "Grade", "Remarks"};
    for (String h : headers) {
        PdfPCell cell = new PdfPCell(new Phrase(h, labelFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    int[] marks = s.getMarks();
    for (int i = 0; i < marks.length; i++) {
        table.addCell(new Phrase("Sub-" + (i + 1), cellFont));
        table.addCell(new Phrase("100", cellFont));
        table.addCell(new Phrase(String.valueOf(marks[i]), cellFont));
        table.addCell(new Phrase(s.getGrade(), cellFont));
        table.addCell(new Phrase(getRemark(marks[i]), cellFont));
    }

    int totalMarks = marks.length * 100;
    table.addCell(new Phrase("Total", labelFont));
    table.addCell(new Phrase(String.valueOf(totalMarks), cellFont));
    table.addCell(new Phrase(String.valueOf(s.getTotal()), cellFont));
    table.addCell(new Phrase("", cellFont));
    table.addCell(new Phrase("", cellFont));

    document.add(table);

    Paragraph signature = new Paragraph("Signature", labelFont);
    signature.setAlignment(Element.ALIGN_RIGHT);
    signature.setSpacingBefore(20f);
    document.add(signature);

    document.add(new Paragraph("----------------------------"));
    document.close();
}

private String getRemark(int mark) {
    if (mark >= 90) return "Excellent";
    else if (mark >= 80) return "Very Good";
    else if (mark >= 70) return "Good";
    else if (mark >= 60) return "Fair";
    else return "Needs Improvement";
}
	public void exportAllStudentsToSinglePDF(List<Student> students, String fileName) throws Exception {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(fileName));
    document.open();

    for (Student s : students) {
        // 🏫 College Header
        Image logo = Image.getInstance("eec_logo.jpg");
	logo.scaleToFit(80, 80);
        logo.setAlignment(Image.ALIGN_CENTER);
        document.add(logo);

	Paragraph header = new Paragraph("EASWARI ENGINEERING COLLEGE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        // 🧾 Exam Name
        Paragraph exam = new Paragraph("Exam: " + examName, FontFactory.getFont(FontFactory.HELVETICA, 14));
        exam.setAlignment(Element.ALIGN_CENTER);
        document.add(exam);

        document.add(Chunk.NEWLINE);

        // 👤 Student Info
        document.add(new Paragraph("Roll No: " + s.getRollNo()));
        document.add(new Paragraph("Name: " + s.getName()));
        document.add(new Paragraph("Subject 1: " + s.getMarks()[0]));
        document.add(new Paragraph("Subject 2: " + s.getMarks()[1]));
        document.add(new Paragraph("Subject 3: " + s.getMarks()[2]));
        document.add(new Paragraph("Subject 4: " + s.getMarks()[3]));
        document.add(new Paragraph("Subject 5: " + s.getMarks()[4]));
        document.add(new Paragraph("Total: " + s.getTotal()));
        document.add(new Paragraph("Percentage: " + String.format("%.2f", s.getPercentage())));
        document.add(new Paragraph("Grade: " + s.getGrade()));
        document.add(new Paragraph("Date Added: " + s.getDateAdded()));

        document.newPage(); // 🗂️ Start new page for next student
    }

    document.close();
}
}