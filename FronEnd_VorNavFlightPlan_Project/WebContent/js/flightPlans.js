	function setUpLeafletMap_flightPlans(){ 
		mymap = L.map('mapid').setView([40.2085, -3.713], 6);
		
		mymap.createPane('polylines');
		mymap.getPane('polylines').style.zIndex = 650; // PANE "polylines" CREATED!
		
		L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
			attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
		}).addTo(mymap);

	}
	
	function sendGetFlightPlansRequest(){
		
		$.ajax({
		    url: 'http://localhost:8181/api/flightplans',
		    type: "GET", 
		    dataType: "json",
		    data: {},
		    
		    success: function (result) {
		        //console.log(result);
				//resposta = JSON.stringify(result);
				copyFlightPlansToList(result);
		    },
		    
		    error: function (error) {
		        console.log(error);
			    document.getElementById("mapid").innerHTML = "Query error retrieving all navaids.";
	
		    }
		});	
	}
	
	function copyFlightPlansToList(jsonResponse){

		var flightsArray = jsonResponse.flights;
		
		removeListOptions();

		var select = document.getElementById("flightPlanSelect");			

		
		for(var i=0;i<flightsArray.length;i++){
			let flightId = flightsArray[i].id;
			let flightName = flightsArray[i].name;
				
			select.add(new Option(flightId+ ". "+flightName,flightId));
		}
		
	}
	
	function removeListOptions(){
		$('#flightPlanSelect')
		    .empty()
		    .append('<option value="0">Select flight plan</option>');
	}
	
	function getFlightPlanRequest(flightId){
		//		window.alert(flightId);
		if (flightId==0) {
			resetFlightPlanFromMap();
			return;
		}
		
		$.ajax({
		    url: 'http://localhost:8181/api/flightplans/'+flightId,
		    type: "GET", 
		    dataType: "json",
		    data: {},
		    
		    success: function (result) {
		        //console.log(result);
				//resposta = JSON.stringify(result);
				drawFlightPlanToMap(result);
		    },
		    
		    error: function (error) {
		        console.log(error);
			    document.getElementById("mapid").innerHTML = "Query error retrieving all navaids.";
	
		    }
		});	
	}
	
	function drawFlightPlanToMap(jsonResponse){
		
//		window.alert(JSON.stringify(jsonResponse));
		resetFlightPlanFromMap();
		
		var fromTo = jsonResponse.flightPlan.flightFromTo;
		
		var lat1 = fromTo.departureLocation.latitude;
		var lng1 = fromTo.departureLocation.longitude;
		
		var lat2 = fromTo.arrivalLocation.latitude;
		var lng2 = fromTo.arrivalLocation.longitude;

		
		departureMark = createCircle(lat1,lng1,"DEPARTURE");
		arrivalMark = createCircle(lat2,lng2,"ARRIVAL");

    	
    	processRouteDataToMap(jsonResponse.flightPlan);
	}
	
	function resetFlightPlanFromMap(){
		if (arrivalMark!=null) arrivalMark.remove(mymap);
		if (departureMark!=null) departureMark.remove(mymap);
		
		if(routePolyline!=null) routePolyline.remove(mymap);
	}
	
	function deleteFlightPlan(){
		//window.alert("delete");
		var select = document.getElementById("flightPlanSelect");
		var flightId = select.value;
		
		removeFlightPlanRequest(flightId);
	}
	
	function removeFlightPlanRequest(flightId){
		//		window.alert(flightId);
		if (flightId==0) return;
		
		$.ajax({
		    url: 'http://localhost:8181/api/flightplans/'+flightId,
		    type: "DELETE", 
		    dataType: "json",
		    data: {},
		    
		    success: function (result) {
		        //console.log(result);
				//resposta = JSON.stringify(result);
		    	//window.alert("OK");
		    	sendGetFlightPlansRequest();
		    },
		    
		    error: function (error) {
		        console.log(error);
			    document.getElementById("mapid").innerHTML = "Query error retrieving all navaids.";
	
		    }
		});	
	}