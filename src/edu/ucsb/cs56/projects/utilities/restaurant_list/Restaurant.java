/**
   Getters and setter for the restaurant objects

   @author Ameya Savale, Andrew Pang, Brandon Hammel
   @version issue 398, CS56, W14
 */

package edu.ucsb.cs56.projects.utilities.restaurant_list;

import java.util.*;
import java.io.*;

public class Restaurant implements Serializable{
    String starttime, endtime;
    String name, phonenumber, address, type;

    /**
       no arg constructor 
     */
    public Restaurant(){
	starttime = "";
	endtime = "";
	name = "";
	phonenumber = "";
	address = "";
	type = "";
    }
    /**
       Arg Constructor creates new Restaurant object with the values

       @param starttime    opening hour of the restaurant
       @param endtime      closing hour of the restaurant
       @param name         name of restaurant
       @param phonenumber  phonenumber of the restaurant
       @param address      address of the restaurant
       @param type         type of cuisine
     */
    public Restaurant(String starttime, String endtime, String name, String phonenumber, String address, String type){
	this.starttime = starttime;
	this.endtime = endtime;
	this.name = name;
	this.phonenumber = phonenumber;
	this.address = address;
	this.type = type;
    }

    //getters for the instance variables
    public String getName(){
	return name;
    }

    public String getPhone(){
	return phonenumber;
    }
    
    public String getAddress(){
	return address;
    }

    public String getEndTime(){
	return endtime;
    }

    public String getStartTime(){
	return starttime;
    } 

    public String getType(){
	return type;
    }
    
    //setters for the instance variables
    public void setName(String name){
	this.name = name;
    }

    public void setStartTime(String starttime){
	this.starttime = starttime;
    }
    
    public void setAddress(String address){
	this.address = address;
    }

    public void setPhone(String phonenumber){
	this.phonenumber = phonenumber;
    }

    public void setEndTime(String endtime){
	this.endtime = endtime;
    }

    public void setType(String type){
	this.type = type;
    }
}
