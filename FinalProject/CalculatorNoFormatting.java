import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

// A simple GUI-based calculator with a history feature
public class CalculatorNoFormatting extends JFrame implements ActionListener {

    //=================== Text Displays ======================//
    private JTextField textDisplay; // Displays the current input and result
    private JTextArea historyDisplay; // Displays the calculation history

    //=================== Functionality Variables ======================//
    private double input1, input2, resultingValue; // Stores operands and result
    private String operator; // Stores the selected operator
    private boolean done; // Indicates if the current calculation is complete

    public CalculatorNoFormatting() {
        //=================== GUI Main Frame ======================//
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        //=================== GUI Upper Text Display ======================//
        textDisplay = new JTextField();
        textDisplay.setEditable(false);
        textDisplay.setFont(new Font("Arial", Font.BOLD, 24));
        textDisplay.setHorizontalAlignment(JTextField.RIGHT);
        textDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(textDisplay, BorderLayout.NORTH);

        //=================== GUI Buttons ======================//
        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout(new GridLayout(5, 4, 5, 5));
        buttonGroup.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String button : buttons) {
            JButton b = new JButton(button);
            b.setFont(new Font("Arial", Font.PLAIN, 20));
            b.setFocusPainted(false);

            // Customize button colors based on type
            if (button.matches("[0-9.]") || button.equals("C")) {
                b.setBackground(Color.LIGHT_GRAY);
            } else {
                b.setBackground(Color.ORANGE);
                b.setForeground(Color.WHITE);
            }

            b.addActionListener(this); // Add action listener for button clicks
            buttonGroup.add(b);
        }

        add(buttonGroup, BorderLayout.CENTER);

        //=================== GUI Left History Text Area ======================//
        JPanel history = new JPanel();
        history.setLayout(new BorderLayout());
        history.setBorder(BorderFactory.createTitledBorder("History"));

        historyDisplay = new JTextArea(10, 20);
        historyDisplay.setEditable(false);
        historyDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        historyDisplay.setText("History:\n");

        JScrollPane scrollPane = new JScrollPane(historyDisplay);
        history.add(scrollPane, BorderLayout.CENTER);

        add(history, BorderLayout.WEST);

        //=================== GUI Frame Visibility ======================//
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String calcuInput = e.getActionCommand();

        //=================== If There is No Input and First Input is 0 ======================//
        if (textDisplay.getText().length() <= 0 && calcuInput.equals("0")) {
            return;
        }

        //=================== Reset Text Area and Input Values ======================//
        if (calcuInput.equals("C")) {
            input1 = 0;
            input2 = 0;
            textDisplay.setText("");
        } 
        //=================== If Input is a Digit or Dot (.) ======================//
        else if (Character.isDigit(calcuInput.charAt(0)) || calcuInput.equals(".")) {
            if (done) {
                textDisplay.setText("");
                done = false;
            }
            if (calcuInput.equals(".") && textDisplay.getText().contains(".")) {
                return;
            }
            textDisplay.setText(textDisplay.getText() + calcuInput);
        } 
        //=================== If Input is Equal Sign (=) ======================//
        else if (calcuInput.equals("=")) {
            input2 = Double.parseDouble(textDisplay.getText());
            calculate();
            textDisplay.setText(String.valueOf(resultingValue));
            done = true;
            try {
                String history = historyRecorder(
                        String.valueOf(input1) + " " +
                        operator + " " +
                        String.valueOf(input2) + " = " +
                        String.valueOf(resultingValue)
                );
                historyDisplay.setText("History:\n" + history);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } 
        //=================== If Input is an Operator ======================//
        else {
            operator = calcuInput;
            input1 = Double.parseDouble(textDisplay.getText());
            textDisplay.setText("");
            done = false;
        }
    }

    // Perform calculation based on the operator
    public void calculate() {
        switch (operator) {
            case "+":
                resultingValue = input1 + input2;
                break;
            case "-":
                resultingValue = input1 - input2;
                break;
            case "*":
                resultingValue = input1 * input2;
                break;
            case "/":
                resultingValue = input1 / input2;
                break;
        }
    }

    // Record history in a text file and return updated history
    public String historyRecorder(String record) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("calculator_history.txt"));
        StringBuilder history = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            history.append(line).append(System.lineSeparator());
        }
        history.append(record).append(System.lineSeparator());

        BufferedWriter writer = new BufferedWriter(new FileWriter("calculator_history.txt"));
        writer.write(history.toString());

        reader.close();
        writer.close();

        return history.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("calculator_history.txt"));
        writer.write(""); // Clear history file
        writer.close();

        new CalculatorNoFormatting();
    }
}