/*
 *
    Displays a window that displays the add new future screen.

    @author   Zihao Zhang
    @author   Hyun Bum Cho
    @version  CS56, Winter 2018
*/

package edu.ucsb.cs56.projects.utilities.restaurant_list;

import se.walkercrou.places.*;
import java.net.MalformedURLException;
import java.io.*;

import java.net.URL;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;

import javax.swing.*;

/**
 * A class that represents GUI Future Panel
 */
public class FuturePanel {
    JFrame frame;
    GuiUserInput gui;
    JButton editButton, back;
    JComboBox restaurantList, cuisineList, futureRestaurant, futureCuisine;
    JLabel pageTitle, hour, place;;
    JPanel textPanel, textPanel2, boxPanel, titlePanel, buttonPanel, futureScreen, futurePanel;
    JTextField futureTime, futureLocation;
    Food food;
    String[] types;
    String cuisineChoice;

    public FuturePanel(JFrame frame, GuiUserInput gui, Food food, String[] types) {
        this.food = food;
        this.frame = frame;
        this.gui = gui;
        this.types = types;

        futureScreen = new JPanel();
        futureScreen.setLayout(new GridLayout(2,1));
        
        pageTitle = new JLabel("Find a Later Time");

        buttonPanel = new JPanel();
        
        futurePanel = new JPanel();
        futurePanel.setLayout(new BoxLayout(futurePanel, BoxLayout.Y_AXIS));
        
        textPanel = new JPanel();
        textPanel2 = new JPanel();
        boxPanel = new JPanel();
        titlePanel = new JPanel();

        hour = new JLabel("Hour (0-24), press enter after input:");
        place = new JLabel("Location (i.e. Isla Vista, CA), press enter after input:");
        
        futureTime = new JTextField(5);
        futureTime.addActionListener(new FutureTimeListener());
        futureTime.setInputVerifier(new TimeVerifier(frame));

        futureLocation = new JTextField(20);
        futureLocation.setEnabled(false);
        futureLocation.addActionListener(new FutureLocationListener());
        
        futureCuisine = new JComboBox();
        futureCuisine.setEnabled(false);
        futureCuisine.addActionListener(new FutureCuisineBoxListener());

        futureRestaurant = new JComboBox();
        futureRestaurant.setEnabled(false);
        futureRestaurant.addActionListener(new FutureListListener());
    

        back = new JButton("Go Back");
        back.addActionListener(new BackButtonListener(this.gui));
    
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

        futureScreen.add(futurePanel);
        futureScreen.add(buttonPanel);
    }

    public JLabel getTitle() {
        return pageTitle;
    }

    public JPanel getPanel() {
        return futureScreen;
    }

    /**
     * Modifies the actionPerformed to print out all the restaurants of that particular cuisine.
     */
    class FutureLocationListener implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            futureCuisine.removeAllItems();
            String[] futureCuisineList = types;
            for (int i = 0; i < futureCuisineList.length; i++) {
                futureCuisine.addItem(futureCuisineList[i]);
            }
            futureCuisine.setEnabled(true);
        }
    }

    /**
     * Turns on location
     */
    class FutureTimeListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            futureLocation.setEnabled(true);
        }
    }

    /**
     * Prints out the list of restaurants for that cuisine.
     */
    class FutureCuisineBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JComboBox cb = (JComboBox) event.getSource();
            String cuisine = (String) cb.getSelectedItem();
            futureRestaurant.removeAllItems();

            String time = futureTime.getText();
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

    /**
     * Looks for restaurants
     */
    class FutureListListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JComboBox cb = (JComboBox) event.getSource();
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

    /**
     * Sets up the gui and prints out all the information about the restaurant while adding a choice to go back, click on menu, and click on reviews.
     * @param cuisineName The type of cuisine selected
     */
    
    private void showChoiceFuture(String cuisineName) {
        
        //stores the restaurant's info in the array
        String[] restaurantInfo = food.showAllInfo(cuisineName);
        Place selectedRestaurant = food.getCuisineWithName(cuisineChoice).getPlacesInfo();
                
        JPanel choice = new JPanel();
        choice.setLayout(new BoxLayout(choice, BoxLayout.Y_AXIS));
        choice.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        JPanel infoPanel = new JPanel();
        if (selectedRestaurant != null) {
            infoPanel.setLayout(new GridLayout(9,2));
        } else {
            infoPanel.setLayout(new GridLayout(6,2));
        }
        
        
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        
        back = new JButton("Go Back");
        back.addActionListener(gui.new FutureListener(gui));
        
        JLabel nameTitle = new JLabel("Name");
        JLabel hoursTitleLabel = new JLabel("Hours");
        JLabel addressTitle = new JLabel("Address");
        JLabel phoneTitle = new JLabel("Phone");
        pageTitle = new JLabel("Restaurant Information");

        JLabel name = new JLabel(restaurantInfo[0]);
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
        
        JButton menu = new JButton("Menu");
        menu.addActionListener(new MenuButtonListener(this.frame, this.gui, this.food, this.cuisineChoice));
        
        //adds all the components to their respective JPanels
        titlePanel.add(pageTitle);
        
        infoPanel.add(nameTitle);
        infoPanel.add(name);
        infoPanel.add(hoursTitleLabel);
        if (selectedRestaurant != null) {
            for (Hours.Period hoursByDay : selectedRestaurant.getHours().getPeriods()) {
                infoPanel.add(new JLabel(hoursByDay.toString()));
            }
        } else {
            infoPanel.add(new JLabel("Operating hours not available"));
        }
        infoPanel.add(addressTitle);
        infoPanel.add(address);
        infoPanel.add(phoneTitle);
        infoPanel.add(phone);
        
        buttonPanel.add(menu);
        buttonPanel.add(back);

        choice.add(titlePanel);
        choice.add(imagePanel);
        choice.add(infoPanel);
        choice.add(buttonPanel);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(choice);
        frame.invalidate();
        frame.validate();
    }
}
