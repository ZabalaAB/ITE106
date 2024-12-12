import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PhonebookSystem extends JFrame implements ActionListener {
	
	private JTextField eventField, nameField, phoneField;
	private JTextArea displayArea;
	private String filename = "phonebook.txt", selectedContact = "";
	
	public PhonebookSystem() {
    //=================== Main Frame ng GUI =================//
    setTitle("Phonebook System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 400);
    setLayout(new BorderLayout());

    //=================== Initialize ung mga variables/objects =================//
    eventField = new JTextField();
    eventField.setEditable(false);
    eventField.setFont(new Font("Arial", Font.ITALIC, 14));
    eventField.setBackground(Color.LIGHT_GRAY);
    eventField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    nameField = new JTextField();
    phoneField = new JTextField();

    displayArea = new JTextArea();
    displayArea.setEditable(false);
    displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    displayArea.setBackground(new Color(240, 248, 255)); // Light blue

    //=================== will hold the header and input panels =================//
    JPanel topPanels = new JPanel(new BorderLayout());
    add(topPanels, BorderLayout.NORTH);

    //=================== Header Panel =================//
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

    //=================== Scroll Pane for Display Area =================//
    JScrollPane scrollPane = new JScrollPane(displayArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Contacts"));
    add(scrollPane, BorderLayout.CENTER);

    //=================== Buttons =================//
    JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    String[] buttons = {"Add", "Search", "Delete", "Select", "Update"};
    for (String button : buttons) {
        JButton b = new JButton(button);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBackground(new Color(100, 149, 237)); // Cornflower blue
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createBevelBorder(1));
        b.addActionListener(this);
        buttonPanel.add(b);
    }
    add(buttonPanel, BorderLayout.SOUTH);

    //=================== Frame Visibility =================//
    setVisible(true);
    loadContacts();
}


	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		// e-clear lang yung nakasulat
		eventField.setText("");
		
		//
		// Kung may empty kahit isa sa name or phone number. Disregard lang yung command
		if (nameField.getText().isEmpty() || phoneField.getText().isEmpty()) {
			eventField.setText("Input field(s) are empty");
			return;
		}
		// Kung kulang ang input sa phone number
		else if (phoneField.getText().length() != 11 || !phoneField.getText().startsWith("09")) {
			eventField.setText("Phone number must be 11 digits long starting from 09");
			return;
		}
		// Check kung lahat ba ng input sa phone number ay digits
		for (char c : phoneField.getText().toCharArray()) {
			if (!Character.isDigit(c)) {
				eventField.setText("Phone number must contain only digits");
				return;
			}
		}
		
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
																																																																																																																								                                                                                                                                                                                                                                                                                                                   					                                       			// Gawa ni Mark Vincent D. Lanada BSCS2A
	public void loadContacts() {
		String line;
		String contacts = "";
		
		try {
			File file = new File(filename);
			// Check kung nag e-exist na yung file natin, kung indi gagawin.
			if (!file.exists()) {
			    file.createNewFile();
			}
			//===================Read FROM File======================//
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				contacts += line + System.lineSeparator();
			}
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		displayArea.setText(contacts);
	}
	
	public void addContact(String name, String phone) {
		
		String line;
		String contacts = "";
		String[] splitLine;
		boolean alreadyExists = false;
		
		selectedContact = "";
		
		try {
			File file = new File(filename);

			if (!file.exists()) {
			    file.createNewFile();
			}
			
			//===================Read FROM File======================//
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				splitLine = line.split(":");
				
				if (splitLine[0].equals(name) && splitLine[1].equals(phone)) {
					alreadyExists = true;
					break;
				}
				contacts += line + System.lineSeparator();
			}
			
			if (alreadyExists) {
				eventField.setText("Contact Already Exist");
				reader.close();
				return;
			}
			else {
				eventField.setText("Contact Added");
			}
			
			contacts += name + ":" + phone;
			
			//===================Write INTO File======================//
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(contacts);
			
			reader.close();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		loadContacts();
	}
	
	public void searchContact(String name, String phone) {
		
		String line;
		String contacts = "";
		String[] splitLine;
		boolean alreadyExists = false;
		
		selectedContact = "";
		
		try {
			File file = new File(filename);
			
			if (!file.exists()) {
			    file.createNewFile();
			}
			
			//===================Read FROM File======================//
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				splitLine = line.split(":");
				
				if (splitLine[0].equals(name) && splitLine[1].equals(phone)) {
					alreadyExists = true;
					continue;
				}
				contacts += line + System.lineSeparator();
			}
			
			if (alreadyExists) {
				eventField.setText("Contact Searched");
				contacts = name + ":" + phone + System.lineSeparator() + contacts;
			}
			else {
				eventField.setText("Contact does not exist");
				reader.close();
				return;
			}
			
			//===================Write INTO File======================//
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(contacts);
			
			reader.close();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		loadContacts();
	}

	public void deleteContact(String name, String phone) {
		
		String line;
		String contacts = "";
		String[] splitLine;
		boolean contactExist = false;
		
		selectedContact = "";
		
		try {
			File file = new File(filename);
			
			if (!file.exists()) {
			    file.createNewFile();
			}
			
			//===================Read FROM File======================//
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				splitLine = line.split(":");
				
				if (splitLine[0].equals(name) && splitLine[1].equals(phone)) {
					contactExist = true;
					continue;
				}
				contacts += line + System.lineSeparator();
			}
			
			if (contactExist) {
				eventField.setText("Contact deleted");
			}
			else {
				eventField.setText("Contact does not exist");
			}
			
			//===================Write INTO File======================//
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(contacts);
			
			reader.close();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		loadContacts();
	}
	
	public void selectContact(String name, String phone) {
		
		String line;
		String contacts = "";
		String[] splitLine;

		try {
			File file = new File(filename);
			
			if (!file.exists()) {
			    file.createNewFile();
			}
			
			//===================Read FROM File======================//
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				splitLine = line.split(":");
				
				if (splitLine[0].equals(name) && splitLine[1].equals(phone)) {
					searchContact(name, phone);
					selectedContact = name + ":" + phone;
					eventField.setText("Contact selected: " + name + ":" + phone);
					return;
				}
				contacts += line + System.lineSeparator();
			}
			
			eventField.setText("Contact does not exist");
			
			//===================Write INTO File======================//
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(contacts);
			
			reader.close();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		loadContacts();
	}
	
	public void updateContact(String name, String phone) {
		
		String line;
		String contacts = "";
		String[] splitLine;
		String[] splitSelectedContact;
		
		if (selectedContact.isEmpty()) {
			eventField.setText("No contact selected - Please select a contact");
			return;
		}
		
		try {
			File file = new File(filename);
			
			if (!file.exists()) {
			    file.createNewFile();
			}
			
			//===================Read FROM File======================//
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				splitLine = line.split(":");
				splitSelectedContact = selectedContact.split(":");
				
				if (splitLine[0].equals(splitSelectedContact[0]) && splitLine[1].equals(splitSelectedContact[1])) {
					eventField.setText(
							"Contact: " + 
							splitSelectedContact[0] + ":" + splitSelectedContact[1] + 
							" updated to " + name + ":" + phone);
					selectedContact = name + ":" + phone;
					continue;
				}
				
				contacts += line + System.lineSeparator();
			}
			
			contacts = selectedContact + System.lineSeparator() + contacts;
			selectedContact = "";
			
			//===================Write INTO File======================//
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(contacts);
			
			reader.close();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		loadContacts();
	}
	
	public static void main(String[] args) {
		new PhonebookSystem();
	}
}