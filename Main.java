// src/Main.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {
    private GradeManager manager;
    private DefaultTableModel tableModel;
    private JLabel lblClassAvg, lblTopStudent, lblLowestStudent;

    public Main() {
        manager = new GradeManager();
        
        // Window Configuration
        setTitle("🎓  Student Grade Management Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. TOP ANALYTICS PANEL ---
        JPanel analyticsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        analyticsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        lblClassAvg = createAnalyticsCard(analyticsPanel, "📈 Class Average", "0.00%", new Color(70, 130, 180));
        lblTopStudent = createAnalyticsCard(analyticsPanel, "🥇 Top Performer", "N/A", new Color(46, 139, 87));
        lblLowestStudent = createAnalyticsCard(analyticsPanel, "📉 Needs Focus", "N/A", new Color(178, 34, 34));
        add(analyticsPanel, BorderLayout.NORTH);

        // --- 2. CENTER DATA TABLE ---
        String[] columns = {"Student ID", "Student Name", "Grades entered", "Final Average"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- 3. SIDE CONTROL PANEL (INPUT) ---
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        controlPanel.setPreferredSize(new Dimension(280, 0));

        JTextField txtName = new JTextField();
        JTextField txtId = new JTextField();
        JTextField txtGrades = new JTextField(); // Comma-separated inputs like: 85,90,78

        controlPanel.add(new JLabel("Student Name:"));
        controlPanel.add(txtName);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(new JLabel("Student ID:"));
        controlPanel.add(txtId);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(new JLabel("Enter Grades (comma separated, e.g., 85,92,78):"));
        controlPanel.add(txtGrades);
        controlPanel.add(Box.createVerticalStrut(20));

        JButton btnAdd = new JButton("➕ Add Student Record");
        btnAdd.setBackground(new Color(100, 149, 237));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        controlPanel.add(btnAdd);

        add(controlPanel, BorderLayout.WEST);

        // --- BUTTON LOGIC ---
        btnAdd.addActionListener(e -> {
            String name = txtName.getText().trim();
            String id = txtId.getText().trim();
            String gradesRaw = txtGrades.getText().trim();

            if (name.isEmpty() || id.isEmpty() || gradesRaw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Student s = new Student(name, id);
                String[] gradeArray = gradesRaw.split(",");
                for (String gStr : gradeArray) {
                    s.addGrade(Double.parseDouble(gStr.trim()));
                }

                manager.addStudent(s);
                
                // Update UI Table
                tableModel.addRow(new Object[]{id, name, s.getGrades().size(), String.format("%.2f%%", s.calculateAverage())});
                
                // Update Analytics Cards
                updateAnalyticsUI();

                // Clear input boxes
                txtName.setText("");
                txtId.setText("");
                txtGrades.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric grades separated by commas.", "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel createAnalyticsCard(JPanel parent, String title, String val, Color bg) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JLabel lblVal = new JLabel(val);
        lblVal.setForeground(Color.WHITE);
        lblVal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblVal, BorderLayout.CENTER);
        parent.add(card);
        return lblVal;
    }

    private void updateAnalyticsUI() {
        ArrayList<Student> list = manager.getStudentList(); // Make sure to add a getter for studentList in GradeManager
        if (list.isEmpty()) return;

        double totalSum = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        String topStudent = "N/A";
        String lowestStudent = "N/A";

        for (Student s : list) {
            double avg = s.calculateAverage();
            totalSum += avg;
            if (avg > highest) { highest = avg; topStudent = s.getName(); }
            if (avg < lowest) { lowest = avg; lowestStudent = s.getName(); }
        }

        lblClassAvg.setText(String.format("%.2f%%", totalSum / list.size()));
        lblTopStudent.setText(String.format("%s (%.1f%%)", topStudent, highest));
        lblLowestStudent.setText(String.format("%s (%.1f%%)", lowestStudent, lowest));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}