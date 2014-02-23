/**
	Gui for the restaurant program, interacts with user

	@author   Ameya Savale
	@author   Brandon Hammel
	@author   Andrew Pang
	@version  issue485, CS56, Winter 2014  
 */

package edu.ucsb.cs56.projects.utilities.restaurant_list;

import java.util.*;
import java.io.*;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

public class GuiUserInput extends JPanel {

    JLabel restaurant, pageTitle;
    JPanel  eatScreen, future;
    JButton back;
    JFrame frame;
    Food food = new Food();
    JTextField name, address, phoneNumber, startTime, endTime, type, futureTime;
    JComboBox cuisineList, restaurantList, futureCuisine, futureRestaurant;
    String time;
    String[] info = new String[6];
       
    //Constructor
    public GuiUserInput() {
   
		frame = new JFrame("Restaurant Finder");
	
		setup();
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,400);
		frame.setVisible(true);
    }

    public static void main(String[] args) {
		GuiUserInput gui = new GuiUserInput();
    }

    /**
     *  Initial screen when the gui is run
     *
     */

    public void setup() {
		frame.getContentPane().removeAll();
		JPanel titlePanel = new JPanel();
		pageTitle = new JLabel("Find a Restaurant");

		JButton eatButton = new JButton("Eat");
		JPanel eatPanel = new JPanel();
	
		JButton newButton = new JButton("Add New");
		JPanel newPanel = new JPanel();

		JButton newCSVButton = new JButton("Add New from CSV file");
		JPanel newCSVPanel = new JPanel();
	
		JButton futureButton = new JButton("Future Time");
		JPanel futurePanel = new JPanel();

		JButton exitButton = new JButton("Exit");
		JPanel exitPanel = new JPanel();

		JPanel initialScreen = new JPanel();
		initialScreen.setLayout(new BoxLayout(initialScreen, BoxLayout.PAGE_AXIS));

		eatButton.addActionListener(new EatListener());
		newButton.addActionListener(new NewListener());
		newCSVButton.addActionListener(new NewCSVListener());
		futureButton.addActionListener(new FutureListener());
		exitButton.addActionListener(new ExitListener());
	
		eatPanel.add(eatButton);
		newPanel.add(newButton);
		newCSVPanel.add(newCSVButton);
		futurePanel.add(futureButton);
		exitPanel.add(exitButton);

		titlePanel.add(pageTitle);

		initialScreen.add(titlePanel);
		initialScreen.add(eatPanel);
		initialScreen.add(newPanel);
		initialScreen.add(newCSVPanel);
		initialScreen.add(futurePanel);
		initialScreen.add(exitPanel);

		frame.getContentPane().add(initialScreen);
		frame.invalidate();
		frame.validate();
    }

    /**
     *  Changes the gui to the addNewResataurant Screen
     */

    public void AddNewScreen() {
		JPanel newScreen = new JPanel();
		newScreen.setLayout(new BorderLayout());
		newScreen.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		JPanel screen = new JPanel();
		screen.setLayout(new GridLayout(6,2));

		JPanel buttonScreen = new JPanel();
		JPanel titlePanel = new JPanel();
	
		pageTitle = new JLabel("Add a New Restaurant");

		JLabel nameTitle = new JLabel("Name");
		JLabel phoneTitle = new JLabel("Phone Number ex. xxx-xxx-xxxx");
		JLabel addressTitle = new JLabel("Address");
		JLabel startTitle = new JLabel("Start Time ex. 8 for 8:00 A.M.");
		JLabel endTitle = new JLabel("End Time ex. 20 for 8:00 P.M.");
		JLabel typeTitle = new JLabel("Type of Cuisine");
	
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new submitButtonListener());
	
		back = new JButton("Go Back");
		back.addActionListener(new backButtonListener());

		// Textfields to read in user input.
		name = new JTextField(20);
		phoneNumber = new JTextField(15);
		address = new JTextField(30);
		startTime = new JTextField(10);
		endTime = new JTextField(10);
		type = new JTextField(20);

		// Set the verifiers for user input validation.
		phoneNumber.setInputVerifier(new phoneVerifier());
		startTime.setInputVerifier(new timeVerifier());
		endTime.setInputVerifier(new timeVerifier());

		screen.add(nameTitle);
		screen.add(name);
	
		screen.add(phoneTitle);
		screen.add(phoneNumber);
	
		screen.add(addressTitle);
		screen.add(address);

		screen.add(startTitle);
		screen.add(startTime);

		screen.add(endTitle);
		screen.add(endTime);

		screen.add(typeTitle);
		screen.add(type);

		buttonScreen.add(submitButton);
		buttonScreen.add(back);

		titlePanel.add(pageTitle);

		newScreen.add(titlePanel, BorderLayout.NORTH);
		newScreen.add(screen, BorderLayout.CENTER);
		newScreen.add(buttonScreen, BorderLayout.SOUTH);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newScreen);
		frame.invalidate();
		frame.validate();
    }

    class phoneVerifier extends InputVerifier {
    	public boolean verify(JComponent input) {
    		JTextField tf = (JTextField) input;
    		String pn = tf.getText();
    		try {
    			String[] parts = pn.split("-");
    			if (parts[0].length() != 3 || parts[1].length() != 3 || parts[2].length() != 4) {
    				input.setBackground(Color.red);
    				JOptionPane.showMessageDialog(frame, "Make sure you use this format: xxx-xxx-xxxx (include dashes)",
    											  "Formatting error", JOptionPane.ERROR_MESSAGE);
    				return false;
    			}
    			for (int i = 0; i < 3; i++) {
    				Integer.parseInt(parts[i]);
    			}
    		} catch (Exception e) {
    			input.setBackground(Color.red);
    			JOptionPane.showMessageDialog(frame, "Make sure you use this format: xxx-xxx-xxxx (include dashes)",
    											  "Formatting error", JOptionPane.ERROR_MESSAGE);
    			return false;
    		}
    		input.setBackground(UIManager.getColor("TextField.background"));
    		return true;
    	}
    }

    class timeVerifier extends InputVerifier {
    	public boolean verify(JComponent input) {
    		JTextField tf = (JTextField) input;
    		try {
    			int time = Integer.parseInt(tf.getText());
    			if (time >= 0 && time <= 24) {
	    			input.setBackground(UIManager.getColor("TextField.background"));
	    			return true;
	    		}
	    		else {
	    			input.setBackground(Color.red);
	    			JOptionPane.showMessageDialog(frame, "Please set a valid time (0-24)",
    										  	  "Formatting error", JOptionPane.ERROR_MESSAGE);
	    			return false;
	    		}
    		} catch (NumberFormatException e) {
    			input.setBackground(Color.red);
    			JOptionPane.showMessageDialog(frame, "Please set a valid time (0-24)",
    										  "Formatting error", JOptionPane.ERROR_MESSAGE);
	    		return false;
    		}
    	}
    }

    class submitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	String n = name.getText();
	    	info[0] = n;

	    	String p = phoneNumber.getText();
	    	info[3] = p;
	   
	    	String a = address.getText();
	    	info[4] = a;
	   
	    	String st = startTime.getText();
	    	info[1] = st;

	    	String et = endTime.getText();
	    	info[2] = et;

	    	String cuisineType = type.getText();
	    	info[5] = cuisineType;
	
	    	food.createNew(info);
	    	food.saveList();
	    	EatScreen();
		}
    }

    public void FutureScreen() {
		frame.getContentPane().removeAll();
		future = new JPanel();
		future.setLayout(new GridLayout(2,1));

		pageTitle = new JLabel("Find a Later Time");

		JPanel buttonPanel = new JPanel();
	
		JPanel futurePanel = new JPanel();
		futurePanel.setLayout(new BoxLayout(futurePanel, BoxLayout.Y_AXIS));
	
		JPanel textPanel = new JPanel();
		JPanel boxPanel = new JPanel();
		JPanel titlePanel = new JPanel();

		JLabel hour = new JLabel("Hour (0-24), press enter after input:");

		futureTime = new JTextField(5);
		futureTime.addActionListener(new futureTimeListener());
	
		futureCuisine = new JComboBox();
		futureCuisine.setEnabled(false);
		futureCuisine.addActionListener(new futureCuisineBoxListener());

		futureRestaurant = new JComboBox();
		futureRestaurant.setEnabled(false);
		futureRestaurant.addActionListener(new futureListListener());
	
		back = new JButton("Go Back");
		back.addActionListener(new backButtonListener());
	
		titlePanel.add(pageTitle);
		textPanel.add(hour);
		textPanel.add(futureTime);
		boxPanel.add(futureCuisine);
		boxPanel.add(futureRestaurant);
		buttonPanel.add(back);
	
		futurePanel.add(titlePanel);
		futurePanel.add(textPanel);
		futurePanel.add(boxPanel);

		future.add(futurePanel);
		future.add(buttonPanel);

		frame.getContentPane().add(future);
		frame.invalidate();
		frame.validate();
    }

    class futureTimeListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	time = futureTime.getText();
	    	futureCuisine.removeAllItems();
	    	String[] futureCuisineList = food.getCuisineTypes();
	    	for (int i = 0; i < futureCuisineList.length; i++) {
				futureCuisine.addItem(futureCuisineList[i]);
	    	}
	    	futureCuisine.setEnabled(true);
		}
    }

    class futureCuisineBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	JComboBox cb = (JComboBox)event.getSource();
	    	String cuisine = (String)cb.getSelectedItem();
	    	futureRestaurant.removeAllItems();
	    	String[] listOfRestaurants = food.showOptions(cuisine, time); 
	    	for (int i = 0; i < listOfRestaurants.length; i++) {
				System.out.println(listOfRestaurants[i]);
				futureRestaurant.addItem(listOfRestaurants[i]);
	    	}
	    	futureRestaurant.setEnabled(true);
		}
    }

    
    class futureListListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	JComboBox cb = (JComboBox)event.getSource();
	    	int index = futureRestaurant.getSelectedIndex();
	    	System.out.println(index);
	    	if (index > 0) {
	    		String restaurantChoice = (String)cb.getSelectedItem();
				System.out.println(restaurantChoice);
				showChoice(restaurantChoice);
	    	}
		}
    }

    public void EatScreen() {
		eatScreen = new JPanel();
		eatScreen.setLayout(new BoxLayout(eatScreen, BoxLayout.Y_AXIS));
		frame.getContentPane().removeAll();

		JPanel boxPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel titlePanel = new JPanel();

		pageTitle = new JLabel("Find a restaurant open at the current time");
	
		String[] type = food.getCuisineTypes();
		back = new JButton("Go Back");
		back.addActionListener(new backButtonListener());

		//JComboBox listing the cuisines
		JComboBox cuisineList = new JComboBox(type);

		restaurantList = new JComboBox();
		restaurantList.setEnabled(false);

		cuisineList.addActionListener(new comboBoxListener());	
	
		restaurantList.addActionListener(new restaurantListListener());
	
		boxPanel.add(cuisineList);
		boxPanel.add(restaurantList);
		buttonPanel.add(back);
		titlePanel.add(pageTitle);

		eatScreen.add(titlePanel);
		eatScreen.add(boxPanel);
		eatScreen.add(buttonPanel);
	
		frame.getContentPane().add(eatScreen);
		frame.invalidate();
		frame.validate();
    }

    /**
     *  Get cuisine chosen by user and adds restaurant names
     *  to the second JComboBox
     */
    //inner class
    class comboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	JComboBox cb = (JComboBox)event.getSource();
	    
	    	String type = (String)cb.getSelectedItem();
	    	String currentTime = String.valueOf(food.getHour());
	    
	    	String[] listOfRestaurants = food.showOptions(type, currentTime);
	    	restaurantList.removeAllItems();
	    
	    	for (int i = 0; i < listOfRestaurants.length; i++) {
				System.out.println(listOfRestaurants[i]);
				restaurantList.addItem(listOfRestaurants[i]);
	    	}

	    	restaurantList.setEnabled(true);
		}
    }

    /**
     *  Gets the restaurant selected by user and passes it to showChoice
     *  from the EatScreen
     */
    //inner class
    class restaurantListListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	JComboBox cb = (JComboBox)event.getSource();
	    	int index = restaurantList.getSelectedIndex();
	    	System.out.println(index);
	    	if (index > 0) {
	    		String restaurantChoice = (String)cb.getSelectedItem();
				System.out.println(restaurantChoice);
				showChoice(restaurantChoice);
	    	} 
		}
    }
    
    /**
     *  Shows all the information of the restaurant chosen by the user
     *  
     *  @param cuisineName  the type of cuisine chosen
     */
    public void showChoice(String cuisineName) {
		frame.getContentPane().removeAll();
	
		JPanel choice = new JPanel();
		choice.setLayout(new BoxLayout(choice, BoxLayout.Y_AXIS));
		choice.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(6,2));

		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
	
		back = new JButton("Go Back");
		back.addActionListener(new backButtonListener());

		JLabel nameTitle = new JLabel("Name");
		JLabel startTimeTitle = new JLabel("Opens");
		JLabel endTimeTitle = new JLabel("Closes");
		JLabel addressTitle = new JLabel("Address");
		JLabel phoneTitle = new JLabel("Phone");
		pageTitle = new JLabel("Restaurant Information");

		//stores the restaurant's info in the array
		String[] restaurantInfo = food.showAllInfo(cuisineName);
	
		//converts the closing time to a 12 hour time frame
		int c = Integer.parseInt(restaurantInfo[2]);
		String t = String.valueOf(c-12);

		JLabel name = new JLabel(restaurantInfo[0]);
		JLabel startTime = new JLabel(restaurantInfo[1] + " A.M.");
		JLabel endTime = new JLabel(t + " P.M.");
		JLabel address = new JLabel(restaurantInfo[3]);
		JLabel phone = new JLabel(restaurantInfo[4]);
	
		//adds all the components to their respective JPanels
		titlePanel.add(pageTitle);

		infoPanel.add(nameTitle);
		infoPanel.add(name);
		infoPanel.add(startTimeTitle);
		infoPanel.add(startTime);
		infoPanel.add(endTimeTitle);
		infoPanel.add(endTime);
		infoPanel.add(addressTitle);
		infoPanel.add(address);
		infoPanel.add(phoneTitle);
		infoPanel.add(phone);
	
		buttonPanel.add(back);
	
		//Adding the panels to the choice panel
		choice.add(titlePanel);
		choice.add(infoPanel);
		choice.add(buttonPanel);

		frame.getContentPane().add(choice);
		frame.invalidate();
		frame.validate();
    }

    //Parses a CSV file and populates database.
    public void readCSV(File file) {
    	BufferedReader br = null;
    	String line = "";
    	String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    	 
    	try {
    		br = new BufferedReader(new FileReader(file));
    		while ((line = br.readLine()) != null) {
    			info = line.split(csvSplitBy);
    			food.createCSVNew(info);
    		}
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}

    	food.saveList();
    	EatScreen();
  	}
    

    //Goes back to the starting screen whenever the back button is clicked
    class backButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	setup();
		}
    }
    
    //Goes back to the eating screen 
    class backToChoiceButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	EatScreen();
		}
    }

    class EatListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	EatScreen();
		}
    }

    class NewListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	AddNewScreen();
		}
    }

    class NewCSVListener implements ActionListener {	
		public void actionPerformed(ActionEvent event) {
	    	final JFileChooser fc = new JFileChooser();
	    	int returnVal = fc.showOpenDialog(null);

        	if (returnVal == JFileChooser.APPROVE_OPTION) {
            	File file = fc.getSelectedFile();
            	System.out.println("Opening: " + file.toString());
            	readCSV(file);
        	}
        	else
        		System.out.println("File open cancelled.");
		}
    }

    class FutureListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	FutureScreen();
		}
    }

    class ExitListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		System.exit(0);
    	}
    }
}
