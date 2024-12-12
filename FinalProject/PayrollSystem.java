import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.io.*;

public class PayrollSystem extends JFrame implements ActionListener {
	
	// declaration of 'global variables' that will be accessed in more than 1 method
	// the text fields for the user input that the text (values) they contain will be saved into a file
	public JTextField eventsDisplay, employeeID, employeeName, hourlyRate, hoursWorked, grossPay, taxRate, taxDeduction, netPay;
	public JTextArea recordDisplay;
	public String previousEmployeeID, previousEmployeeName, previousHourlyRate, previousHoursWorked, previousTaxRate;
	String filename = "PayrollDatafile.txt";
	
	// link sa design layout ko, na sinunod ko (kinda) sa paggawa ng GUI - https://excalidraw.com/#json=PT5eN-oxhZ0pEwzQJeYKu,U6FnVaPMGv_nayrF4ql62g
	
    public PayrollSystem() {
        // Main GUI frame setup
        setTitle("Payroll System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(680, 545);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        // Set minimalistic font and background color
        Font minimalFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color backgroundColor = new Color(245, 245, 245); // Light gray background
        Color primaryTextColor = new Color(20, 20, 20); // Deep dark text color

        // Set background color for the entire frame
        getContentPane().setBackground(backgroundColor);

        // Initialization of the 5 main panels
        JPanel eventsPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new BorderLayout());
        JPanel calculationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel outputPanel = new JPanel(new BorderLayout());
        JPanel recordsPanel = new JPanel(new BorderLayout());

        // Set borders for the panels with a subtle border color
        inputPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        outputPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        recordsPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1));

        // ======== START OF eventsPanel COMPONENTS ========
        eventsDisplay = new JTextField("");
        eventsDisplay.setEditable(false);
        eventsDisplay.setHorizontalAlignment(JTextField.CENTER);
        eventsDisplay.setPreferredSize(new Dimension(getWidth() - 25, 25)); // Adjust text field size
        eventsDisplay.setBackground(Color.WHITE);
        eventsDisplay.setForeground(primaryTextColor);
        eventsDisplay.setFont(minimalFont);
        eventsPanel.add(eventsDisplay);
        add(eventsPanel);
        // ======== END OF eventsPanel COMPONENTS ========

        // ======== START OF inputPanel COMPONENTS ========
        JPanel slimPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel textGroup1 = new JPanel(new GridLayout(2, 4, 0, 0)); // 2 rows, 4 columns
        JPanel buttonPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Labels for the input fields
        String[] inputTexts = { "Employee ID", "Employee Name", "Hourly Rate (₱)", "Hours Worked (Daily)" };

        // Create labels and add them to textGroup1
        for (String text : inputTexts) {
            JLabel label = new JLabel(text);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setForeground(primaryTextColor);
            label.setFont(minimalFont);
            textGroup1.add(label);
        }

        employeeID = new JTextField("");
        employeeName = new JTextField("");
        hourlyRate = new JTextField("");
        hoursWorked = new JTextField("");

        // Array of text fields for easy iteration
        JTextField[] textFields = { employeeID, employeeName, hourlyRate, hoursWorked };

        // Add text fields to textGroup1
        for (int i = 0; i < textFields.length; i++) {
            textFields[i].setPreferredSize(new Dimension(150, 25)); // Adjust text field size
            textFields[i].setFont(minimalFont);
            textFields[i].setBackground(Color.WHITE);
            textFields[i].setForeground(primaryTextColor);
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.add(textFields[i]);
            textGroup1.add(panel);
        }

        // Add textGroup1 and buttonPanel1 to inputPanel
        inputPanel.add(textGroup1, BorderLayout.NORTH);
        inputPanel.add(buttonPanel1, BorderLayout.SOUTH);
        slimPanel1.add(inputPanel);
        add(slimPanel1);
        // ======== END OF inputPanel COMPONENTS ========

        // ======== START OF calculationPanel COMPONENTS ========
        JPanel slimPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        calculateButton.setFocusPainted(false); // Remove focus outline
        calculateButton.setFont(minimalFont);
        calculateButton.setBackground(new Color(255, 182, 193)); // Light pink color
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setBorder(new LineBorder(new Color(255, 182, 193), 1));

        // Add button to calculationPanel and then add to the main JFrame
        calculationPanel.add(calculateButton);
        slimPanel2.add(calculationPanel);
        add(slimPanel2);
        // ======== END OF calculationPanel COMPONENTS ========

        // ======== START OF outputPanel COMPONENTS ========
        JPanel slimPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel textGroup2 = new JPanel(new GridLayout(2, 6));
        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Labels for the output fields
        String[] inputTexts2 = { "Gross Pay (₱)", "Tax Rate (%)", "Tax Deduction", "Net Pay" };

        // Create labels and add them to textGroup2
        for (String text : inputTexts2) {
            JLabel label = new JLabel(text);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setForeground(primaryTextColor);
            label.setFont(minimalFont);
            textGroup2.add(label);
        }

        grossPay = new JTextField("");
        taxRate = new JTextField("");
        taxDeduction = new JTextField("");
        netPay = new JTextField("");

        // Array of text fields for easy iteration
        JTextField[] textFields2 = { grossPay, taxRate, taxDeduction, netPay };

        // Add text fields to textGroup2
        for (int i = 0; i < textFields2.length; i++) {
            textFields2[i].setPreferredSize(new Dimension(150, 25)); // Adjust text field size
            textFields2[i].setHorizontalAlignment(JTextField.CENTER);
            textFields2[i].setEditable(false); // Disable editing for output fields
            textFields2[i].setFont(minimalFont);
            textFields2[i].setBackground(Color.WHITE);
            textFields2[i].setForeground(primaryTextColor);

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.add(textFields2[i]);
            textGroup2.add(panel);
        }

        taxRate.setEditable(true); // Allow editing for tax rate field

        // Create the "Save Employee Record" button
        JButton saveEmployeeRecord = new JButton("Save Employee Record");
        saveEmployeeRecord.addActionListener(this);
        saveEmployeeRecord.setFocusPainted(false); // Remove focus outline
        saveEmployeeRecord.setFont(minimalFont);
        saveEmployeeRecord.setBackground(new Color(255, 182, 193)); // Light pink color
        saveEmployeeRecord.setForeground(Color.WHITE);
        saveEmployeeRecord.setBorder(new LineBorder(new Color(255, 182, 193), 1));
        buttonPanel2.add(saveEmployeeRecord);

        // Add textGroup2 and buttonPanel2 to outputPanel
        outputPanel.add(textGroup2, BorderLayout.NORTH);
        outputPanel.add(buttonPanel2, BorderLayout.SOUTH);
        slimPanel3.add(outputPanel);
        add(slimPanel3);
        // ======== END OF outputPanel COMPONENTS ========

        // ======== START OF recordsPanel COMPONENTS ========
        recordDisplay = new JTextArea("", 12, 58); // Adjust text area size
        recordDisplay.setEditable(false);
        recordDisplay.setBackground(new Color(245, 245, 245)); // Match background color
        recordDisplay.setForeground(primaryTextColor);
        recordDisplay.setFont(minimalFont);

        // Add scroll bars for the text area
        JScrollPane scrollPane = new JScrollPane(recordDisplay);
        recordsPanel.add(scrollPane, BorderLayout.CENTER);
        add(recordsPanel);
        // ======== END OF recordsPanel COMPONENTS ========

        // Final setup: show the window
        setVisible(true);

        // Load data
        try {
            refreshData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		
		
		if (!inputNumberFieldsValid()) {
			return;
		}
		
		switch (command) {
			case "Add/Update Employee Record":
				// -removed- because the function is the same as the Save Employee Record button
				break;
				
			case "Calculate":
				if (!taxRateValid()) {
					return; // if there is an error
				}
				
				// proceed if no errors
				// get text input on hourly rate and hours worked, then parse (convert) to double data type and send to calculate method
				calculateRecord(Double.parseDouble(hourlyRate.getText()), Double.parseDouble(hoursWorked.getText()));
				eventsDisplay.setText("Calculations Complete");
				break;
				
			case "Save Employee Record":
				if (valuesChanged()) { // when changing values, needs to calculate again for data consistency when saving
					return;
				}
				
				
				if (!taxRateValid()) { // check if all characters in the tax rate input is a digit
					return; // if there is an error
				}
				
				if (netPay.getText() == "") { // there is an error in the calculations so do not record
					eventsDisplay.setText("Invalid Calculations");
					break;
				}
				
				// single string to hold all data in a record to be stored in the data file
				String record = (
						employeeID.getText() + ", " +
						employeeName.getText() + ", " +
						hourlyRate.getText() + ", " +
						hoursWorked.getText() + ", " +
						grossPay.getText() + ", " +
						taxRate.getText() + ", " +
						taxDeduction.getText() + ", " +
						netPay.getText()
				);
				
				eventsDisplay.setText("Employee Record Added/Updated");
				try {
					// separate method to save to file
					System.out.println("save to file successful");
					recordData(record);
				} 
				catch (IOException e1) {
					e1.printStackTrace();
				}
				
				break;
		}
	}
	
	public boolean inputNumberFieldsValid() {
		// for easy iteration
		JTextField[] inputFields = {employeeID, employeeName, hourlyRate, hoursWorked};
		JTextField[] numberFields = {employeeID, hourlyRate, hoursWorked};
		
		// for checking if an input field is empty
		for (JTextField inputField : inputFields) {
			if (inputField.getText().length() == 0) {
				eventsDisplay.setText("Empty Field(s)");
				return false;
			}
		}
		
		boolean hasDecimalPoint = false;
		// check each character
		for (int i = 0; i < numberFields.length; i++) {
			// convert string to character array and iterate on that array
			for (char c : numberFields[i].getText().toCharArray()) {
				// if character is a digit proceed to next element
				if (Character.isDigit(c)) {
					continue;
				}
				
				// if character is not a digit but a decimal point, and the component is Hourly Rate (Enable decimal input)
				if (c == '.' && i == 1) {
					// if there is no decimal point yet proceed to next element. If there is already a decimal point, print error. (only one decimal point allowed)
					if (hasDecimalPoint) {
						eventsDisplay.setText("Hourly Rate has more than one decimal point");
						return false;
					}
					else {
						hasDecimalPoint = true;
						continue;
					}
				}
				
				// if all characters are a digit or decimal point then skip the code below
				
				// if character is not a digit and not a decimal point
				switch (i) {
					case 0: // element 1 at index 0 is employee ID
						eventsDisplay.setText("Invalid Employee ID Input");
						return false;
					
					case 1: // element 2 at index 1 is hourly rate
						eventsDisplay.setText("Invalid Hourly Rate Input");
						return false;
					
					case 2: // element 3 at index 2 is hours worked
						eventsDisplay.setText("Invalid Hours Worked Input");
						return false;
				}
			}
			
			// check if employee name are all letters. convert string to character array
			for (char c : employeeName.getText().toCharArray()) {
				// if character is letter or space proceed to next element
				if (Character.isLetter(c) || c == ' ') {
					continue;
				}
				// a character is not a letter
				else {
					eventsDisplay.setText("Invalid Employee Name");
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean taxRateValid() { // check if all characters in the tax rate input is a digit
		if (taxRate.getText().length() == 0) {
			eventsDisplay.setText("Invalid Tax Rate");
			
			// clear the tax deduction and net pay values because the current tax rate input is invalid 
			taxDeduction.setText("");
			netPay.setText("");
			return false;
		}
		
		boolean hasDecimalPoint = false;
		// check if all characters in the tax rate input is a digit
		for (char c : taxRate.getText().toCharArray()) {
			// if character is not a digit but a decimal point (Enable decimal input)
			if (c == '.') {
				// if there is no decimal point yet proceed to next element. If there is already a decimal point, print error. (only one decimal point allowed)
				if (hasDecimalPoint) {
					eventsDisplay.setText("Tax Rate has more than one decimal point");
					return false;
				}
				else {
					hasDecimalPoint = true;
					continue;
				}
			}
			// if character is a digit proceed to next element
			else if (Character.isDigit(c)) {
				continue;
			}
			else {
				eventsDisplay.setText("Invalid Tax Rate");

				// clear the tax deduction and net pay values because the current tax rate input is invalid 
				taxDeduction.setText("");
				netPay.setText("");
				return false;
			}
		}
		
		// check if input is betweeen 0 and 100
		double taxRateNum = Double.parseDouble(taxRate.getText());
		if (taxRateNum < 0 || taxRateNum > 100) {
			eventsDisplay.setText("Tax Rate must be between 0 and 100");

			// clear the tax deduction and net pay values because the current tax rate input is invalid 
			taxDeduction.setText("");
			netPay.setText("");
			return false;
		}

		return true; // no errors
	}
	
	public void calculateRecord(double hourlyRateParam, double hoursWorkedParam) {
		// get gross pay by multiplying
		double grossPayNum = hourlyRateParam * hoursWorkedParam;
		grossPay.setText(String.valueOf(grossPayNum));
		
		// get tax rate from input. convert to double from string and divide by 100 because it is percentage
		double taxRateDouble = Double.parseDouble(taxRate.getText()) / 100;
		// get tax deduction by multiplying gross to tax rate
		double taxRateNum = (grossPayNum * taxRateDouble);
		taxDeduction.setText(String.valueOf(taxRateNum));
		
		// get net by subtracting tax deduction from gross pay
		double netPayNum = grossPayNum - taxRateNum;
		netPay.setText(String.valueOf(netPayNum));
		
		// for checking if the values changed. Because if they are changed, saving record should be disabled until record is recalculated
		previousEmployeeID = employeeID.getText();
		previousEmployeeName = employeeName.getText();
		previousHourlyRate = hourlyRate.getText();
		previousHoursWorked = hoursWorked.getText();
		previousTaxRate = taxRate.getText();
	}
	
	public boolean valuesChanged() {
		// check if there has been changes to the values
		// if there is, disable saving until recalculation
		if (!employeeID.getText().equals(previousEmployeeID)) {
			eventsDisplay.setText("Employee ID changed - repeat calculation.");
			return true;
		}
		else if (!employeeName.getText().equals(previousEmployeeName)) {
			eventsDisplay.setText("Employee Name changed - repeat calculation.");
			return true;
		}
		else if (!hourlyRate.getText().equals(previousHourlyRate)) {
			eventsDisplay.setText("Hourly Rate changed - repeat calculation.");
			return true;
		}
		else if (!hoursWorked.getText().equals(previousHoursWorked)) {
			eventsDisplay.setText("Hours Worked changed - repeat calculation.");
			return true;
		}
		else if (!taxRate.getText().equals(previousTaxRate)) {
			eventsDisplay.setText("Tax Rate changed - repeat calculation.");
			return true;
		}
		
		// there are no changes to the values
		return false;
	}
	
	public void refreshData() throws IOException {
		File file = new File(filename);
		
		if (!file.exists()) {
			return;
		}
		
		//===================Reading FROM File======================//
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		String line;
		String history = "";
		
		while ((line = reader.readLine()) != null) {
		    history += line + System.lineSeparator();
		}
		
		reader.close();
		recordDisplay.setText("Employee ID, Employee Name, Hourly Rate, Hours Worked, Gross Pay (₱), Tax Rate (%), Tax Deduction, Net Pay\n" + history);
	}
	
	public void recordData(String record) throws IOException {
		String line;
		String records = "";
		String[] splitLineRecord;
		String[] splitNewRecord;
		
		File file = new File(filename);		
		if (file.exists()) {
			//===================Reading FROM File======================//
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				// check if there is already a record with the same employee ID
				splitLineRecord = line.split(",");
				splitNewRecord = record.split(",");
				// if there is a copy - then skip this line. because it will be updated with new data
				if (splitLineRecord[0].equals(splitNewRecord[0])) {
					eventsDisplay.setText("Existing ID. Record Updated");
					continue;
				}
				
				records += line + System.lineSeparator();
			}
			
			records += record;
			reader.close();
		}
		else {
			records = record;
		}
		
		//===================Writing INTO File======================//
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write(records);
		
		writer.close();
		
		refreshData();
	}
	
	public static void main(String[] args) {
		new PayrollSystem();
	}
}