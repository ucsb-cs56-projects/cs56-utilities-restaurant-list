/*
 *
 Review entry that contains the rating the author's name and review
 @author   John Rehbein
 @author   Colin Mai
 @version  CS56, Fall 2016
 */

package edu.ucsb.cs56.projects.utilities.restaurant_list;

import java.util.*;
import java.io.*;

// Google Places Helper library
import se.walkercrou.places.*;


import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;

import javax.swing.*;

/**
 * A class that holds restaurant reviews
 */
public class RatingTableCellPanel extends JPanel {
    private JLabel authorLabel;
    private JLabel ratingLabel, ratingScoreLabel;
    private JPanel ratingContainer; // This is so the rating label is always next to the ratingScoreLabel
    private JTextArea reviewText;

    /** 
     * Constructor for each review cell
     */
    
    public RatingTableCellPanel(Review googlePlacesReview) {
        
        //set up author Label
        authorLabel = new JLabel();
        add(authorLabel);
        setAuthor(googlePlacesReview.getAuthor());
        
        //set up rating Panel
        ratingLabel = new JLabel("Rating: ");
        ratingScoreLabel = new JLabel("No Rating");
        ratingContainer = new JPanel();
        setRating("" + googlePlacesReview.getRating());
        ratingContainer.add(ratingLabel);
        ratingContainer.add(ratingScoreLabel);
        add(ratingContainer);
        
        //set up Review text area
        reviewText = new JTextArea();
        reviewText.setLineWrap(true);
        reviewText.setWrapStyleWord(true);
        reviewText.setSize(400,200);
        setReview(googlePlacesReview.getText());
        add(reviewText);
        
    }
    /* Eventually Add this. Probably the best way to do it would be to make a Reviewable interface with stuff like getRating(), getReviewText(), etc and extend the Review class from the Google Places library and implement the interface on that and a YelpReview class. This would be a refactor friendly solution.
    public RatingTableCellPanel(Reviewable review) {
        
    }
    */
    
    // setters

    /**
     * If author is annoymous, print Anonymous
     */
    public void setAuthor(String author) {
        if (author == null) {
            authorLabel.setText("Anonymous");
        } else {
            authorLabel.setText(author);
        }
    }
    /**
     * If no rating, print no rating
     */ 
    public void setRating(String rating) {
        if (rating == null) {
            ratingScoreLabel.setText("No Rating");
        } else {
            ratingScoreLabel.setText(rating);
        }
    }
    /**
     * If no review, print no review
     */ 
    public void setReview(String review) {
        if (review == null) {
            reviewText.setText("No Review");
        } else {
            reviewText.setText(review);
        }
    }
}
