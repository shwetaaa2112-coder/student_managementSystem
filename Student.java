import java.util.ArrayList;

public class Student {
    private String name;
    private String studentId;
    private ArrayList<Double> grades;

    // Constructor
    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.grades = new ArrayList<>();
    }

    // Add a grade to this student
    public void addGrade(double grade) {
        if (grade >= 0 && grade <= 100) {
            this.grades.add(grade);
        } else {
            System.out.println("Invalid grade! Must be between 0 and 100.");
        }
    }

    // Calculate the student's individual average
    public double calculateAverage() {
        if (grades.isEmpty()) return 0.0;
        
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getStudentId() { return studentId; }
    public ArrayList<Double> getGrades() { return grades; }
}