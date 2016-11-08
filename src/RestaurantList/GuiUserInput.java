/**
	Gui for the restaurant program, interacts with user

	@author   Ameya Savale
	@author   Brandon Hammel
	@author   Andrew Pang
	@author   Thien Hoang
	@author   Brenda Flores
	@version  CS56, Winter 2016  

	@author   Timothy Kwong
	@author   Alan Tran
	@version  CS56, Summer 2016
 
    @author   John Rehbein
    @author   Colin Mai
    @version  CS56, Fall 2016
 */

package edu.ucsb.cs56.projects.utilities.restaurant_list;

import java.util.*;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;



import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;

import javax.swing.*;

public class GuiUserInput extends JPanel {

    JLabel restaurant, pageTitle;
    JPanel  eatScreen, editScreen, future, menuScreen; 
    JButton back, edit, menu, reviewsButton, image, locationSearchSubmitButton;
    JFrame frame;
    Food food = new Food();
    JTextField name, address, phoneNumber, startTime, endTime, type, futureTime, location, futureLocation;
    JComboBox cuisineList, restaurantList, futureCuisine, futureRestaurant;
    String time;
    String[] info = new String[6];
    String cuisineChoice;
    String[] types = new String[]{"Mexican","Chinese","Thai","Sushi Bars","Seafood","Fast Food","Sandwiches","Pizza","Italian","Coffee & Tea","Vegetarian"};
    Restaurant selectedRestaurant;
    
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
     *  Initial home screen when the gui is run
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

		JButton editButton = new JButton("Editing Existing Entries");
		JPanel editPanel = new JPanel();

		JButton newCSVButton = new JButton("Load from CSV file");
		JPanel newCSVPanel = new JPanel();

		JButton CSVSaveButton = new JButton("Save to CSV file");
		JPanel CSVSavePanel = new JPanel();
	
		JButton futureButton = new JButton("Future Time");
		JPanel futurePanel = new JPanel();

		JButton exitButton = new JButton("Exit");
		JPanel exitPanel = new JPanel();

		JPanel initialScreen = new JPanel();
		initialScreen.setLayout(new BoxLayout(initialScreen, BoxLayout.PAGE_AXIS));

		eatButton.addActionListener(new EatListener());
		newButton.addActionListener(new NewListener());
		editButton.addActionListener(new EditListener());
		newCSVButton.addActionListener(new NewCSVListener());
		CSVSaveButton.addActionListener(new CSVSaveListener());
		futureButton.addActionListener(new FutureListener());
		exitButton.addActionListener(new ExitListener());

		
		titlePanel.add(pageTitle);
		eatPanel.add(eatButton);
		newPanel.add(newButton);
		editPanel.add(editButton);
		newCSVPanel.add(newCSVButton);
		CSVSavePanel.add(CSVSaveButton);
		futurePanel.add(futureButton);
		exitPanel.add(exitButton);

		initialScreen.add(titlePanel);
		initialScreen.add(eatPanel);
		initialScreen.add(newPanel);
		initialScreen.add(editPanel);
		initialScreen.add(newCSVPanel);
		initialScreen.add(CSVSavePanel);
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
		JLabel menuTitle = new JLabel("Menu (if available)");
	
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

    /**
     * Ensure that phone number is entered in the right format
     */
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
		    tf.setText(null);
		    return true;
		}
		for (int i = 0; i < 3; i++) {
		    Integer.parseInt(parts[i]);
		}
	    } catch (Exception e) {
		input.setBackground(Color.red);
		JOptionPane.showMessageDialog(frame, "Make sure you use this format: xxx-xxx-xxxx (include dashes)",
					      "Formatting error", JOptionPane.ERROR_MESSAGE);
		tf.setText(null);
		return true;
	    }
	    input.setBackground(UIManager.getColor("TextField.background"));
	    return true;
        }
    }

    /**
     * Ensure that time is entered in the right format
     */
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
		    tf.setText(null);
		    return true;
		}
	    } catch (NumberFormatException e) {
		input.setBackground(Color.red);
		JOptionPane.showMessageDialog(frame, "Please set a valid time (0-24)",
					      "Formatting error", JOptionPane.ERROR_MESSAGE);
		tf.setText(null);
		return true;
	    }
        }
    }

    /**
     * Submit the restaurants to the database
     */
    class submitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
		    String st = startTime.getText();
		    info[0] = st;
		    
		    String et = endTime.getText();
		    info[1] = et;
		    
		    String n = name.getText();
		    info[2] = n;
		    
		    String p = phoneNumber.getText();
		    info[3] = p;
		    
		    String a = address.getText();
		    info[4] = a;
		    
		    String cuisineType = type.getText();
		    String properCuisineType = cuisineType.substring(0,1).toUpperCase() + cuisineType.substring(1).toLowerCase();
		    info[5] = properCuisineType;
		    
		    food.createNew(info);
		    food.saveList();
		    EatScreen();
		}
    }
    
    /**
     * Allow for Editing (Work in Progress)
     */
    class EditListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    EditScreen();
	}
    }
    
    /**
     * Set up the Edit Screen
     * NEED MAJOR WORK!
     */

    public void EditScreen(){
	editScreen = new JPanel();
	editScreen.setLayout(new BoxLayout(editScreen, BoxLayout.Y_AXIS));
	frame.getContentPane().removeAll();
	
	JPanel boxPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JPanel titlePanel = new JPanel();
	
	pageTitle = new JLabel("Editing Existing Restaurant Entries");
	
	String[] type = food.getCuisineTypes();
	back = new JButton("Go Back");
	back.addActionListener(new backButtonListener());


	//NEED MAJOR WORK RIGHT HERE
	edit = new JButton("Edit");
	edit.addActionListener(new backButtonListener());
	edit.setEnabled(false);
	//JComboBox listing the cuisines
	cuisineList = new JComboBox(type);
	
	restaurantList = new JComboBox();
	restaurantList.setEnabled(false);
	
	cuisineList.addActionListener(new comboBoxListener());	
	
	restaurantList.addActionListener(new EditRestaurantListListener());
	
	boxPanel.add(cuisineList);
	boxPanel.add(restaurantList);
	buttonPanel.add(back);
	buttonPanel.add(edit);
	titlePanel.add(pageTitle);
	
	editScreen.add(titlePanel);
	editScreen.add(boxPanel);
	editScreen.add(buttonPanel);
		
	frame.getContentPane().add(editScreen);
	frame.invalidate();
	frame.validate();
    }

    /**
     * Enable the edit button to turn on. However, the button
     * only leads back to the home screen.
     * NEED MAJOR WORK!
     */
    class EditRestaurantListListener implements ActionListener{
	public void actionPerformed(ActionEvent event){
	    JComboBox cb = (JComboBox)event.getSource();
	    int index = restaurantList.getSelectedIndex();
	    System.out.println(index);
	    if(index > 0){
		String restaurantChoice = (String)cb.getSelectedItem();
		System.out.println(restaurantChoice);
		edit.setEnabled(true);
	    }
	}
    }
    
    /**
     * Set up the Future Screen
     */
    public void FutureScreen() {
		frame.getContentPane().removeAll();
		future = new JPanel();
		future.setLayout(new GridLayout(2,1));
		
		pageTitle = new JLabel("Find a Later Time");

		JPanel buttonPanel = new JPanel();
		
		JPanel futurePanel = new JPanel();
		futurePanel.setLayout(new BoxLayout(futurePanel, BoxLayout.Y_AXIS));
		
		JPanel textPanel = new JPanel();
		JPanel textPanel2 = new JPanel();
		JPanel boxPanel = new JPanel();
		JPanel titlePanel = new JPanel();

		JLabel hour = new JLabel("Hour (0-24), press enter after input:");

		JLabel place = new JLabel("Location (i.e. Isla Vista, CA), press enter after input:");
		
		futureTime = new JTextField(5);
		futureTime.addActionListener(new futureTimeListener());
		futureTime.setInputVerifier(new timeVerifier());

		futureLocation = new JTextField(20);
		futureLocation.setEnabled(false);
		futureLocation.addActionListener(new futureLocationListener());
		
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

		textPanel2.add(place);
		textPanel2.add(futureLocation);
		
		boxPanel.add(futureCuisine);
		boxPanel.add(futureRestaurant);
		buttonPanel.add(back);
	
		futurePanel.add(titlePanel);
		futurePanel.add(textPanel);
		futurePanel.add(textPanel2);
		futurePanel.add(boxPanel);

		future.add(futurePanel);
		future.add(buttonPanel);

		frame.getContentPane().add(future);
		frame.invalidate();
		frame.validate();
    }

    class futureLocationListener implements ActionListener{
	public void actionPerformed(ActionEvent event) {
	    futureCuisine.removeAllItems();
	    String[] futureCuisineList = types;
	    for (int i = 0; i < futureCuisineList.length; i++) {
		futureCuisine.addItem(futureCuisineList[i]);
	    }
	    futureCuisine.setEnabled(true);
	}
    }
    
    class futureTimeListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    futureLocation.setEnabled(true);
	}
    }



    class futureCuisineBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	JComboBox cb = (JComboBox)event.getSource();
	    	String cuisine = (String)cb.getSelectedItem();
	    	futureRestaurant.removeAllItems();

		time = futureTime.getText();
		String lct = (String)futureLocation.getText();
		
		food.clearEntries();
		food.populateRestaurantsDatabase(cuisine, lct);
		
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
				cuisineChoice = restaurantChoice;
				showChoiceFuture(restaurantChoice);
	    	}
		}
    }


    public void showChoiceFuture(String cuisineName) {
        frame.getContentPane().removeAll();
        
        JPanel choice = new JPanel();
        choice.setLayout(new BoxLayout(choice, BoxLayout.Y_AXIS));
        choice.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(6,2));
        
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        
        back = new JButton("Go Back");
        back.addActionListener(new FutureListener());
        
        /*	image = new JButton("Image");
         image.addActionListener(new imageListener());
         */
        
        JLabel nameTitle = new JLabel("Name");
        JLabel startTimeTitle = new JLabel("Opens");
        JLabel endTimeTitle = new JLabel("Closes");
        JLabel addressTitle = new JLabel("Address");
        JLabel phoneTitle = new JLabel("Phone");
        pageTitle = new JLabel("Restaurant Information");
        
        //stores the restaurant's info in the array
        String[] restaurantInfo = food.showAllInfo(cuisineName);
        
        int closingTime = Integer.parseInt(restaurantInfo[2]);
        //Convert closing time to 12 hour time frame if needed
        String t;
        if (closingTime > 12) {
            t = "" + (closingTime - 12);
        } else {
            t = "" + closingTime;
        }
        JLabel name = new JLabel(restaurantInfo[0]);
        JLabel startTime = new JLabel(restaurantInfo[1] + " A.M.");
        JLabel endTime = new JLabel(t + " P.M."); // TODO these wrongly assume AM & PM easy fix though
        JLabel address = new JLabel(restaurantInfo[3]);
        JLabel phone = new JLabel(restaurantInfo[4]);
        
        try {
            URL url = new URL(restaurantInfo[5]);
            BufferedImage img = ImageIO.read(url);
            ImageIcon imagePic = new ImageIcon(img);
            
            JLabel imageLabel = new JLabel(imagePic);
            imagePanel.add(imageLabel);
            
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        menu = new JButton("Menu");
        menu.addActionListener(new menuButtonListener());
        
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
        
        
        //buttonPanel.add(back);
        buttonPanel.add(menu);
        //	buttonPanel.add(image);
        buttonPanel.add(back);
        //Adding the panels to the choice panel
        choice.add(titlePanel);
        choice.add(imagePanel);
        choice.add(infoPanel);
        choice.add(buttonPanel);
        
        frame.getContentPane().add(choice);
        frame.invalidate();
        frame.validate();
    }
    
    class menuButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    MenuScreen();
	}
    }
        
    public void MenuScreen() {
        frame.getContentPane().removeAll();
        menuScreen = new JPanel();
        menuScreen.setLayout(new BoxLayout(menuScreen, BoxLayout.Y_AXIS));
        //	menuScreen.setBorder
        
        String[] restaurantInfo = food.showAllInfo(cuisineChoice);
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JLabel menuLabel = new JLabel(restaurantInfo[6]);
        
        pageTitle = new JLabel("Menu");
        back = new JButton("Back");
        back.addActionListener(new backButtonListener());
        
        titlePanel.add(pageTitle);
        buttonPanel.add(back);
        
        menuScreen.add(titlePanel);
        menuScreen.add(menuLabel);
        if(restaurantInfo[5].equals("")){
            JLabel menuEmptyLabel = new JLabel("This restaurant does not have a menu online.");
            menuScreen.add(menuEmptyLabel);
        }
        menuScreen.add(buttonPanel);
        
        frame.getContentPane().add(menuScreen);
        frame.invalidate();
        frame.validate();
    }
    
    public void EatScreen() {
        eatScreen = new JPanel();
        eatScreen.setLayout(new BoxLayout(eatScreen, BoxLayout.Y_AXIS));
        frame.getContentPane().removeAll();
        
        JPanel textPanel = new JPanel();
        JPanel boxPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel titlePanel = new JPanel();
        
        pageTitle = new JLabel("Find a restaurant open at the current time.");
        
        JLabel place = new JLabel("Location (i.e. Isla Vista, CA), press enter after input:");
        //String[] type = food.getCuisineTypes();
        back = new JButton("Go Back");
        back.addActionListener(new backButtonListener());
        
        //JComboBox listing the cuisines
        location = new JTextField(20);
        location.addActionListener(new locationListener());
        
        locationSearchSubmitButton = new JButton("Submit");
        locationSearchSubmitButton.addActionListener(new locationListener());
        
        
        cuisineList = new JComboBox(types);
        cuisineList.setEnabled(false);
        
        restaurantList = new JComboBox();
        restaurantList.setEnabled(false);
        
        cuisineList.addActionListener(new comboBoxListener());
        
        restaurantList.addActionListener(new restaurantListListener());
        
        textPanel.add(place);
        textPanel.add(location);
        textPanel.add(locationSearchSubmitButton);
        boxPanel.add(cuisineList);
        boxPanel.add(restaurantList);
        buttonPanel.add(back);
        titlePanel.add(pageTitle);
        
        eatScreen.add(titlePanel);
        eatScreen.add(textPanel);
        eatScreen.add(boxPanel);
        eatScreen.add(buttonPanel);
        
        frame.getContentPane().add(eatScreen);
        frame.invalidate();
        frame.validate();
    }

    class locationListener implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            cuisineList.setEnabled(true);
        }
    }
    /**
     *  Get cuisine chosen by user and adds restaurant names
     *  to the second JComboBox
     */
    //inner class
    class comboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
	    	JComboBox cb = (JComboBox)event.getSource();

		String lct = (String)location.getText();
	    	String type = (String)cb.getSelectedItem();
	    	String currentTime = String.valueOf(food.getHour());
            
		food.clearEntries();
            String storedTitle = pageTitle.getText();
            pageTitle.setText("Loading...");
            // Cause it doesn't update in time otherwise
            pageTitle.paintImmediately(pageTitle.getVisibleRect());
            // TODO: This blocks the main thread
		food.populateRestaurantsDatabase(type, lct);
            pageTitle.setText(storedTitle);
            pageTitle.paintImmediately(pageTitle.getVisibleRect());
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
     *  Gets the restaurant selected by user and passes it to showChoiceEat
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
			cuisineChoice = restaurantChoice;
			showChoiceEat(restaurantChoice);
            
		    } 
		}
    }
    
    /**
     *  Shows all the information of the restaurant chosen by the user
     *  
     *  @param cuisineName  the type of cuisine chosen
     */
    public void showChoiceEat(String cuisineName) {
		frame.getContentPane().removeAll();
	
		JPanel choice = new JPanel();
		
		choice.setLayout(new BoxLayout(choice, BoxLayout.Y_AXIS));
		choice.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(6,2));

		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		JPanel imagePanel = new JPanel(new BorderLayout());

		back = new JButton("Go Back");
		back.addActionListener(new EatListener());

		/*	image = new JButton("Image");
		image.addActionListener(new imageListener());
		*/
		
		JLabel nameTitle = new JLabel("Name");
		JLabel startTimeTitle = new JLabel("Opens");
		JLabel endTimeTitle = new JLabel("Closes");
		JLabel addressTitle = new JLabel("Address");
		JLabel phoneTitle = new JLabel("Phone");
		pageTitle = new JLabel("Restaurant Information");

		//stores the restaurant's info in the array
		String[] restaurantInfo = food.showAllInfo(cuisineName);
        
		//converts the closing time to a 12 hour time frame if needed
		int closingTime = Integer.parseInt(restaurantInfo[2]);
        String t;
        if (closingTime > 12) {
            t = "" + (closingTime - 12);
        } else {
            t = "" + closingTime;
        }
		

		JLabel name = new JLabel(restaurantInfo[0]);
		JLabel startTime = new JLabel(restaurantInfo[1] + " A.M.");
		JLabel endTime = new JLabel(t + " P.M."); // TODO this is wrong
		JLabel address = new JLabel(restaurantInfo[3]);
		JLabel phone = new JLabel(restaurantInfo[4]);

		try {
		    URL url = new URL(restaurantInfo[5]);
		    BufferedImage img = ImageIO.read(url);
		    ImageIcon imagePic = new ImageIcon(img);
		    
		    JLabel imageLabel = new JLabel(imagePic, JLabel.CENTER);
		    imagePanel.add(imageLabel, BorderLayout.CENTER);

		}
		catch (MalformedURLException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}

		menu = new JButton("Menu");
		menu.addActionListener(new menuButtonListener());
        reviewsButton = new JButton("Reviews");
        reviewsButton.addActionListener(new reviewsButtonListener());
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
		buttonPanel.add(menu);
        buttonPanel.add(reviewsButton);
	
		//buttonPanel.add(back);
		//	buttonPanel.add(image);
		buttonPanel.add(back);
		//Adding the panels to the choice panel
		choice.add(titlePanel);
		choice.add(imagePanel);
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
    
    class reviewsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
           Restaurant selectedRestaurant = food.getCuisineWithName(cuisineChoice);
            System.out.println("NUMBER OF RATINGS FOR " + selectedRestaurant.getName() + " : " + selectedRestaurant.getReviews().size());
            GuiRatings gr = new GuiRatings(selectedRestaurant.getReviews()); // creates a window with the reviews
        }
        
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

    class CSVSaveListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    try {
		FileWriter fw = new FileWriter("RestaurantList.csv");
		PrintWriter pw = new PrintWriter(fw);
		for (int i = 0; i < food.allRestaurants.size(); i++) {
		    String startTime = food.allRestaurants.get(i).getStartTime();
		    String endTime = food.allRestaurants.get(i).getEndTime();
		    String name = food.allRestaurants.get(i).getName();
		    String phone = food.allRestaurants.get(i).getPhone();
		    String address = food.allRestaurants.get(i).getAddress();
		    String type = food.allRestaurants.get(i).getType();
		    pw.println('"' + startTime + '"' + "," +
			       '"' + endTime + '"' + "," +
			       '"' + name + '"' + "," +
			       '"' + phone + '"' + "," +
			       '"' + address + '"' + "," +
			       '"' + type + '"');
		    pw.flush();
		}
		pw.close();
		fw.close();
		JOptionPane.showMessageDialog(frame, "Restaurant list saved to RestaurantList.csv",
    					      "Save successful", JOptionPane.INFORMATION_MESSAGE);
	    } catch (IOException ex) {
		ex.printStackTrace();
	    }
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
