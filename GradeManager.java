// // src/GradeManager.java
// import java.util.ArrayList;

// public class GradeManager {
//     private ArrayList<Student> studentList;

//     public GradeManager() {
//         this.studentList = new ArrayList<>();
//     }

//     // Naya student list me add karne ke liye
//     public void addStudent(Student student) {
//         studentList.add(student);
//         System.out.println("Student " + student.getName() + " ko add kar diya gaya hai!");
//     }

//     // Saare students ki report dekhne ke liye
//     public void displayAllStudents() {
//         if (studentList.isEmpty()) {
//             System.out.println("List khali hai! Pehle students add karein.");
//             return;
//         }
//         for (Student s : studentList) {
//             System.out.println("ID: " + s.getStudentId() + " | Name: " + s.getName() + " | Average: " + s.calculateAverage());
//         }
//     }
// }
import java.util.ArrayList;

public class GradeManager {
    private ArrayList<Student> studentList;

    public GradeManager() {
        this.studentList = new ArrayList<>();
    }

    public void addStudent(Student student) {
        studentList.add(student);
        System.out.println("🎉 Student " + student.getName() + " successfully added!");
    }

    public void displayAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println(" No students found in the database.");
            return;
        }
        System.out.println("\n--- 📋 Student Records ---");
        for (Student s : studentList) {
            System.out.printf("ID: %s | Name: %s | Average Grade: %.2f%%\n", 
                s.getStudentId(), s.getName(), s.calculateAverage());
        }
    }

    // PRO FEATURE: Class Analytics (Highest, Lowest, Class Average)
    public void displayClassAnalytics() {
        if (studentList.isEmpty()) {
            System.out.println(" Add students first to view analytics.");
            return;
        }

        double totalSum = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        String topStudent = "";
        String lowestStudent = "";

        for (Student s : studentList) {
            double avg = s.calculateAverage();
            totalSum += avg;

            if (avg > highest) {
                highest = avg;
                topStudent = s.getName();
            }
            if (avg < lowest) {
                lowest = avg;
                lowestStudent = s.getName();
            }
        }

        double classAverage = totalSum / studentList.size();

        System.out.println("\n📊 ======= CLASS ANALYTICS =======");
        System.out.printf("📈 Class Average Score : %.2f%%\n", classAverage);
        System.out.printf("🥇 Top Performer       : %s (%.2f%%)\n", topStudent, highest);
        System.out.printf("📉 Needs Improvement   : %s (%.2f%%)\n", lowestStudent, lowest);
        System.out.println("==================================");
    }

// GUI ko student list dene ke liye getter method
public ArrayList<Student> getStudentList() {
    return this.studentList;
}
}