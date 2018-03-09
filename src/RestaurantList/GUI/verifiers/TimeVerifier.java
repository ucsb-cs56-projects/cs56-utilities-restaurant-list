package edu.ucsb.cs56.projects.utilities.restaurant_list;

import javax.swing.*;
import java.awt.*;


/**
 * Ensure that time is entered in the right format
 */
class TimeVerifier extends InputVerifier {
	public JFrame frame;
	public TimeVerifier(JFrame frame) {
		this.frame = frame;
	}

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
			    JOptionPane.showMessageDialog(frame, "Please set a valid time (0-24)", "Formatting error", JOptionPane.ERROR_MESSAGE);
			    tf.setText(null);
			    return true;
			}
	    } catch (NumberFormatException e) {
			input.setBackground(Color.red);
			JOptionPane.showMessageDialog(frame, "Please set a valid time (0-24)", "Formatting error", JOptionPane.ERROR_MESSAGE);
			tf.setText(null);
			return true;
	    }
    }
}