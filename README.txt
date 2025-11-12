# 🎓 Student Marksheet Manager

A Java-based GUI application for managing student academic records, generating professional PDF marksheets, and visualizing performance analytics.

## 📌 Features

- 🧑‍🎓 Add, update, and delete student records
- 📊 Live dashboard showing top scorer, average marks, and grade distribution
- 📝 Export individual or batch marksheets as professionally formatted PDFs
- 📂 Import/export student data via CSV
- 🧭 Guided update wizard to minimize admin errors
- 🎨 Branded UI with college logo and color-coded stats

## 🛠️ Technologies Used

| Category               | Tools/Tech Used                     |
|------------------------|-------------------------------------|
| Front End              | Java Swing                          |
| Back End               | Java                                |
| PDF Export             | iTextPDF 5.5.13.3                   |
| IDE                    | IntelliJ IDEA / NetBeans            |
| Data Format            | CSV, DAT                            |
| Image Assets           | College Logo (JPG)                  |

## 📦 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/SamuvelRajkumarGR/Student-Marksheet-Manager.git
   cd Student-Marksheet-Manager

2. Compile the project:
  ```bash
  javac -cp "itextpdf-5.5.13.3.jar;javax.mail.jar;activation.jar" *.java

3. Run the application:
  ```bash
  java -cp ".;itextpdf-5.5.13.3.jar;javax.mail.jar;activation.jar" LoginScreen

File Structure
├── AddStudentDialog.java
├── ButtonPanel.java
├── ChartPanel.java
├── ChartWindow.java
├── ExportCSVDialog.java
├── ExportDialog.java
├── ImportCSVDialog.java
├── LoginScreen.java
├── MarksheetApp.java
├── MarksheetGUI.java
├── MarksheetManager.java
├── NameSearchDialog.java
├── PDFTest.java
├── PreviewDialog.java
├── SearchDialog.java
├── Student.java
├── UpdateStudentDialog.java
└── README.txt

