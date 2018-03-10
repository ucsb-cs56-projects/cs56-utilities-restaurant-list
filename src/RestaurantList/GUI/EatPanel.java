/*
 *
    The gui that is displayed when the user wants to find a place to eat

    @author   Zihao Zhang
    @author   Hyun Bum Cho
    @version  CS56, Winter 2018
*/

package edu.ucsb.cs56.projects.utilities.restaurant_list;

import se.walkercrou.places.*;
import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;

import javax.imageio.*;
import javax.swing.*;

import org.geonames.*;

public class EatPanel {
    final static String[] stateNames = {"Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina","North Dakota","Ohio","Oklahoma", "Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming"};
    boolean isNaturalLanguage = true;
    
    String cuisineChoice;
    JFrame frame;
    GuiUserInput gui;
    Food food;
    JPanel boxPanel, buttonPanel, titlePanel, eatScreen;
    JLabel pageTitle, place, stateTitleLabel, cityTitleLabel;
    JButton back, searchToggleButton, searchButton, menu, reviewsButton;
    JComboBox cuisineList, restaurantList, stateDropDown, cityDropDown;
    JPanel naturalLanguageSearchPanel, stateCitySearchPanel, searchControlContainer;
    JTextField location;
    String[] types;

    public EatPanel(JFrame frame, GuiUserInput gui, Food food, String[] types) {
        this.gui = gui;
        this.food = food;
        this.frame = frame;
        this.types = types;

        place = new JLabel("Location (i.e. Isla Vista, CA), press enter after input:");
        searchToggleButton = new JButton("By City");
        searchToggleButton.addActionListener(e -> { toggleSearchType(); });
        stateCitySearchPanel = new JPanel();

        stateTitleLabel = new JLabel("State");
        cityTitleLabel = new JLabel("City");
        stateDropDown = new JComboBox(stateNames);
        cityDropDown = new JComboBox();
        searchControlContainer = new JPanel();

        searchButton = new JButton("Search");
        searchButton.addActionListener( e -> {
            String lct, type = (String)cuisineList.getSelectedItem();
            if(isNaturalLanguage) {
                lct = (String)location.getText();
            } else {
                lct = stateDropDown.getSelectedItem() + ", " + cityDropDown.getSelectedItem();
            }
            System.out.println("Location: " + lct + "   Type: " + type);
            search(lct, type);
        });

        setupCitySearchPanel();

        eatScreen = new JPanel();
        eatScreen.setLayout(new BoxLayout(eatScreen, BoxLayout.Y_AXIS));
        
        boxPanel = new JPanel();
        buttonPanel = new JPanel();
        titlePanel = new JPanel();
        
        pageTitle = new JLabel("Find a restaurant open at the current time.");
        
        back = new JButton("Go Back");
        back.addActionListener(new BackButtonListener(this.gui));
        
        location = new JTextField(20);
        location.addActionListener(new locationListener());
        
        cuisineList = new JComboBox(types);
        
        restaurantList = new JComboBox();
        restaurantList.setEnabled(false);
        
        cuisineList.addActionListener(new ComboBoxListener());
        
        restaurantList.addActionListener(new restaurantListListener());
        
        naturalLanguageSearchPanel = new JPanel();
        naturalLanguageSearchPanel.add(place);
        naturalLanguageSearchPanel.add(location);
        if (searchControlContainer.getComponentCount() == 0) {
            searchControlContainer.add(naturalLanguageSearchPanel);
        }
        boxPanel.add(cuisineList);
        boxPanel.add(restaurantList);
        
        buttonPanel.add(searchToggleButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(back);
        titlePanel.add(pageTitle);
        
        eatScreen.add(titlePanel);
        eatScreen.add(searchControlContainer);
        eatScreen.add(boxPanel);
        eatScreen.add(buttonPanel);
    }

    public JLabel getTitle() {
        return pageTitle;
    }

    public JPanel getPanel() {
        return eatScreen;
    }

    public void toggleSearchType() {
        searchControlContainer.removeAll();
        if (isNaturalLanguage) {
            searchToggleButton.setText("Natural Language");
            searchControlContainer.add(stateCitySearchPanel);
        } else {
            searchToggleButton.setText("By City");
            searchControlContainer.add(naturalLanguageSearchPanel);
        }
        searchControlContainer.paintImmediately(searchControlContainer.getVisibleRect());
        isNaturalLanguage = !isNaturalLanguage;
    }

    /**
     * Setup the city search panel GUI
     */
    public void setupCitySearchPanel() {
        stateCitySearchPanel.add(stateTitleLabel);
        stateDropDown.addActionListener( e -> {
            JComboBox cb = (JComboBox)e.getSource();
            String state = (String)cb.getSelectedItem();
            setupCityComboBox(state);
        });
        stateCitySearchPanel.add(stateDropDown);
        stateCitySearchPanel.add(cityTitleLabel);
        stateCitySearchPanel.add(cityDropDown);
        
        cityDropDown.setEnabled(true);
        
    }

    /**
     * Setup the city combo (list) GUI
     */
    public void setupCityComboBox(String state) {
        
        //clear old cities because we're adding new ones
        cityDropDown.removeAllItems();
        
        //Perform search for cities using the geonames webservice
        WebService.setUserName("N7Alpha"); // add your username here
         
        ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
        final String[] citiesOnlyCodes = {"PPL", "PPLC", "PPLA"};
         searchCriteria.setQ("United States, " + state); // Set query
        searchCriteria.setFeatureCodes(citiesOnlyCodes);
        ToponymSearchResult searchResult = null;
        try {
            searchResult = WebService.search(searchCriteria);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        ArrayList<String> cityNames = new ArrayList<String>();
        for (Toponym toponym : searchResult.getToponyms()) {
            System.out.println("Location: " + toponym.getName() + ", " + toponym.getCountryName());
            cityNames.add(toponym.getName());
        }
        Collections.sort(cityNames);
         for (String name : cityNames) {
             cityDropDown.addItem(name);
         }
        cityDropDown.setEnabled(true);
    }

    /**
     * Performs a search and populates restaurant drop down menu with the results
     */
    public void search(String lct, String type) {
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
    
    /**
     *  Shows all the information of the restaurant chosen by the user
     *  @param cuisineName  the type of cuisine chosen
     */
    public void showChoiceEat(String cuisineName) {
        String[] restaurantInfo = food.showAllInfo(cuisineName);
        Place selectedRestaurant = food.getCuisineWithName(cuisineChoice).getPlacesInfo();
        
        frame.getContentPane().removeAll();
    
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
        back.addActionListener(new EatListener(this.frame, this.gui, this.food, this.types));

        /* JButton image = new JButton("Image");
        image.addActionListener(new imageListener());
        */
        
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
            
            JLabel imageLabel = new JLabel(imagePic, JLabel.CENTER);
            imagePanel.add(imageLabel, BorderLayout.CENTER);

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        menu = new JButton("Menu");
        menu.addActionListener(new MenuButtonListener(this.frame, this.gui, this.food, this.cuisineChoice));
        reviewsButton = new JButton("Reviews");
        reviewsButton.addActionListener(new reviewsButtonListener());
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
        buttonPanel.add(reviewsButton);
        buttonPanel.add(back);

        choice.add(titlePanel);
        choice.add(imagePanel);
        choice.add(infoPanel);
        choice.add(buttonPanel);

        frame.getContentPane().add(choice);
        frame.invalidate();
        frame.validate();
    }

    /**
     * Action Performed for printing the number of ratings for each restaurant
     */ 
    class reviewsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
           Restaurant selectedRestaurant = food.getCuisineWithName(cuisineChoice);
            System.out.println("NUMBER OF RATINGS FOR " + selectedRestaurant.getName() + " : " + selectedRestaurant.getReviews().size());
            GuiRatings gr = new GuiRatings(selectedRestaurant.getReviews()); // creates a window with the reviews
        }
        
    }

    /**
     *  Gets the restaurant selected by user and passes it to showChoiceEat
     *  from the EatScreen
     */
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
     * ActionPerformed for the cuisine list button
     */
    class locationListener implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            cuisineList.setEnabled(true);
        }
    }
}
