/*
 *
    Displays a window that displays the add new edit screen.
    This class currently does not work as expected and can use a lot of work! Only the GUI is set up.

    @author   Zihao Zhang
    @author   Hyun Bum Cho
    @version  CS56, Winter 2018
*/

package edu.ucsb.cs56.projects.utilities.restaurant_list;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

/**
 * A class that represents GUI Edit Panel
 */
public class EditPanel {
    JFrame frame;
    MainPanel gui;
    JButton editButton, back;
    JComboBox restaurantList, cuisineList;
    JPanel boxPanel, buttonPanel, titlePanel, JPanel, editScreen;
    JLabel pageTitle;

    public EditPanel(JFrame frame, MainPanel gui, Food food) {
        this.frame = frame;
        this.gui = gui;

        editScreen = new JPanel();
        editScreen.setLayout(new BoxLayout(editScreen, BoxLayout.Y_AXIS));
        
        boxPanel = new JPanel();
        buttonPanel = new JPanel();
        titlePanel = new JPanel();
        
        pageTitle = new JLabel("Editing Existing Restaurant Entries");
        
        String[] type = food.getCuisineTypes();
        back = new JButton("Go Back");
        back.addActionListener(new BackButtonListener(gui));

        editButton = new JButton("Edit");
        editButton.addActionListener(new BackButtonListener(gui)); // Temporary
        editButton.setEnabled(false);

        cuisineList = new JComboBox(type);
        
        restaurantList = new JComboBox();
        restaurantList.setEnabled(false);
        
        cuisineList.addActionListener(new ComboBoxListener());  
        
        restaurantList.addActionListener(new EditRestaurantListListener());
        
        boxPanel.add(cuisineList);
        boxPanel.add(restaurantList);
        buttonPanel.add(back);
        buttonPanel.add(editButton);
        titlePanel.add(pageTitle);
        
        editScreen.add(titlePanel);
        editScreen.add(boxPanel);
        editScreen.add(buttonPanel);
    }

    public JLabel getTitle() {
        return pageTitle;
    }

    public JPanel getPanel() {
        return editScreen;
    }

    /**
     * Enable the edit button to turn on. However, the button
     * only leads back to the home screen.
     */
    class EditRestaurantListListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            JComboBox cb = (JComboBox)event.getSource();
            int index = restaurantList.getSelectedIndex();
            System.out.println(index);
            if(index > 0){
                String restaurantChoice = (String)cb.getSelectedItem();
                System.out.println(restaurantChoice);
                editButton.setEnabled(true);
            }
        }
    }
}
