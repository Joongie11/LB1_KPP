package reflectionWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Task1GUI extends JFrame {
    private final JTextField classNameTextField;
    private final JTextArea classDescriptionTextArea;

    public Task1GUI() {
        setTitle("Class Description");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);

        classNameTextField = new JTextField(20);
        classNameTextField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));

        JButton analyzeButton = new JButton("Analyze");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");

        classDescriptionTextArea = new JTextArea();
        classDescriptionTextArea.setEditable(false);
        classDescriptionTextArea.setLineWrap(true);
        classDescriptionTextArea.setWrapStyleWord(true);

        analyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String className = classNameTextField.getText();
                try {
                    String classDescription = Task1.describeClass(className);
                    classDescriptionTextArea.setText(classDescription);
                } catch (ClassNotFoundException ex) {
                    classDescriptionTextArea.setText("Class not found: " + className);
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                classNameTextField.setText("");
                classDescriptionTextArea.setText("");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(analyzeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        inputPanel.add(new JLabel("Class Name:"), BorderLayout.WEST);
        inputPanel.add(classNameTextField, BorderLayout.CENTER);

        classDescriptionTextArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(classDescriptionTextArea), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Task1GUI gui = new Task1GUI();
                gui.setVisible(true);
            }
        });
    }
}