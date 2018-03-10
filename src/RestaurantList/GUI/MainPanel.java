/*
 *
	Gui for the restaurant program, interacts with user

    @author   Zihao Zhang
    @author   Hyun Bum Cho
    @version  CS56, Winter 2018

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

import se.walkercrou.places.*;
import org.geonames.*;

import edu.ucsb.cs56.projects.utilities.YelpAPI.Dotenv;

/**
 * The GUI class that generates the Yelp Interface
 */
public class MainPanel extends JPanel {

    JLabel pageTitle;
    JFrame frame;
    Food food = new Food();
    String[] types = new String[]{"Mexican","Chinese","Thai","Sushi Bars","Seafood","Fast Food","Sandwiches","Pizza","Italian","Coffee & Tea","Vegetarian"};

    /**
	 * The constructor that creates the entire JFrame
     */
    public MainPanel() {
   
		frame = new JFrame("Restaurant Finder");
	
		setup();
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setVisible(true);
    }

    public static void main(String[] args) {
		Dotenv.load();
		MainPanel gui = new MainPanel();
    }

    /**
     *  Initial home screen when the gui is run
     *
     */
    public void setup() {
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

		eatButton.addActionListener(new EatListener(frame, this, food, types));
		newButton.addActionListener(new NewListener(this));
		editButton.addActionListener(new EditListener(this));
		newCSVButton.addActionListener(new NewCSVListener());
		CSVSaveButton.addActionListener(new CSVSaveListener());
		futureButton.addActionListener(new FutureListener(this));
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

		frame.getContentPane().removeAll();
		frame.getContentPane().add(initialScreen);
		frame.invalidate();
		frame.validate();
    }
    
    public void setPageTitle(JLabel pageTitle) {
    	this.pageTitle = pageTitle;
    }


	/**
	 * Action Performed to add a new screen
	 */ 

    class NewListener implements ActionListener {
    	MainPanel gui;

    	public NewListener(MainPanel gui) {
        	this.gui = gui;
    	}

		public void actionPerformed(ActionEvent event) {
	    	AddNewRestaurantPanel newPanel = new AddNewRestaurantPanel(frame, this.gui, food);

			MainPanel.this.setPageTitle(newPanel.getTitle());

			frame.getContentPane().removeAll();
			frame.getContentPane().add(newPanel.getPanel());
			frame.invalidate();
			frame.validate();

		}
    }

    /**
     * Allow for Editing
     */
    class EditListener implements ActionListener {
    	MainPanel gui;

    	public EditListener(MainPanel gui) {
        	this.gui = gui;
    	}

		public void actionPerformed(ActionEvent event) {
		    EditPanel editScreen = new EditPanel(frame, this.gui, food);
		    MainPanel.this.setPageTitle(editScreen.getTitle());

			frame.getContentPane().removeAll();
            frame.getContentPane().add(editScreen.getPanel());
	        frame.invalidate();
	        frame.validate();
		}
    }
    
    /**
	 * Action Performed for the FutureScreen
	 */ 
    class FutureListener implements ActionListener {
    	MainPanel gui;

    	public FutureListener(MainPanel gui) {
        	this.gui = gui;
    	}

		public void actionPerformed(ActionEvent event) {
			FuturePanel futureScreen = new FuturePanel(frame, this.gui, food, types);
			MainPanel.this.setPageTitle(futureScreen.getTitle());

			frame.getContentPane().removeAll();
			frame.getContentPane().add(futureScreen.getPanel());
	        frame.invalidate();
	        frame.validate();
		}
    }

    /**
	 * Parses a CSV file and populates database.
     */
    public void readCSV(File file) {
    	BufferedReader br = null;
    	String line = "";
    	String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    	 
    	try {
    		br = new BufferedReader(new FileReader(file));
    		while ((line = br.readLine()) != null) {
    			String[] info = line.split(csvSplitBy);
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

    	EditPanel editScreen = new EditPanel(frame, this, food);
	    pageTitle = editScreen.getTitle();

		frame.getContentPane().removeAll();
        frame.getContentPane().add(editScreen.getPanel());
        frame.invalidate();
        frame.validate();
  	}

	/**
	 * Action Performed for opening the file
	 */ 
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

	/**
	 * Action Performed to print out all the information about the restaurant
	 */ 

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

	/**
	 * Action Performed for the exit button
	 */ 
    class ExitListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		System.exit(0);
    	}
    }
}
