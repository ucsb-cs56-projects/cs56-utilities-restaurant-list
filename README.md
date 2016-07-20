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
