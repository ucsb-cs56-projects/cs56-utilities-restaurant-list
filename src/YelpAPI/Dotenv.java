/**
 *
 * @author Danny Cho
 * @version CS56, W18
 */

package edu.ucsb.cs56.projects.utilities.YelpAPI;

import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Manages private config variables that aren't to be checked into source control (API Keys)
 */
public class Dotenv {
	private static JSONObject config_vars;

    /**
     * Load file specified environment variables
    */
    public static void load() {
    	try {
			JSONParser parser = new JSONParser();
    		config_vars = (JSONObject) parser.parse(new FileReader("./src/config.json"));
    	} catch(Exception ex) {
    		System.out.println("Excepted: " + ex);
    	}

    }

    /**
     * Returns the environment variable that you supply
     * 
     * @param key The environment variable you want to fetch
     * @return value of environment variable
     */
    public static String get(String key) {
    	return config_vars.get(key).toString();
    }
}
