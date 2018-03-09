/*
 *
    Displays a window that displays the add new restaurant screen

    @author   Zihao Zhang
    @author   Hyun Bum Cho
    @version  CS56, Winter 2018
*/

package edu.ucsb.cs56.projects.utilities.restaurant_list;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

public class AddNewRestaurantPanel {
    JPanel newScreen, buttonScreen, titlePanel, screen;
    JLabel phoneTitle, nameTitle, addressTitle, startTitle, endTitle, typeTitle, menuTitle, pageTitle;
    JTextField name, address, phoneNumber, startTime, endTime, type, futureTime, futureLocation;
    JButton back;
    String[] info;
    JButton submitButton;
    GuiUserInput gui;
    Food food;

    public AddNewRestaurantPanel(JFrame frame, GuiUserInput gui, Food food) {
        this.gui = gui;
        this.food = food;
        
        info = new String[6];

        newScreen = new JPanel();
        newScreen.setLayout(new BorderLayout());
        newScreen.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        screen = new JPanel();
        screen.setLayout(new GridLayout(6,2));

        buttonScreen = new JPanel();
        titlePanel = new JPanel();
    
        pageTitle = new JLabel("Add a New Restaurant");

        nameTitle = new JLabel("Name");
        phoneTitle = new JLabel("Phone Number ex. xxx-xxx-xxxx");
        addressTitle = new JLabel("Address");
        startTitle = new JLabel("Start Time ex. 8 for 8:00 A.M.");
        endTitle = new JLabel("End Time ex. 20 for 8:00 P.M.");
        typeTitle = new JLabel("Type of Cuisine");
        menuTitle = new JLabel("Menu (if available)");
    
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
    
        back = new JButton("Go Back");
        back.addActionListener(new BackButtonListener(this.gui));

        // Textfields to read in user input.
        name = new JTextField(20);
        phoneNumber = new JTextField(15);
        address = new JTextField(30);
        startTime = new JTextField(10);
        endTime = new JTextField(10);
        type = new JTextField(20);

        // Set the verifiers for user input validation.
        phoneNumber.setInputVerifier(new PhoneVerifier(frame));
        startTime.setInputVerifier(new TimeVerifier(frame));
        endTime.setInputVerifier(new TimeVerifier(frame));

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
    }

    public JLabel getTitle() {
        return pageTitle;
    }

    public JPanel getPanel() {
        return newScreen;
    }

    /**
     * Submit the restaurants to the database
     */
    class SubmitButtonListener implements ActionListener {
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
            // EatScreen();
        }
    }
}
