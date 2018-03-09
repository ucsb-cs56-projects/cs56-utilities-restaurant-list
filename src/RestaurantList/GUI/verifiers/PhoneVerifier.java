package edu.ucsb.cs56.projects.utilities.restaurant_list;

import javax.swing.*;
import java.awt.*;

/**
 * Ensure that phone number is entered in the right format
 */
class PhoneVerifier extends InputVerifier {
	public JFrame frame;
	public PhoneVerifier(JFrame frame) {
		this.frame = frame;
	}

	public boolean verify(JComponent input) {
		JTextField tf = (JTextField) input;
		String pn = tf.getText();
		try {
			String[] parts = pn.split("-");
			if (parts[0].length() != 3 || parts[1].length() != 3 || parts[2].length() != 4) {
			    input.setBackground(Color.red);
			    JOptionPane.showMessageDialog(frame, "Make sure you use this format: xxx-xxx-xxxx (include dashes)", "Formatting error", JOptionPane.ERROR_MESSAGE);
			    tf.setText(null);
			    return true;
			}
			for (int i = 0; i < 3; i++) {
			    Integer.parseInt(parts[i]);
			}
		} catch (Exception e) {
			input.setBackground(Color.red);
			JOptionPane.showMessageDialog(frame, "Make sure you use this format: xxx-xxx-xxxx (include dashes)", "Formatting error", JOptionPane.ERROR_MESSAGE);
			tf.setText(null);
			return true;
		}

		input.setBackground(UIManager.getColor("TextField.background"));
		return true;
	}
}