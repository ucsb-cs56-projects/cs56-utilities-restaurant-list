/**
   Getters and setter for the restaurant objects

   @author Ameya Savale
   @author Brandon Hammel
   @author Andrew Pang
   @author Thien Hoang
   @author Brenda Flores
   @version issue 398, CS56, W16
 */

package edu.ucsb.cs56.projects.utilities.restaurant_list;

import java.util.*;
import java.io.*;

public class Restaurant implements Serializable {
    String starttime, endtime;
    String name, phonenumber, address, type;
    String imagePath;

    /**
       Arg Constructor creates new Restaurant object with the values

       @param starttime    opening hour of the restaurant
       @param endtime      closing hour of the restaurant
       @param name         name of restaurant
       @param phonenumber  phonenumber of the restaurant
       @param address      address of the restaurant
       @param type         type of cuisine
     */
    public Restaurant(String starttime, String endtime, String name, String phonenumber, String address, String type) {
	if(starttime.equals("")||endtime.equals("")||name.equals("")||phonenumber.equals("")||address.equals("")||type.equals("")){
	    this.starttime = "9";
	    this.endtime = "22";
	    this.name = "Panda Express";
	    this.phonenumber = "805-683-1857";
	    this.address = "131,N. Fairview Ave,Goleta,CA,93117";
	    this.type = "Chinese";
	    this.imagePath = "";
	}else{
	    this.starttime = starttime;
	    this.endtime = endtime;
	    this.name = name;
	    this.phonenumber = phonenumber;
	    this.address = address;
	    this.type = type;
	    this.imagePath = "";
	}
    }
    
    //getters for the instance variables
    public String getName() {
      return name;
    }

    public String getPhone() {
      return phonenumber;
    }
    
    public String getAddress() {
      return address;
    }

    public String getEndTime() {
      return endtime;
    }

    public String getStartTime() {
      return starttime;
    } 

    public String getType() {
      return type;
    }

    public String getImagePath(){
	return imagePath;
    }
    
    //setters for the instance variables
    public void setName(String name) {
	if(!name.equals(""))
	    this.name = name;
    }

    public void setStartTime(String starttime) {
	if(!starttime.equals(""))
	    this.starttime = starttime;
    }
    
    public void setAddress(String address) {
	if(!address.equals(""))
	    this.address = address;
    }

    public void setPhone(String phonenumber) {
	if(!phonenumber.equals(""))
	    this.phonenumber = phonenumber;
    }

    public void setEndTime(String endtime) {
	if(!endtime.equals(""))
	    this.endtime = endtime;
    }

    public void setType(String type) {
	if(!type.equals(""))
	    this.type = type;
    }

    public void setImagePath(String imagePath){
	if(!imagePath.equals(""))
	    this.imagePath = imagePath;
    }
    
    public boolean equals (Restaurant rhs){
	boolean val1 = name.toLowerCase().equals(rhs.getName().toLowerCase()); 
	boolean val2 = phonenumber.equals(rhs.getPhone()); 
	boolean val3 = address.toLowerCase().equals(rhs.getAddress().toLowerCase());
	return val1 && val2 && val3;

    }
}
