package edu.ucsb.cs56.projects.utilities.restaurant_list;
import javax.swing.*;
import java.awt.event.*;

/**
 * Action Performed for the EatScreen
 */ 
class EatListener implements ActionListener {
    GuiUserInput gui;
    JFrame frame;
    Food food;
    String[] types;

    public EatListener(JFrame frame, GuiUserInput gui, Food food, String[] types) {
        this.gui = gui;
        this.frame = frame;
        this.food = food;
        this.types = types;
    }

    public void actionPerformed(ActionEvent event) {
        EatPanel eatScreen = new EatPanel(this.frame, this.gui, this.food, this.types);
        this.gui.setPageTitle(eatScreen.getTitle());

        frame.getContentPane().removeAll();        
        frame.getContentPane().add(eatScreen.getPanel());
        frame.invalidate();
        frame.validate();
    }
}
