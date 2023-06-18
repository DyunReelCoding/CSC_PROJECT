import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextFileReaderWriter extends JFrame {
    //private JTextArea textArea;
    private JButton readButton;
    private JButton writeButton;
    private JFileChooser fileChooser;

    public TextFileReaderWriter() {
        setTitle("Text File Reader/Writer");
        setSize(200, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       //textArea = new JTextArea();
        readButton = new JButton("Read File");
        writeButton = new JButton("Write File");
        fileChooser = new JFileChooser();

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(readButton);
        buttonPanel.add(writeButton);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);

        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(TextFileReaderWriter.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String content = readFile(file);
                    displayContent(content);
                }
            }
        });

        writeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayWritePanel();
            }
        });
    }

    private String readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void writeFile(File file, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            JOptionPane.showMessageDialog(this, "File written successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayContent(String content) {
        JFrame contentFrame = new JFrame();
        JTextArea contentArea = new JTextArea(content);
        contentArea.setEditable(false);
        contentFrame.setTitle("File Content");
        contentFrame.setSize(500, 400);
        contentFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        contentFrame.setLocationRelativeTo(null);
        contentFrame.add(new JScrollPane(contentArea));
        contentFrame.setVisible(true);
    }

    private void displayWritePanel() {
        JFrame writeFrame = new JFrame();
        JTextArea writeArea = new JTextArea();
        JButton saveButton = new JButton("Save");

        writeFrame.setTitle("Write File");
        writeFrame.setSize(500, 400);
        writeFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        writeFrame.setLocationRelativeTo(null);

        JPanel writePanel = new JPanel(new BorderLayout());
        writePanel.add(new JScrollPane(writeArea), BorderLayout.CENTER);
        writePanel.add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showSaveDialog(TextFileReaderWriter.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    writeFile(file, writeArea.getText());
                    writeFrame.dispose();
                }
            }
        });

        writeFrame.add(writePanel);
        writeFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TextFileReaderWriter().setVisible(true);
            }
        });
    }
}
