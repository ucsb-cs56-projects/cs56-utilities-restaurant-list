cs56-utilities-restaurant-list
==============================

project history
===============
```
 W14 | jcneally 4pm | andrewpang,bhammel | app that lists restaurants from its database
```

An app to display information about restaurants from a database, including hours of operation and phone number.

===============
How to run
===============
Use ant run to open the GUI which takes the user to a menu where they can find somewhere to eat or add their own restaurant entries.

===============
User capabilities
===============

At the main menu, the user can choose either the "Eat" or "Future Time" button to see what type of cuisine they want to eat. The "Eat" button takes into account the current time in order to determine whether restaurants are opened, while Future Time requires the user to enter a certain time that will determine what's open. Regardless of which button is clicked, the user is taken to a dropdown list of restaurants that are currently open. Then they can click a restaurant in order to find information about it such as address, phone number, and hours of operation. 

Users are able to add a new restaurant entry and edit exisiting entry using text fields. However, editing exisiting entry has not been implemented yet. They can also save their data through a CSV importer and exporter.

===============
Yelp API Information
===============

This program utilizies the Yelp Developer API v2. Most if not all of the methods which utilize the Yelp API are from the Yelp "Business" methods.


===============
W16 Final Remarks
===============

```
 W16 | pconrad 4pm | thienhoang23,Brenda_Flores | improves the app to integrate YelpAPI to generate the database and fix multiple bugs on add restaurants and eat features of the GUI
```

Implementing this app is a great way to learn about API, networking, and simple GUI. I have highlighted the areas in GUI file that could use a major improvement (mostly the editting feature). A confusing thing about this lab is that the eat method will only show the restaurants openned during real time. So if you are coding at night, you might not see a lot of restaurants on your eat list and confused it with a bug. Also worth noticing is that even though Yelp API greatly increases the usability of the app, it, however, does not provide the operation hours of the businesses. If you could find a second API and cross refernce them with Yelp, that would boost this app's usability immensely. A little hint: You could add image into of the restaurant in in its info because when I first generated the restaurants database, I have included the imageURL into each restaurant. Lastly, just a suggestion, if you could move all the directories around and rename in a more logical way, that would be great!

========================
Summer M16 Final Remarks
========================

```
M16 | pconrad 9am | alanthetran,timnkwong | improves the app to integrate YelpAPI to include restaurant pictures and a more diverse choice of restaurant
```
This app is a neat way to learn about API usage and how to interact with an API specifically Yelp. Not only that but also learn how to work with networks, simple GUI and legacy code. Yelp's API gives you a lot of information in order for you to improve this app more. So far we have implemented a menu button that doesn't work as intended, added location setting for different restaurants, and added a picture of the restaurant. There are issues that needs to be worked on for Fall 2016 and that is your job to fix these issues or come up with new issues that may arise. The edit screen currently does not work and needs to fixed if you choose to tackle this issue. Overall the code is fairly clean and easy to understand. You should have a fun time with this lab!


========================
Fall 16 Final Remarks
========================

```
F16 | pconrad 5pm | colinmai,N7Alpha | improves the app to integrate GoogleAPI to include correct restaurant hours and added new features.
```
This is probably one of the harder projects, but it is a great way to learn how to use APIs (a very useful skill in real life). You will also develop GUI skills that are applicable to any app you might be working on in the future. Right now, the code is pretty much the same thing as yelp; you can search for restaurants by city and cuisine type. Then you will be presented by menus, hours, reviews, etc. Many features presented in the main menu do not work perfectly yet, such as the option to add your own restaurants. The code right now is a little messy, as the majority of it is dumped into a few files; it might be easier to move seperate parts into new files. Overall, though this code might seem daunting at first, it has great potential and provides a very valuable opportunity to develop real like skill. Good luck and have fun!


========================
Winter 18 Final Remarks
========================

```
W18 | pconrad 11am | dannycho7,DennisZZH | improves the app to integrate GoogleAPI to include correct restaurant hours and added new features.
```
It is a good way to learn about Java GUIs and industry standard APIs. Coming into the project, we mainly worked on fixing the bugs that existed from the prior year. This involved having to debug network exception issues (url parsing) and other misc errors. Towards the end, we worked to make the code structure more organized; the current state of the code is more object-oriented. One very important thing to do is to upgrade to the Yelp API v3, which is the newest/latest version of the yelp api. If this upgrade hasn't been made by June 2018, the project will likely no longer function. One thing we'd like to see this project implement is a way to incorporate a "recommendations" system based on other restaurant preferences. Overall, this is an okay project, functionally, but provides value if you are looking to learn about networking, APIs, and the Java GUI.