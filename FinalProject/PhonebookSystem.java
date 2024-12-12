import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PhonebookSystem extends JFrame implements ActionListener {
	
	// Declare GUI components
	private JTextField eventField, nameField, phoneField;
	private JTextArea displayArea;
	private String filename = "phonebook.txt", selectedContact = "";
	
	public PhonebookSystem() {
    //=================== Main Frame of the GUI =================//
    setTitle("Phonebook System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 400); // Set size of the window
    setLayout(new BorderLayout()); // Set layout for the window

    //=================== Initialize components =================//
    eventField = new JTextField();
    eventField.setEditable(false); // Make event field non-editable
    eventField.setFont(new Font("Arial", Font.ITALIC, 14));
    eventField.setBackground(Color.LIGHT_GRAY);
    eventField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    nameField = new JTextField();
    phoneField = new JTextField();

    displayArea = new JTextArea();
    displayArea.setEditable(false); // Make display area non-editable
    displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    displayArea.setBackground(new Color(240, 248, 255)); // Light blue background

    //=================== Panel to hold header and input areas =================//
    JPanel topPanels = new JPanel(new BorderLayout());
    add(topPanels, BorderLayout.NORTH);

    //=================== Header Panel for Event Log =================//
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBorder(BorderFactory.createTitledBorder("Event Log"));
    headerPanel.add(eventField);
    topPanels.add(headerPanel, BorderLayout.NORTH);

    //=================== Name and Phone Number Input =================//
    JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Contact Details"));
    inputPanel.add(new JLabel("Name:"));
    inputPanel.add(nameField);
    inputPanel.add(new JLabel("Phone:"));
    inputPanel.add(phoneField);
    topPanels.add(inputPanel, BorderLayout.SOUTH);

    //=================== Scroll Pane for the Contacts Display Area =================//
    JScrollPane scrollPane = new JScrollPane(displayArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Contacts"));
    add(scrollPane, BorderLayout.CENTER);

    //=================== Buttons Panel =================//
    JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Array of button labels
    String[] buttons = {"Add", "Search", "Delete", "Select", "Update"};
    for (String button : buttons) {
        JButton b = new JButton(button);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setFocusPainted(false); // Remove focus effect
        b.setBackground(new Color(100, 149, 237)); // Cornflower blue
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createBevelBorder(1)); // Add bevel border for button
        b.addActionListener(this); // Attach action listener to buttons
        buttonPanel.add(b);
    }
    add(buttonPanel, BorderLayout.SOUTH);

    //=================== Set the window to visible and load contacts =================//
    setVisible(true);
    loadContacts(); // Load contacts when the program starts
}

	// Handle actions when a button is clicked
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		// Clear the event log field
		eventField.setText("");
		
		// If name or phone fields are empty, show a message
		if (nameField.getText().isEmpty() || phoneField.getText().isEmpty()) {
			eventField.setText("Input field(s) are empty");
			return;
		}
		// Check if phone number is valid
		else if (phoneField.getText().length() != 11 || !phoneField.getText().startsWith("09")) {
			eventField.setText("Phone number must be 11 digits long starting from 09");
			return;
		}
		// Check if the phone number contains only digits
		for (char c : phoneField.getText().toCharArray()) {
			if (!Character.isDigit(c)) {
				eventField.setText("Phone number must contain only digits");
				return;
			}
		}
		
		// Perform actions based on the button clicked
		if (command.equals("Add")) {
			addContact(nameField.getText(), phoneField.getText());
		}
		else if (command.equals("Search")) {
			searchContact(nameField.getText(), phoneField.getText());
		}
		else if (command.equals("Delete")) {
			deleteContact(nameField.getText(), phoneField.getText());
		}
		else if (command.equals("Select")) {
			selectContact(nameField.getText(), phoneField.getText());
		}
		else if (command.equals("Update")) {
			updateContact(nameField.getText(), phoneField.getText());
		}
	}

	// Load contacts from the file and display them
	public void loadContacts() {
		String line;
		String contacts = "";
		
		try {
			File file = new File(filename);
			// If the file doesn't exist, create it
			if (!file.exists()) {
			    file.createNewFile();
			}
			// Read contacts from the file
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				contacts += line + System.lineSeparator();
			}
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		// Display the contacts in the text area
		displayArea.setText(contacts);
	}
	
	// Add a contact to the phonebook
	public void addContact(String name, String phone) {
		String line;
		String contacts = "";
		String[] splitLine;
		boolean alreadyExists = false;
		
		selectedContact = ""; // Reset selected contact
		
		try {
			File file = new File(filename);

			// If the file doesn't exist, create it
			if (!file.exists()) {
			    file.createNewFile();
			}
			
			// Read contacts from the file
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				splitLine = line.split(":");
				
				// If the contact already exists, show a message
				if (splitLine[0].equals(name) && splitLine[1].equals(phone)) {
					alreadyExists = true;
					break;
				}
				contacts += line + System.lineSeparator();
			}
			
			// If contact already exists, display a message and exit
			if (alreadyExists) {
				eventField.setText("Contact Already Exist");
				reader.close();
				return;
			}
			else {
				eventField.setText("Contact Added");
			}
			
			// Add the new contact to the list
			contacts += name + ":" + phone;
			
			// Write the updated contacts list to the file
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(contacts);
			
			reader.close();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Reload contacts and update the display
		loadContacts();
	}

	// Search for a contact by name and phone number
	public void searchContact(String name, String phone) {
		String line;
		String contacts = "";
		String[] splitLine;
		boolean alreadyExists = false;
		
		selectedContact = ""; // Reset selected contact
		
		try {
			File file = new File(filename);
			
			if (!file.exists()) {
			    file.createNewFile();
			}
			
			// Read contacts from the file
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				splitLine = line.split(":");
				
				// If the contact is found, mark as existing and add to the contacts list
				if (splitLine[0].equals(name) && splitLine[1].equals(phone)) {
					alreadyExists = true;
					continue;
				}
				contacts += line + System.lineSeparator();
			}
			
			// If contact is found, display the message and update the contacts list
			if (alreadyExists) {
				eventField.setText("Contact Searched");
				contacts = name + ":" + phone + System.lineSeparator() + contacts;
			}
			else {
				eventField.setText("Contact does not exist");
				reader.close();
				return;
			}
			
			// Write the updated contacts list to the file
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(contacts);
			
			reader.close();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Reload contacts and update the display
		loadContacts();
	}

	// Delete a contact from the phonebook
	public void deleteContact(String name, String phone) {
		String line;
		String contacts = "";
		String[] splitLine;
		boolean contactExist = false;
		
		selectedContact = ""; // Reset selected contact
		
		try {
			File file = new File(filename);
			
			if (!file.exists()) {
			    file.createNewFile();
			}
			
			// Read contacts from the file
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				splitLine = line.split(":");
				
				// If the contact is found, mark it for deletion
				if (splitLine[0].equals(name) && splitLine[1].equals(phone)) {
					contactExist = true;
					continue;
				}
				contacts += line + System.lineSeparator();
			}
			
			// Display message based on whether contact was found or not
			if (contactExist) {
				eventField.setText("Contact deleted");
			}
			else {
				eventField.setText("Contact does not exist");
			}
			
			// Write the updated contacts list to the file
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(contacts);
			
			reader.close();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Reload contacts and update the display
		loadContacts();
	}

	// Select a contact for further operations
	public void selectContact(String name, String phone) {
		String line;
		String contacts = "";
		String[] splitLine;

		try {
			File file = new File(filename);
			
			if (!file.exists()) {
			    file.createNewFile();
			}
			
			// Read contacts from the file
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				splitLine = line.split(":");
				
				// If contact is found, set it as selected and display the message
				if (splitLine[0].equals(name) && splitLine[1].equals(phone)) {
					searchContact(name, phone);
					selectedContact = name + ":" + phone;
					eventField.setText("Contact selected: " + name + ":" + phone);
					return;
				}
				contacts += line + System.lineSeparator();
			}
			
			// If contact not found, display a message
			eventField.setText("Contact does not exist");
			
			// Write the updated contacts list to the file
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(contacts);
			
			reader.close();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Reload contacts and update the display
		loadContacts();
	}

	// Update an existing contact's details
	public void updateContact(String name, String phone) {
		String line;
		String contacts = "";
		String[] splitLine;
		String[] splitSelectedContact;
		
		// Check if a contact is selected before updating
		if (selectedContact.isEmpty()) {
			eventField.setText("No contact selected - Please select a contact");
			return;
		}
		
		try {
			File file = new File(filename);
			
			if (!file.exists()) {
			    file.createNewFile();
			}
			
			// Read contacts from the file
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				splitLine = line.split(":");
				splitSelectedContact = selectedContact.split(":");
				
				// If the selected contact matches the current contact, update it
				if (splitLine[0].equals(splitSelectedContact[0]) && splitLine[1].equals(splitSelectedContact[1])) {
					contacts += name + ":" + phone + System.lineSeparator();
				} else {
					contacts += line + System.lineSeparator();
				}
			}
			
			// Write the updated contacts list to the file
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(contacts);
			
			reader.close();
			writer.close();
			
			// Set the selected contact to the updated one
			selectedContact = name + ":" + phone;
			eventField.setText("Contact Updated");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Reload contacts and update the display
		loadContacts();
	}
	
	public static void main(String[] args) {
		// Create and display the Phonebook System GUI
		new PhonebookSystem();
	}
}
