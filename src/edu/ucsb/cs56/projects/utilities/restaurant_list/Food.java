/**
   A program that finds open retaurants depending on the current time. User can also find restaurants that are open are a later hour. Users can also input in new restaurants.
   
   @author Ameya Savale
   @author Brandon Hammel
   @author Andrew Pang
   @author Thien Hoang
   @author Brenda Flores
   @version CS56, Winter 2016
 */
package edu.ucsb.cs56.projects.utilities.restaurant_list;

import java.util.*;
import java.io.*;

public class Food implements Serializable {

    ArrayList<Restaurant> allRestaurants = new ArrayList<Restaurant>();
    Restaurant restaurant = new Restaurant();

    /**
       noarg Constructor for objects of class Food
     */
    public Food() {
		boolean fileLoaded = true;
    
		try {
	    	fileLoaded = this.readSavedList();
		} catch(Exception e) {
	    	System.out.println("Could not load the file");
		}
	
		if (fileLoaded == false) {
	    	//Default objects to add to arrayLists
	    	Restaurant a = new Restaurant("9","22","Panda Express","805-683-1857","131,N. Fairview Ave,Goleta,CA,93117","chinese");
	    	Restaurant b = new Restaurant("0","23","Ming Dynasty","805-968-1308","290-G,Storke Road,Goleta,Ca,93117","chinese");
	    	Restaurant c = new Restaurant("0","24","Subway (I.V.)","805-685-8600","888,Embarcadero Del norte,Isla Vista,CA,93117","burgers");
	    	Restaurant d = new Restaurant("11","22","Javan's (I.V.)","805-968-2180","938,Embarcadero Del norte,Isla Vista,CA,93117","burgers");

	    	//Adding the default Restaurant objects to arraylist
	    	this.addNew(a);
	    	this.addNew(b);
	    	this.addNew(c);
	    	this.addNew(d);
		}
    }


    public boolean readSavedList() {
		boolean load = true;

		try {
	    	FileInputStream fileStream = new FileInputStream("RestaurantList.ser");
	    	ObjectInputStream is = new ObjectInputStream(fileStream);
	    	Object list = is.readObject();
	    	allRestaurants = (ArrayList) list;
		} catch(Exception ex) {
	    	load = false;
	    	System.out.println("Could not read the saved file.");
		}
		return load;
    }

    /** 
	goes through the correct arrayList and gets the 
	list of the different restaurant options that are open within the hour specified
	
	@param cuisine    The type of cuisine
	@param hour       The hour during which the restaurant should be open

	@return choice    The restaurant which the user wnats the information for
    */
    public String[] showOptions(String cuisine, String time) {
	
		String cuisineType = "";
		int start, end, presentTime = 0;
		ArrayList<String> cuisineList = new ArrayList<String>();
	
		presentTime = Integer.parseInt(time);

		for (int i = 0; i < allRestaurants.size(); i++) {
	    	cuisineType = (allRestaurants.get(i)).getType();
	    	start = Integer.parseInt(allRestaurants.get(i).getStartTime());
	    	end = Integer.parseInt(allRestaurants.get(i).getEndTime());

	    	if (cuisineType.equals(cuisine) && start <= presentTime && presentTime < end) {
				cuisineList.add(allRestaurants.get(i).getName());
	    	}
		}

		String[] chosenCuisine = new String[cuisineList.size() + 1];
		chosenCuisine[0] = "-Select Restaurant-";

		for (int j = 0; j < cuisineList.size(); j++) {
	    	chosenCuisine[j+1] = cuisineList.get(j);
		}
	
		return chosenCuisine;
    }
    
    /**
     *  Saves the arrayList of restaurant objects
     */

    public void saveList() {
		try {
	    	FileOutputStream fs = new FileOutputStream("RestaurantList.ser");
	    	ObjectOutputStream os = new ObjectOutputStream(fs);
	    	os.writeObject(allRestaurants);
	    	os.close();
	    	System.out.println("Saved");
		} catch(Exception ex) {
	    	ex.printStackTrace();
		}
    }

    /**
       shows all the information about the restaurant chosen by the user
       
       @param choice    The index of the restaurant the user wants to access
    */
    public String[] showAllInfo(String choice) {

		String[] restaurantInfo = new String[5];

		for (int i = 0; i < allRestaurants.size(); i++) {
	    	String restaurant = allRestaurants.get(i).getName();
	    	if (restaurant.equals(choice)) {
				restaurantInfo[0] = allRestaurants.get(i).getName();
				restaurantInfo[1] = allRestaurants.get(i).getStartTime();
				restaurantInfo[2] = allRestaurants.get(i).getEndTime();
				restaurantInfo[3] = allRestaurants.get(i).getAddress();
				restaurantInfo[4] = allRestaurants.get(i).getPhone();
	    	}
		}

		return restaurantInfo;
    }
    
    /**
       gives the current hour

       @return hour   current hour of the day
    */
    
    public int getHour() {
		Calendar hour = new GregorianCalendar();
		return hour.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
       creates a new restaurant object which is stored into the correct 
       arrayList with the properties inputed by the user
     */
    
    public void createNew(String[] info) {

		Restaurant r = new Restaurant();

		r.setStartTime(info[0]);
		r.setEndTime(info[1]);
		r.setName(info[2]);
		r.setPhone(info[3]);
		r.setAddress(info[4]);
		r.setType(info[5]);
        
		allRestaurants.add(r);
    }

    public void createCSVNew(String[] info)
    {
    	Restaurant r = new Restaurant();
    	String[] withoutQuotes = new String[6];

    	for (int i = 0; i < 6; i++) {
    		withoutQuotes[i] = info[i].substring(1, info[i].length() - 1);
    	}

		r.setStartTime(withoutQuotes[0]);
		r.setEndTime(withoutQuotes[1]);
		r.setName(withoutQuotes[2]);
		r.setPhone(withoutQuotes[3]);
		r.setAddress(withoutQuotes[4]);
		r.setType(withoutQuotes[5]);
        
		allRestaurants.add(r);
    }

    /**
     *  Gets the different cuisine types
     *
     *  @return cuisine  returns the array that contains the different cuisine types
     */
    
    public String[] getCuisineTypes() {
		ArrayList<String> cuisineTypes = new ArrayList<String>();
       	
		for (int i = 0; i < allRestaurants.size(); i++) {
	    	String type = (allRestaurants.get(i)).getType();
	     	if (cuisineTypes.contains(type) == false) {
		 		cuisineTypes.add(type);
 	    	}
		}
	
		String[] cuisine = new String[cuisineTypes.size() + 1];
		cuisine[0] = "-Select Cusine-";
		for (int i = 0; i < cuisineTypes.size(); i++) {
	    	cuisine[i+1] = cuisineTypes.get(i);
		}
	
		return cuisine;
    }
    
    /**
       Adds the default restaurant objects to the correct arrayList
    */
    public void addNew(Restaurant newRestaurant) {
       	
	   allRestaurants.add(newRestaurant);
    }
}
