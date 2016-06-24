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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import edu.ucsb.cs56.projects.utilities.YelpAPI.YelpAPI;
import edu.ucsb.cs56.projects.utilities.YelpAPI.NameAndID;

public class Food implements Serializable {

    ArrayList<Restaurant> allRestaurants = new ArrayList<Restaurant>();
    
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
		    this.populateRestaurantsDatabase("Mexican","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Chinese","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Thai","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Fast Food","Isla Vista, CA");
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
		System.out.format("Present Time = %d%n",presentTime);
		
		for (int i = 0; i < allRestaurants.size(); i++) {
		    cuisineType = (allRestaurants.get(i)).getType();
		    start = Integer.parseInt(allRestaurants.get(i).getStartTime());
		    end = Integer.parseInt(allRestaurants.get(i).getEndTime());
		    
		    if (cuisineType.equals(cuisine) && ((start <= presentTime && presentTime < end) || (end<=start && (presentTime>=start||presentTime< end)))) {
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
	if(info.length<6){
	    System.out.println("Info Array is less than 6");
	    System.out.println("createNew failed");
	    return;
	}else{
	    Restaurant r = new Restaurant(info[0],info[1],info[2],info[3],info[4],info[5]);
	    this.addNew(r);
	}
	for(int i=0;i<allRestaurants.size();i++){
	    System.out.println(allRestaurants.get(i).getName());
	}
    }

    public void createCSVNew(String[] info)
    {
    	String[] withoutQuotes = new String[6];

    	for (int i = 0; i < 6; i++) {
    		withoutQuotes[i] = info[i].substring(1, info[i].length() - 1);
    	}
	Restaurant r = new Restaurant(withoutQuotes[0],withoutQuotes[1],withoutQuotes[2],withoutQuotes[3],withoutQuotes[4],withoutQuotes[5]);
	this.addNew(r);
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
	if (newRestaurant == null) {
	    System.out.println("error null new restaurant");
	    return;
	}

	for(int i=0; i<allRestaurants.size();i++)
	    if(allRestaurants.get(i) != null)
		if(newRestaurant.equals(allRestaurants.get(i)))
			return;
	allRestaurants.add(newRestaurant);
    }
    

    /**
       Search for a specific infomation about a restaurant from all the
       infomration collected from using the YelpAPI
       @param GeneralInfo  All info of the restaurant from the YelpAPI
       @param info         The specific info wanted to get extract
       @return Object      The specific data we look for (can be under many type)
    */
    private Object RestaurantSpecificInfo(String GeneralInfo, String info){
	JSONParser parser = new JSONParser();
	JSONObject response = null;
	try{
	    response = (JSONObject) parser.parse(GeneralInfo);
	}catch (ParseException pe){
	    System.out.println("Error: could not parse JSON response:");
	    System.out.println(GeneralInfo);
	    System.exit(1);
	}
	return response.get(info);
    }

    /**
       Populate the restaurants database of a cuisine around an area
       @param cuisine   cuisine that is being looked for
       @param area      area where the restaurants are located
     */
    public void populateRestaurantsDatabase(String cuisine, String area){
	//Collect the cuisine-specific local restaurants around an area
	//Implement using the YelpAPI
	ArrayList<NameAndID> LocalRestaurants = YelpAPI.LocalRestaurantNamesAndID(cuisine,area);
	
	for(int i = 0; i < LocalRestaurants.size(); i++){
	    String GeneralInfo = YelpAPI.RestaurantGeneralInfo(LocalRestaurants.get(i).id);
	    System.out.println(GeneralInfo);
	    //name is a string
	    String name=(String) this.RestaurantSpecificInfo(GeneralInfo, "name");
	    System.out.println(name);
	    //display_phone is a string
	    String phone=(String) this.RestaurantSpecificInfo(GeneralInfo,"display_phone");
	    System.out.println(phone);
	    String address="";
	    //location is a dictionary which is on its own right a JSONObject
	    JSONObject locationAtr = (JSONObject) this.RestaurantSpecificInfo(GeneralInfo,"location");
	    System.out.println(locationAtr.toString());
	    //display_address is an adress with each entry holding a specific piece of the address
	    List<String> DisplayAddress=(List<String>)locationAtr.get("display_address");
	    for(int j=0; j<DisplayAddress.size();j++){
		address += DisplayAddress.get(j);
		if(j<DisplayAddress.size()-1)
		    address += ", ";
		System.out.println(DisplayAddress.get(j));
	    }
	    if(address.equals(""))
		address = "unlisted";
	    //Potentially could use to generate a picture in the app
	    String imageURL = (String) this.RestaurantSpecificInfo(GeneralInfo,"image_url");
	    //YelpAPI does not have the open and closing time, thus make them all 8-22
	    Restaurant restaurant = new Restaurant("8","22",name,phone,address, cuisine, imageURL);
	    this.addNew(restaurant);
	}
    }
}
