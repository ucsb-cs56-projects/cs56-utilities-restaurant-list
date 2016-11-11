/**
 Displays a window that displays ratings in a verticle table format
@author   John Rehbein
@author   Colin Mai
@version  CS56, Fall 2016
*/

package edu.ucsb.cs56.projects.utilities.restaurant_list;

import java.util.*;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;

// Google Places Helper library
import se.walkercrou.places.*;

import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;

import javax.swing.*;

public class GuiRatings {
    private JFrame frame;
    private JScrollPane scrollPane;
    private Box reviewTable;
    
    public GuiRatings(ArrayList<Review> reviews) {
        //GUI Setup
        reviewTable = new Box(BoxLayout.Y_AXIS);
        frame = new JFrame("Reviews");
        
        scrollPane = new JScrollPane(reviewTable);
        
        addReviews(reviews);
        frame.add(scrollPane);
        
        //BoilerPlate
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setVisible(true);
    }
    public void addReviews(ArrayList<Review> reviews) {
        for (Review review : reviews) {
            RatingTableCellPanel cell = new RatingTableCellPanel(review);
            reviewTable.add(cell);
        }
    }
}
