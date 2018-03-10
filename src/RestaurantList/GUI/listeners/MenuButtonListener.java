package edu.ucsb.cs56.projects.utilities.restaurant_list;
import javax.swing.*;
import java.awt.event.*;

/**
 * When a button is clicked, call MenuScreen
 */
class MenuButtonListener implements ActionListener {
    JFrame frame;
    Food food;
    MainPanel gui;
    String cuisineChoice;

    public MenuButtonListener(JFrame frame, MainPanel gui, Food food, String cuisineChoice) {
        this.frame = frame;
        this.gui = gui;
        this.food = food;
        this.cuisineChoice = cuisineChoice;
    }

    public void actionPerformed(ActionEvent event) {
        MenuPanel menuScreen = new MenuPanel(this.frame, this.gui, this.food, this.cuisineChoice);
        this.gui.setPageTitle(menuScreen.getTitle());

        frame.getContentPane().removeAll();        
        frame.getContentPane().add(menuScreen.getPanel());
        frame.invalidate();
        frame.validate();
    }
}