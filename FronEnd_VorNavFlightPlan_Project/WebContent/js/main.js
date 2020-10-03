	
	//*****************************
	//*     GLOBAL VARIABLES      *
	//*****************************
	
	var mymap = null; //global variable to operate with javaScript with Leaflet map
	var routePolyline = null; //polyline object stored to remove polylines from previous route (clearing the map for a new route)
	var departureMark = null; //departure location marker
	var arrivalMark = null; //arrival location marker
	var circleRange = null; //last navaid range circle object shown in last marker click
	var IDENTIFIER_lastNavaidClick = null; //last navaid marker click
	var currentNavaidList = null; //list of navaids from the current flight plan
	
	//*****************************
	//*        FUNCTIONS          *
	//*****************************
	
	// Function to set up the leaflet map
	//
	// How to show objects in front over the map: (working with panes)
	//    Indicate attribute pane with value "polylines" when creating objects to show and bring the object to the front.
	//    Example:  var polyline = L.polyline(polylinePoints,{ pane: "polylines" }).addTo(mymap);   

	function setUpLeafletMap(){ 
		mymap = L.map('mapid').setView([40.2085, -3.713], 6);
		
		mymap.createPane('polylines');
		mymap.getPane('polylines').style.zIndex = 650; // PANE "polylines" CREATED!
		
		L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
			attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
		}).addTo(mymap);

		mymap.on('click', onMapClick);
	}
	
	// AJAX HTTP Request to get a list with all navaids from backend REST service
	function sendGetNavaidsRequest(){
				
		$.ajax({
		    url: 'http://localhost:8181/api/navaids',
		    type: "GET", 
		    dataType: "json",
		    data: {},
		    
		    success: function (result) {
		        //console.log(result);
				//resposta = JSON.stringify(result);
				processNavaidsDataToMap(result);
		    },
		    
		    error: function (error) {
		        console.log(error);
			    document.getElementById("mapid").innerHTML = "Query error retrieving all navaids.";

		    }
		});
			
	}
	
	// Draw with markers on the leaflet map all the navaids locations 
	// PARAM jsonResponse : response from REST service : list of all navaids from database
	function processNavaidsDataToMap(jsonResponse){
				
		var navaidList = jsonResponse.navaids;
				
		for (var i = 0;i<navaidList.length;i++){
			
			let navaid = navaidList[i];
			
			var lat = navaid.geolocationLat;
			var lon = navaid.geolocationLon;
			var name = navaid.name;
			var idName = navaid.idName;
			var type = navaid.type;
			var range = navaid.paramRange;
			
			var navaidInfo = "[idName="+idName+" , name="+name+" , type="+type+", paramRange="+range+"]";
			
			var marker = L.marker([lat, lon]);
			//marker.setOpacity(0.5);
			marker.addTo(mymap).bindPopup(navaidInfo)
							   .on('click', function(e) { onMarkerClick(e, navaid);} );
			
		}
		
		//testNavaidsRoute(navaidList[1],navaidList[10],navaidList[20]); 
		
	}
	
	// Function executed when a navaid marker is clicked
	// Draws a circle in navaid location (the same than the marker) with the real range. Useful to understand route algorithm.
	// If it is the second time that the marker is clicked, the range circle is removed.
	//
	// PARAM navaid : contains all the info about the navaid that represents this marker
	
	function onMarkerClick(e,navaid){ //show range circle from navaid where it is detectable
		//window.alert(e.latlng);
		//this.getLatLng();
		//window.alert(range);
		//window.alert(navaid.paramRange);
		
		if (navaid.identifier == IDENTIFIER_lastNavaidClick){ //if current click is in the same last marker remove the range circle
		   if(circleRange!=null) circleRange.remove(mymap);
		   IDENTIFIER_lastNavaidClick=null;
		}else{ // if current click is different than the last marker, draw and show new range circle
		
			let lat = navaid.geolocationLat;
			let lng = navaid.geolocationLon;
			let range = navaid.paramRange * 1852; //range in meters
			
			if(circleRange!=null) circleRange.remove(mymap);
			
			circleRange = L.circle([lat,lng], {
				color: 'green',
				fillColor: 'green',
				fillOpacity: 0.5,
				radius: range
			}).addTo(mymap);	
			
			IDENTIFIER_lastNavaidClick = navaid.identifier;
		}
	}
	
	// function for testing purposes. It draws a polyline on the map.
	function testNavaidsRoute(navaid1, navaid10, navaid20){
				
		var lat1 = navaid1.geolocationLat;
		var lon1 = navaid1.geolocationLon;
		var lat10 = navaid10.geolocationLat;
		var lon10 = navaid10.geolocationLon;	
		var lat20 = navaid20.geolocationLat;
		var lon20 = navaid20.geolocationLon;
	
		var polylinePoints = [
				[lat1, lon1],
				[lat10, lon10],
				[lat20,lon20]
			];            

		var polyline = L.polyline(polylinePoints,{ pane: "polylines" }).addTo(mymap);   
		
		polyline.setStyle({
			color: 'red',
			weight: 10
		});
		
		routePolyline = polyline;
				
	}
	
	// Draw the polyline route on the map with the points passed by parameter
	function drawNavaidsRoute(polylinePoints){

		var polyline = L.polyline(polylinePoints,{ pane: "polylines" }).addTo(mymap);   
		
		polyline.setStyle({
			color: 'red',
			weight: 10
		});
		
		routePolyline = polyline;
				
	}
	
	// Draw a circle in coordinate with latitude "lat" and longitude "lng" and show popUp with message "message"
	// Used to draw departure and arrival location points
	function createCircle(lat,lng,message){
		
		var circle = L.circle([lat,lng], {
			color: 'green',
			fillColor: 'green',
			fillOpacity: 1,
			radius: 20000,
			pane: "polylines"
		}).addTo(mymap).bindPopup(message).openPopup();	
		
		return circle;
	}
	
	//
	// When the leaflet map is clicked: (the parameter contains the map coordinates where is clicked)
	//	  if departure location is empty, get map coordinates for departure and draw departure location on the map
	//	  if arrival location is empty, get map coordinates for arrival and draw arrival location on the map
	//
	function onMapClick(e) {
		//alert("You clicked the map at " + e.latlng);
		var lat = e.latlng.lat;
		var lng = e.latlng.lng;
		//alert(lat+","+lng);
		
		var d = document.getElementById("lat1").value;
		var a = document.getElementById("lat2").value;
		
		if(d==""){ 
			document.getElementById("lat1").value = lat; 
			document.getElementById("lng1").value = lng; 
			
			document.getElementById("departureMessage").innerHTML = ""; 
			document.getElementById("arrivalMessage").innerHTML = "Click on the map the arrival location"; 
			
			//var marker = L.marker([lat, lng]).addTo(mymap).bindPopup("SOURCE");
			departureMark = createCircle(lat,lng,"DEPARTURE");
			
		}else if(a==""){
			document.getElementById("lat2").value = lat; 
			document.getElementById("lng2").value = lng;
			
			document.getElementById("arrivalMessage").innerHTML = ""; 
			
			if(arrivalMark!=null) arrivalMark.remove(mymap);
			//var marker = L.marker([lat, lng]).addTo(mymap).bindPopup("TARGET");
			arrivalMark = createCircle(lat,lng,"ARRIVAL");

		}
	}

	// Clear departure location and arrival location from text fields and remove circle points from the map.
	// Remove also the route polylines (if there are) from the map.
	function resetLocations(){
				
		document.getElementById("lat1").value = ""; 
		document.getElementById("lng1").value = ""; 
	
		document.getElementById("lat2").value = ""; 
		document.getElementById("lng2").value = "";
		
		document.getElementById("arrivalMessage").innerHTML = ""; 
		document.getElementById("departureMessage").innerHTML = "Click on the map the departure location"; 
		arrivalMark.remove(mymap);
		departureMark.remove(mymap);
		
		if(routePolyline!=null) routePolyline.remove(mymap);
		
		currentNavaidList = null;

	}
	
	// ********************************************
	// ***    ROUTE FLIGHT PLAN IMPLENTATION 	***
	// ********************************************
	// except function drawNavaidsRoute that is long before this line of code...
	
	// Prepare coordinates and call the REST service to get the simple route calculation
	// This function encapsulate all the route process until it is drawn on the map 
	function calculateFlightPlan(){
		
		var lat1 = document.getElementById("lat1").value; 
		var lng1 = document.getElementById("lng1").value; 
	
		var lat2 = document.getElementById("lat2").value; 
		var lng2 = document.getElementById("lng2").value;
		
		if(routePolyline!=null) routePolyline.remove(mymap);

		sendSimpleRouteRequest(lat1,lng1,lat2,lng2);
		
		//window.alert("Missing implementation");
		
	}
	
	// Prepare JSON call at HTTP body request and call the REST service to get the simple route calculation
	
	function sendSimpleRouteRequest(p_lat1,p_lng1,p_lat2,p_lng2){
				
		var jsonObject = JSON.stringify( {
			"departureLocation" : { "latitude":parseFloat(p_lat1) , "longitude":parseFloat(p_lng1)},
			"arrivalLocation" : { "latitude":parseFloat(p_lat2), "longitude":parseFloat(p_lng2)}
		});
				
		//window.alert(jsonObject);
		
		$.ajax({
		    url: 'http://localhost:8181/api/routes/strategy/simple',
			type: "POST",
		    method: "POST", 
		    dataType: "json",
		    contentType: "application/json",
		    data: jsonObject,
		    success: function (result) {
		        //console.log(result);
				//window.alert(JSON.stringify(result));
				processRouteDataToMap(result); //draw route on the map from REST service response with navaids list
		    },
		    
		    error: function (error) {
		        console.log(error);
				window.alert("Error connecting or retrieving data with REST service.");
		    }
		});
			
	}
	
	// It draws the route on the map using the navaids list from the REST service response
	
	function processRouteDataToMap(jsonResponse){
			
		var routeList = [];
				
		var navaidList = jsonResponse.route;
		var success = jsonResponse.success;
		var message = jsonResponse.message;
		
		currentNavaidList = navaidList; // save navaids list in global variable
		
		var firstLeg = true;
		var lastLeg = true;
		
		if (success==false){
			if( navaidList.length==0 ){ //There is not any  VOR station with range enough to be detected at departure location
				window.alert(message);
				firstLeg=false;
				firstLeg=false;
			}else{ //There is not any  VOR station with range enough to be detected at some point in the middle of this route
				window.alert(message);
				firstLeg=true;
				lastLeg=false;
			}
		}
		
		//window.alert("all working fine!");
			
		if(firstLeg){ // add departure location to the route polyline
			routeList.push(getFirstLeg());
		}
			
		for (var i = 0;i<navaidList.length;i++){
			
			var navaid = navaidList[i];
			
			var lat = navaid.geolocationLat;
			var lon = navaid.geolocationLon;
			var name = navaid.name;
			var idName = navaid.idName;
			var type = navaid.type;
			
			//var navaidInfo = "[idName="+idName+" , name="+name+" , type="+type+"]";
			routeList.push([lat,lon]); // add navaid location to the route polyline
		}
		
		//window.alert("FIRST LEG"+firstLeg);

		if(lastLeg){ // add arrival location to the route polyline
			routeList.push(getLastLeg());
		}
		
		
		drawNavaidsRoute(routeList);
	}
	
	// return an array with the departure location (departure marker location)
	function getFirstLeg(){
		var latlng = departureMark.getLatLng();
		return [latlng.lat,latlng.lng];
	}
	
	// return an array with the arrival location (arrival marker location)
	function getLastLeg(){
		var latlng = arrivalMark.getLatLng();
		return [latlng.lat,latlng.lng];
	
	}
	
// ********************************************
// ***    SAVE FLIGHT PLAN IMPLENTATION 	***
// ********************************************	
	
	function saveFlightPlan(){
		if(currentNavaidList==null) {
			window.alert("Calculate Flight Plan first.")
		}else{			
			var json = prepareJSONBodyRequest();
			sendSaveFlightPlanRequest(json);
		}
	}
	
	function prepareJSONBodyRequest(){
		
		var flightFromTo = null;
		var route = [];
				
		for(let i=0; i<currentNavaidList.length; i++){
			route.push({ "identifier" : currentNavaidList[i].identifier });
		}
		
		var departureArray = getFirstLeg();
		var arrivalArray = getLastLeg();
		
		flightFromTo = { "departureLocation" : 
						        {
						            "latitude" : departureArray[0],
						            "longitude" : departureArray[1]
						        },
					        "arrivalLocation" : 
						        {
						            "latitude" : arrivalArray[0],
						            "longitude" : arrivalArray[1]
						        }
		   				}; //end flightFromTo
		
		var name = document.getElementById("flightPlanName").value;
		//window.alert("name="+name);
		
		var json = { "flightFromTo" : flightFromTo,
					 "route" : route ,
					 "name" : name };
		
		json = JSON.stringify(json);
		//window.alert(json);

		return json;
    }
		
	function sendSaveFlightPlanRequest(jsonRequest){
		
		$.ajax({
		    url: 'http://localhost:8181/api/flightplans',
			type: "POST",
		    method: "POST", 
		    dataType: "json",
		    contentType: "application/json",
		    data: jsonRequest,
		    success: function (result) {
		        //console.log(result);
				window.alert(JSON.stringify(result));
		    },
		    
		    error: function (error) {
		        console.log(error);
				window.alert("Error connecting or retrieving data with REST service.");
		    }
		});
			
	}
		
	
	