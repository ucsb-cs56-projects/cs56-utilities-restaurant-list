package edu.ucsb.cs56.projects.utilities.YelpAPI;

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
import java.util.ArrayList;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Code sample for accessing the Yelp API V2.
 * 
 * This program demonstrates the capability of the Yelp API version 2.0 by using the Search API to
 * query for businesses by a search term and location, and the Business API to query additional
 * information about the top result from the search query.
 * 
 * <p>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp Documentation</a> for more info.
 * 
 */
public class YelpAPI {

  private static final String API_HOST = "api.yelp.com";
  private static final String DEFAULT_TERM = "dinner";
  private static final String DEFAULT_LOCATION = "Isla Vista, CA";
  private static final int SEARCH_LIMIT = 10;
  private static final String SEARCH_PATH = "/v2/search";
  private static final String BUSINESS_PATH = "/v2/business";

  /*
   * Update OAuth credentials below from the Yelp Developers API site:
   * http://www.yelp.com/developers/getting_started/api_access
   */
  private static final String CONSUMER_KEY = "cxcQVZAIEPFCkOHA9Z_WHQ";
  private static final String CONSUMER_SECRET = "8n4yJzuli4u5Cs4OvOG7obekhvc";
  private static final String TOKEN = "9vMaIONm-IA0wcEwq_vNwxI343f5TCuL";
  private static final String TOKEN_SECRET = "6lRe2CQwW2YH5ulTWXjWY2dxAns";

  OAuthService service;
  Token accessToken;

  /**
   * Setup the Yelp API OAuth credentials.
   * 
   * @param consumerKey Consumer key
   * @param consumerSecret Consumer secret
   * @param token Token
   * @param tokenSecret Token secret
   */

    public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
	this.service =
	    new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
            .apiSecret(consumerSecret).build();
	this.accessToken = new Token(token, tokenSecret);
    }
    
    /**
     * Creates and sends a request to the Search API by term and location.
     * <p>
     * See <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search API V2</a>
     * for more info.
     * 
     * @param term <tt>String</tt> of the search term to be queried
     * @param location <tt>String</tt> of the location
     * @return <tt>String</tt> JSON Response
     */
  public String searchForBusinessesByLocation(String term, String location) {
    OAuthRequest request = createOAuthRequest(SEARCH_PATH);
    request.addQuerystringParameter("term", term);
    request.addQuerystringParameter("location", location);
    request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
    return sendRequestAndGetResponse(request);
  }

  /**
   * Creates and sends a request to the Business API by business ID.
   * <p>
   * See <a href="http://www.yelp.com/developers/documentation/v2/business">Yelp Business API V2</a>
   * for more info.
   * 
   * @param businessID <tt>String</tt> business ID of the requested business
   * @return <tt>String</tt> JSON Response
   */
  public String searchByBusinessId(String businessID) {
    OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);
    return sendRequestAndGetResponse(request);
  }

  /**
   * Creates and returns an {@link OAuthRequest} based on the API endpoint specified.
   * 
   * @param path API endpoint to be queried
   * @return <tt>OAuthRequest</tt>
   */
  private OAuthRequest createOAuthRequest(String path) {
    OAuthRequest request = new OAuthRequest(Verb.GET, "https://" + API_HOST + path);
    return request;
  }

  /**
   * Sends an {@link OAuthRequest} and returns the {@link Response} body.
   * 
   * @param request {@link OAuthRequest} corresponding to the API request
   * @return <tt>String</tt> body of API response
   */
  private String sendRequestAndGetResponse(OAuthRequest request) {
    System.out.println("Querying " + request.getCompleteUrl() + " ...");
    this.service.signRequest(this.accessToken, request);
    Response response = request.send();
    return response.getBody();
  }

  /**
   * Queries the Search API based on the term and location and return the first 10 businesses found
   * 
   * @param yelpApi <tt>YelpAPI</tt> service instance
   * @param term for the term we look for
   * @param location for location we look for
   */
    private static JSONArray LocalBusinesses(YelpAPI yelpApi, String term, String location) {
	String searchResponseJSON =
	    yelpApi.searchForBusinessesByLocation(term, location);
	
	JSONParser parser = new JSONParser();
	JSONObject response = null;
	try {
	    response = (JSONObject) parser.parse(searchResponseJSON);
	} catch (ParseException pe) {
	    System.out.println("Error: could not parse JSON response:");
	    System.out.println(searchResponseJSON);
	    System.exit(1);
	}
	
	JSONArray businesses = (JSONArray) response.get("businesses");
	return businesses;
    }
    
    private String RestaurantGeneralInfo(YelpAPI yelpApi, String BusinessID){
	String businessResponseJSON = yelpApi.searchByBusinessId(BusinessID.toString());
	System.out.println(String.format("Result for business \"%s\" found:", BusinessID));
	System.out.println(businessResponseJSON);
	return businessResponseJSON;
    }
    
    public class NameAndID{
	public String name;
	public String id;
    }
    
  /**
   * Main entry for sample Yelp API requests.
   * <p>
   * After entering your OAuth credentials, execute <tt><b>run.sh</b></tt> to run this example.
   */
    public ArrayList<NameAndID> LocalBusinessesNames(String term, String location) {
	YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
	JSONArray businesses = LocalBusinesses(yelpApi, term, location);
	ArrayList<NameAndID> Businesses = new ArrayList<NameAndID>();
	for(int i=0;i<businesses.size();i++){
	    NameAndID entry= new NameAndID();
	    JSONObject Business = (JSONObject) businesses.get(i);
	    entry.name = Business.get("name").toString();
	    entry.id=Business.get("id").toString();
	    Businesses.add(entry);
	}
	return Businesses;
    }
    
    public String RestaurantSpecificInfo(String BusinessID, String info){
	YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
	String GeneralInfo = RestaurantGeneralInfo(yelpApi, BusinessID);
	JSONParser parser = new JSONParser();
	JSONObject response = null;
	try {
	    response = (JSONObject) parser.parse(GeneralInfo);
	} catch (ParseException pe) {
	    System.out.println("Error: could not parse JSON response:");
	    System.out.println(GeneralInfo);
	    System.exit(1);
	}
	JSONObject SpecificInfo = (JSONObject) response.get(info);
	return SpecificInfo.toString();
    }
}
