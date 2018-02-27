/*
 *
	A program that finds open retaurants depending on the current time. User can also find restaurants that are open are a later hour. Users can also input in new restaurants.

	@author Ameya Savale
	@author Brandon Hammel
	@author Andrew Pang
	@author Thien Hoang
	@author Brenda Flores
	@version CS56, Winter 2016

	@author Timothy Kwong
	@author Alan Tran
	@version CS56, Summer 2016

	@author Colin Mai
	@author John Rehbeim
	@version CS56, Fall 2016
 */
package edu.ucsb.cs56.projects.utilities.restaurant_list;

import java.util.*;
import java.io.*;

//Google Places API helper library, Protip: you should frequently update this library because it's not officially supported by Google. Here's a link https://github.com/windy1/google-places-api-java
import se.walkercrou.places.*;

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
import edu.ucsb.cs56.projects.utilities.YelpAPI.Dotenv;

/**
 * A class that represents each food item
 */
public class Food implements Serializable {

	private Calendar calendar = Calendar.getInstance();
	private Day[] days = { Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY };    
	private Day current_day = days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    
    ArrayList<Restaurant> allRestaurants = new ArrayList<Restaurant>();
    
    //Google Place static constants
    //The GooglePlaces object is the interface to the API so most functionality you need from the library can be got from that object.
    public static final String GOOGLE_PLACES_API_KEY = Dotenv.get("GOOGLE_PLACES_API_KEY");
    public static final GooglePlaces googlePlacesClient;
    
    static {
        googlePlacesClient = new GooglePlaces(GOOGLE_PLACES_API_KEY);
    }
    
    /**
	 * noarg Constructor for objects of class Food
     */
    public Food() {
		boolean fileLoaded = true;
    
		try {
		    fileLoaded = this.readSavedList();
		} catch(Exception e) {
		    System.out.println("Could not load the file");
		}
		
		if (fileLoaded == false) {
		    /*Default objects to add to arrayLists. Obsolete now because of new methods to add restaurants upon choosing cuisines
		    this.populateRestaurantsDatabase("Mexican","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Chinese","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Thai","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Fast Food","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Sandwiches","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Pizza","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Italian","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Coffee & Tea","Isla Vista, CA");
		    this.populateRestaurantsDatabase("Vegetarian","Isla Vista, CA");
		    */}
    }
	/**
	 * Allows us to access and print out the restaurant's name
	 * @param name The stirng that holds the name of the restaurant
	 * @return r the restaurant which corresponds with the name
	 */		
    public Restaurant getCuisineWithName(String name) {
        // This is a hack but needed for getting restaurant info in a reasonable fashion
        for(Restaurant r : allRestaurants) {
            if(r.getName().equals(name)) {
                return r;
            }
        }
        
        int[] array = new int[1]; // crash on purpose cause theres no graceful way to handle a failure
        array[1] = 0;
        return allRestaurants.get(0);
    }

	/** 
	 * Reads the restaurant list 
	 * @return load    the list of restaurants
    */
    
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
     * goes through the correct arrayList and gets the 
	 * list of the different restaurant options that are open within the hour specified
	 * @param cuisine    The type of cuisine
	 * @param hour       The hour during which the restaurant should be open
	 * @return choice    The restaurant which the user wants the information for
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
     * shows all the information about the restaurant chosen by the user
     * @param choice    The index of the restaurant the user wants to access
     */
    public String[] showAllInfo(String choice) {

		String[] restaurantInfo = new String[7];
		
		for (int i = 0; i < allRestaurants.size(); i++) {
		    String restaurant = allRestaurants.get(i).getName();
		    if (restaurant.equals(choice)) {
			restaurantInfo[0] = allRestaurants.get(i).getName();
			restaurantInfo[1] = allRestaurants.get(i).getStartTime();
			restaurantInfo[2] = allRestaurants.get(i).getEndTime();
			restaurantInfo[3] = allRestaurants.get(i).getAddress();
			restaurantInfo[4] = allRestaurants.get(i).getPhone();
			restaurantInfo[5] = allRestaurants.get(i).getImagePath();
			restaurantInfo[6] = allRestaurants.get(i).getMenu();
		        }
		}
		
		return restaurantInfo;
    }
    
    /**
	 * gives the current hour
	 * @return hour current hour of the day
    */
    public int getHour() {
		Calendar hour = new GregorianCalendar();
		return hour.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
     * creates a new restaurant object which is stored into the correct 
     * arrayList with the properties inputed by the user
     */
    
    public void createNew(String[] info) {
		if(info.length<6){
		    System.out.println("Info Array is less than 6");
		    System.out.println("createNew failed");
		    return;
		}else{
		    Restaurant r = new Restaurant(info[0],info[1],info[2],info[3],info[4],info[5],info[6],info[7], (Place)null);
		    this.addNew(r);
		}
		for(int i=0;i<allRestaurants.size();i++){
		    System.out.println(allRestaurants.get(i).getName());
		}
    }

    /** 
	 * Prints out the restaurant's information. Creates a comma seperated value file
	 * @param info      Contains an array of string has lists information
    	*/
    public void createCSVNew(String[] info) {
    	String[] withoutQuotes = new String[7];

    	for (int i = 0; i < 6; i++) {
    		withoutQuotes[i] = info[i].substring(1, info[i].length() - 1);
    	}

		Restaurant r = new Restaurant(withoutQuotes[0],withoutQuotes[1],withoutQuotes[2],withoutQuotes[3],withoutQuotes[4],withoutQuotes[5],withoutQuotes[6],withoutQuotes[7], (Place)null);
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
     * Adds the default restaurant objects to the correct arrayList
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
     * Clears the list of restaurants
     */ 
    public void clearEntries() {
		allRestaurants.clear();
    }

    /**
     * Search for a specific infomation about a restaurant from all the
	 * infomration collected from using the YelpAPI
	 * @param GeneralInfo  All info of the restaurant from the YelpAPI
	 * @param info         The specific info wanted to get extract
	 * @return Object      The specific data we look for (can be under many type)
     */
    private Object RestaurantSpecificInfo(String GeneralInfo, String info){
		JSONParser parser = new JSONParser();
		JSONObject response = null;
		try {
		    response = (JSONObject) parser.parse(GeneralInfo);
		} catch (ParseException pe) {
		    System.out.println("Error: could not parse JSON response:");

		    System.out.println(GeneralInfo);
		    System.exit(1);
		}
		return response.get(info);
    }

    /**
	 * Populate the restaurants database of a cuisine around an area
	 * @param cuisine   cuisine that is being looked for
	 * @param area      area where the restaurants are located
     */
    public void populateRestaurantsDatabase(String cuisine, String area){
		//Collect the cuisine-specific local restaurants around an area
		//Implement using the YelpAPI
		ArrayList<NameAndID> LocalRestaurants = YelpAPI.LocalRestaurantNamesAndID(cuisine,area);
	    // Query the google Places API using the client helper object googlePlacesClient
	        List<Place> restaurantResults = googlePlacesClient.getPlacesByQuery(cuisine + " in " + area, GooglePlaces.DEFAULT_RESULTS); // This will fetch 20 results at most
	        //, Param.name("type").value("restaurant")); You could add this as a parameter but it narrows your results and removes some restaurants that aren't yet categorized as restaurants
	        
	        List<Place> detailedRestaurantResults = new ArrayList<Place>();
	        for (Place restaurant : restaurantResults) {
	            detailedRestaurantResults.add(restaurant.getDetails());
	        }
	        
	        
		for(int i = 0; i < LocalRestaurants.size(); i++){
	        String openTime = "8";
	        String closeTime = "22";

	        try {
		    	String GeneralInfo = YelpAPI.RestaurantGeneralInfo(LocalRestaurants.get(i).id);
			
		    	System.out.println(GeneralInfo);

			    //name is a string
			    String name=(String) this.RestaurantSpecificInfo(GeneralInfo, "name");
			    System.out.println(name);
			    //display_phone is a string
			    String phone = (String) this.RestaurantSpecificInfo(GeneralInfo,"display_phone");
			    System.out.println(phone);
			    String address="";
			    String menu=(String) this.RestaurantSpecificInfo(GeneralInfo, "menu_provider");
			    System.out.println(menu);
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
			   
			    //Use the Google Places API for operating hours cause the Yelp API doesn't have them
		        Place placesRestaurant = null;
		        ArrayList<Review> reviews = new ArrayList<Review>();
		        if(phone != null) {
		            String comparableYelpPhoneNumber = phone.replaceAll("\\D", "").substring(1);
		            for (Place restaurant : detailedRestaurantResults) {
		                
		                if (restaurant.getPhoneNumber() != null) {
		                    String comparableGooglePlacesPhoneNumber = restaurant.getPhoneNumber().replaceAll("\\D", "");
		                    //We use phone numbers to determine if the restaurants are the same there really isn't too much of a cleaner way to do this
		                    if (comparableYelpPhoneNumber.equals(comparableGooglePlacesPhoneNumber)) {
		                        placesRestaurant = restaurant;
		                        System.out.println("NUMBER OF REVIEWS : " + restaurant.getReviews().size());
		                        reviews = new ArrayList<Review>(restaurant.getReviews());
		                        Hours operatingHoursWeekly = restaurant.getHours();
		                        List<Hours.Period> operatingHoursByDay = operatingHoursWeekly.getPeriods();
		                        if (operatingHoursByDay.size() > 0) {
		                        	int dayIndex = 0;
		                        	for(dayIndex = 0; dayIndex < operatingHoursByDay.size(); dayIndex++) {
		                        		if(operatingHoursByDay.get(dayIndex).getOpeningDay() == current_day) {
		                        			System.out.println("Found instance of current day at index: " + i + " Day is " + operatingHoursByDay.get(dayIndex).getOpeningDay());
		                        			break;
		                        		}
		                        	}
		                        	if(dayIndex == operatingHoursByDay.size()) {
		                        		dayIndex = 0;
		                        	}

		                            Hours.Period dailyHours = operatingHoursByDay.get(dayIndex);
		                            String nonCleanedOpenTime = dailyHours.getOpeningTime(); // The library puts it in HH:MM format
		                            String nonCleanedCloseTime = dailyHours.getClosingTime();
		                            openTime = nonCleanedOpenTime.substring(0,2);
		                            closeTime = nonCleanedCloseTime.substring(0,2);
		                        }
		                    }
		                }
		                
		            }
		            
		        }
		        Restaurant restaurant;
		        if (placesRestaurant != null) {
		            restaurant = new Restaurant(openTime, closeTime, name, phone, address, cuisine, imageURL, menu, placesRestaurant);
		        } else {
		            restaurant = new Restaurant(openTime, closeTime, name, phone, address, cuisine, imageURL, menu);
		        }
		        
			    this.addNew(restaurant);
			} catch(UnsupportedEncodingException ex) {

			}
		} 
    }
}
