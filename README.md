# BugReportApp

TCP socket client and server applications written in Java.

## Design Choices

### Loading and Saving of Data

* I have assumed that the server will keep all data in memory during its lifecycle but the data will not be persisted unless it is done so manually
* It would be simple to implement a data backup on each data change but I thought this was overkill (?)
* On server startup, any data in the two CSV files will be loaded into memory
* At any time, from the main menu, it is possible to:
	* back up the current in-memory data to the two CSV files
	* load the data from the two CSV file into memory - this is a destructive action which will cause any data added since last save to be discarded 
	
* The two data files are:
	* resources/employees.csv
	* resources/bugreports.csv

* TODO: CSV files could be implemented better but I have not taken validation this far.
	* ensure that no semicolon characters exist in input strings so that csv file works correctly
	

### BugReport Status

* I have not used the suggested status of "Assigned" as the flow does not fit because I reason that a bug report should be able to be both Assigned and Open/Closed
* When a bug report is added, it is automatically set to a status of "Open"
* The bug report can be manually set to either open or closed by using the bug edit menu (Main Menu #7)
* When a bug report is printed to the console, if it has not been assigned to an employee, the "Assigned To" field will display "Unassigned", otherwise it will display the email of the employee to whom it was assigned

### Menus

* To keep source files smaller in size, I have broken the menus up into a number of separate classes with static methods.  All of these classes are in the menus package.
* The MainMenu class is the only instantiable class in the menu package and this class in instantiated by a RequestHandler thread.
* All other menu logic is contained within (mostly) public static methods which are called by the main menu and sub-menus.

### Error Handling

* No error handling on client side for connecting to server
* Input validation is present for email addresses and integers
* Most often, if an input error is detected, the user is directed back to the previous menu and no changed are made to data
* In the edit bug menu (#7), the user must manually exit by entering -1
* If the client is disconnected from the server at certain times, the server will throw an exception - this will occur when the client thread is within one of the menu loops.  It is caused by the RequestHandler getMessage() method returning a null pointer.  The server will continue running so I have fixed this.  This can be seen in the screencast.
