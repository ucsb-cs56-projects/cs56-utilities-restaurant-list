# Hyun Bum Cho, Zihao Zhang

a) It's a restaurant viewing GUI application. It looks like a Yelp client clone.

b) As a customer, I can find out when a particular restaurant opens and closes. As a customer, I can read yelp reviews of any particular restaurant. As a customer, I can find restaurants based on location and the type of food it serves.

c) The software does run. It brings up a list of options where you can view and edit restaurants through. These options include "Eat". "Add New", "Editing Existing Entries", "Load from CSV File", "Save to CSV File", "Future Time", and "Exit".
  
d) As a customer, I want to continue viewing the restaurant viewer after closing the reviews popup. As a customer, I want to be able to view more than one photo of the restauarant. As a customer, I want to view restaurants that are currently closed. As a restaurant owner or customer, I would like the GUI to be more modern.

e) The README should include a screenshot of the current run state of the application. This would help future maintainers quickly understand the interface and features that this application provides.

f) All targets have descriptions. There are no legacy jws stuff in use. There are, however, properties that are defined with jws prefixes, which are not used. We can remove this.

g) There are enough issues to total to 1000 points. The issues are clear in their expectations.

h) No additional issues at the moment. There are a lot of issues.

i) The code is now organized pretty well. It is clear to me which classes and folders provide the functionalities that make the restaurant viewer work. There are two main subdivisions within the code. The YelpAPI folder contains that classes that relate to communicating with Yelp's API. You supply it relevant API keys, and the YelpAPI object is able to query the Yelp API server for relevant information. The RestaurantList folder contains the GUI and client that invokes the Yelp API's queries. Specifically, the Food class will call the YelpAPI object to obtain restaurant information. The other classes within the RestaurantList help with the display of the information.

j) There are no tests specified in the build file. Careful inspection of the entire codebase shows that there are absolutely no tests within the codebase. There are definitely opportunities to improving test coverage, considering there are no tests at all. I would start by writing unit tests for the GUI code and then expanding to the Yelp wrapper code.

