// Hanumesh K
// 2117240020122
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// ---------- Problem Class ----------
class Problem implements Serializable {
    private String title;
    private String topic;
    private String difficulty;
    private String codeSnippet;
    private LocalDate dateAdded;

    public Problem(String title, String topic, String difficulty, String codeSnippet) {
        this.title = title;
        this.topic = topic;
        this.difficulty = difficulty;
        this.codeSnippet = codeSnippet;
        this.dateAdded = LocalDate.now();
    }

    public String getTitle() { return title; }
    public String getTopic() { return topic; }
    public String getDifficulty() { return difficulty; }
    public String getCodeSnippet() { return codeSnippet; }
    public LocalDate getDateAdded() { return dateAdded; }

    @Override
    public String toString() {
        return "Title: " + title + "\nTopic: " + topic + "\nDifficulty: " + difficulty +
                "\nDate: " + dateAdded + "\nCode:\n" + codeSnippet + "\n-----------------------------\n";
    }
}

// ---------- Main GUI Class ----------
public class PracticeTrackerGUI extends JFrame {
    private static final String FILE_NAME = "problems.dat";
    private List<Problem> problems;

    private JTextField titleField, topicField, diffField, searchField;
    private JTextArea codeArea, outputArea;

    public PracticeTrackerGUI() {
        setTitle("Programming Practice Tracker - Hanumesh K (2117240020122)");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        problems = loadProblems();

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Problem"));

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Topic:"));
        topicField = new JTextField();
        inputPanel.add(topicField);

        inputPanel.add(new JLabel("Difficulty (Easy/Medium/Hard):"));
        diffField = new JTextField();
        inputPanel.add(diffField);

        inputPanel.add(new JLabel("Code Snippet:"));
        codeArea = new JTextArea(5, 20);
        inputPanel.add(new JScrollPane(codeArea));

        JButton addButton = new JButton("Add Problem");
        inputPanel.add(addButton);

        // Output panel
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));
        searchField = new JTextField(15);
        JButton searchTopicBtn = new JButton("Search by Topic");
        JButton searchDiffBtn = new JButton("Search by Difficulty");
        JButton viewAllBtn = new JButton("View All");
        searchPanel.add(new JLabel("Enter keyword:"));
        searchPanel.add(searchField);
        searchPanel.add(searchTopicBtn);
        searchPanel.add(searchDiffBtn);
        searchPanel.add(viewAllBtn);

        // Add listeners
        addButton.addActionListener(e -> addProblem());
        searchTopicBtn.addActionListener(e -> searchByTopic());
        searchDiffBtn.addActionListener(e -> searchByDifficulty());
        viewAllBtn.addActionListener(e -> viewAllProblems());

        // Layout
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addProblem() {
        String title = titleField.getText().trim();
        String topic = topicField.getText().trim();
        String diff = diffField.getText().trim();
        String code = codeArea.getText().trim();

        if (title.isEmpty() || topic.isEmpty() || diff.isEmpty() || code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        problems.add(new Problem(title, topic, diff, code));
        saveProblems();
        outputArea.setText("✅ Problem added successfully!\n");
        clearFields();
    }

    private void viewAllProblems() {
        if (problems.isEmpty()) {
            outputArea.setText("No problems found!");
        } else {
            outputArea.setText("");
            for (Problem p : problems) {
                outputArea.append(p.toString());
            }
        }
    }

    private void searchByTopic() {
        String keyword = searchField.getText().trim();
        List<Problem> filtered = problems.stream()
                .filter(p -> p.getTopic().equalsIgnoreCase(keyword))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) outputArea.setText("No problems found for topic: " + keyword);
        else {
            outputArea.setText("");
            for (Problem p : filtered) outputArea.append(p.toString());
        }
    }

    private void searchByDifficulty() {
        String keyword = searchField.getText().trim();
        List<Problem> filtered = problems.stream()
                .filter(p -> p.getDifficulty().equalsIgnoreCase(keyword))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) outputArea.setText("No problems found for difficulty: " + keyword);
        else {
            outputArea.setText("");
            for (Problem p : filtered) outputArea.append(p.toString());
        }
    }

    private void saveProblems() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(problems);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Problem> loadProblems() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Problem>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void clearFields() {
        titleField.setText("");
        topicField.setText("");
        diffField.setText("");
        codeArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PracticeTrackerGUI());
    }
}