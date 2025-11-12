import java.io.Serializable;
import java.time.LocalDate;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int rollNo;
    private String name;
    private int[] marks;
    private LocalDate dateAdded;
    private String email;

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

    public Student(int rollNo, String name, int[] marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
        this.dateAdded = LocalDate.now();
    }

    public int getRollNo() { return rollNo; }
    public String getName() { return name; }
    public int[] getMarks() { return marks; }
    public LocalDate getDateAdded() { return dateAdded; }

    public int getTotal() {
        int total = 0;
        for (int mark : marks) total += mark;
        return total;
    }

    public double getPercentage() {
        return getTotal() / (double) marks.length;
    }

    public String getGrade() {
        double percent = getPercentage();
        if (percent >= 90) return "A+";
        else if (percent >= 80) return "A";
        else if (percent >= 70) return "B";
        else if (percent >= 60) return "C";
        else return "F";
    }
public String getPerformanceSummary() {
    StringBuilder sb = new StringBuilder();
    sb.append("Roll No: ").append(rollNo).append("\n");
    sb.append("Name: ").append(name).append("\n");
    for (int i = 0; i < marks.length; i++) {
        sb.append("Subject ").append(i + 1).append(": ").append(marks[i]).append("\n");
    }
    sb.append("Total: ").append(getTotal()).append("\n");
    sb.append("Percentage: ").append(String.format("%.2f", getPercentage())).append("%\n");
    sb.append("Grade: ").append(getGrade()).append("\n");
    sb.append("Date Added: ").append(getDateAdded()).append("\n");
    return sb.toString();
}
}