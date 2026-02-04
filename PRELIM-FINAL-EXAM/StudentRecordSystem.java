/**
 * Programmer Identifier: Norman S. Gonzales Jr. | 25-0536-638
 */
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class StudentRecordSystem extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtID, txtName, txtGrade;
    private final String FILE_NAME = "MOCK_DATA.csv";

    public StudentRecordSystem() {
        // Updated title with your actual details
        setTitle("Student Records - Norman S. Gonzales Jr. [25-0536-638]");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout(10, 10));

        initializeUI();
        loadData();
    }

    private void initializeUI() {
        // Table Setup with Sorting
        model = new DefaultTableModel(new String[]{"ID", "Full Name", "Prelim Grade"}, 0);
        table = new JTable(model);
        table.setRowSorter(new TableRowSorter<>(model)); // Allows clicking headers to sort
        
        // Input Panel with better layout
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        txtID = new JTextField(5);
        txtName = new JTextField(12);
        txtGrade = new JTextField(5);
        
        JButton btnAdd = new JButton("Add Record");
        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new Color(255, 200, 200)); // Subtle red for delete

        inputPanel.add(new JLabel("ID:")); inputPanel.add(txtID);
        inputPanel.add(new JLabel("Name:")); inputPanel.add(txtName);
        inputPanel.add(new JLabel("Grade:")); inputPanel.add(txtGrade);
        inputPanel.add(btnAdd);
        inputPanel.add(btnDelete);

        // CRUD: Create (with validation)
        btnAdd.addActionListener(e -> addRecord());

        // CRUD: Delete (with confirmation)
        btnDelete.addActionListener(e -> deleteRecord());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void addRecord() {
        String id = txtID.getText().trim();
        String name = txtName.getText().trim();
        String grade = txtGrade.getText().trim();

        if (id.isEmpty() || name.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.addRow(new Object[]{id, name, grade});
        txtID.setText(""); txtName.setText(""); txtGrade.setText("");
        txtID.requestFocus();
    }

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Convert view index to model index (important when table is sorted!)
            int modelRow = table.convertRowIndexToModel(selectedRow);
            
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(modelRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    }

    private void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No existing data file found. Starting fresh.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines
                
                String[] data = line.split(",");
                // Defensive check: Ensure at least 7 columns exist based on your index usage
                if (data.length >= 7) {
                    model.addRow(new Object[]{data[0], data[1] + " " + data[2], data[6]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Apply System Look and Feel for a more modern native appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new StudentRecordSystem().setVisible(true));
    }
}