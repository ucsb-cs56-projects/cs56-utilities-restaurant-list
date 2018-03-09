package edu.ucsb.cs56.projects.utilities.restaurant_list;
import javax.swing.*;
import java.awt.event.*;

/**
 * Goes back to the starting screen whenever the back button is clicked
 */ 

class BackButtonListener implements ActionListener {
	GuiUserInput gui;
	public BackButtonListener(GuiUserInput gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent event) {
    	gui.setup();
	}
}
    