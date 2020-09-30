------------------------------------
HOW TO INSTALL (DEVELOPMENT VERSION)
------------------------------------

1) Download Spring Tool Suite 4 (current website: https://spring.io/tools)

2) Clone the repository or download the project:
	
	If you choose clone the repository: 
		- Window->Show View->Other->Git Repositories
		- Right Click on Git Repositories Window -> Clone a Git Repository
		- Import Project into Workspace

	If you choose download manually the repository: 
		- From this github.com repository from your web browser
		- Select branch "dev" if it's selected another branch
		- Push "Code" button
		- Select "Download ZIP" option
		- Go to Spring Tool Suite
		- Import Project into Workspace

3) Change file "src/main/resources/application.properties": set user and password from your database instance.
	
	spring.datasource.username=[username]
	spring.datasource.password=[password]

4) Create a new empty database: new schema named "flightplans" in MySQL database 
	(it will be updated with tables automatically when the application is run)


5) Run Spring BackEnd Project:
	- Right Click on BackEnd_VorNavFlightPlan_Project -> Run As -> Spring Boot App

6) Install Postman (current website: https://www.postman.com/)

7) IMPORT NAVAIDS FROM XML TO DATABASE:
   Create a new request: http://localhost:8181/api/navaids (POST method)
   Copy the content of the XML inside the file "Input_example/xml_import_navaid_spain_es.aip"
   and paste it into the Body Request. (How? click on the "Body" link below the input field and 
   select "raw" among the different radio buttons and then select "XML" in the drop down list.
   Finally copy and paste the XML inside the textarea. Click SEND button.). 

8) CALCULATE RANGE FOR EVERY NAVAIDS, AND STORE THE RELATIONSHIP BETWEEN NAVAIDS IN DATABASE:
   Create a new request: http://localhost:8181/api/navaids/range (POST method)
   This is all business logic and database operations stuff, so don't worry about input or output.

9) Test BackEnd with temporary HORRIBLE FrontEnd:
        - Open HTML file "FrontEnd_VorNavFlightPlan_Project/WebContent/main.html" in Web Browser.
	(Be careful IF backend is running, if not a query navaids error will be thrown instead of 
	 showing the leaflet map)

