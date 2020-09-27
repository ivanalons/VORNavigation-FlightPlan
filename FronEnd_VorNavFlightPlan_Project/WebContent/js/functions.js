	var mymap = null; //global variable to operate with javaScript with Leaflet map
	var routePolyline = null; //global variable to remove polylines for route drawings
	
	function setUpLeafletMap(){
		mymap = L.map('mapid').setView([40.2085, -3.713], 6);
		
		mymap.createPane('polylines');
		mymap.getPane('polylines').style.zIndex = 650;
		
		L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
			attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
		}).addTo(mymap);

		mymap.on('click', onMapClick);
	}
	
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
	
	function processNavaidsDataToMap(jsonResponse){
				
		var navaidList = jsonResponse.navaids;
				
		for (var i = 0;i<navaidList.length;i++){
			
			var navaid = navaidList[i];
			
			var lat = navaid.geolocationLat;
			var lon = navaid.geolocationLon;
			var name = navaid.name;
			var idName = navaid.idName;
			var type = navaid.type;
			
			var navaidInfo = "[idName="+idName+" , name="+name+" , type="+type+"]";
			
			var marker = L.marker([lat, lon]);
			//marker.setOpacity(0.5);
			marker.addTo(mymap).bindPopup(navaidInfo);
			
		}
		
		testNavaidsRoute(navaidList[1],navaidList[10],navaidList[20]); 
		
	}
	
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
	
	function onMapClick(e) {
		//alert("You clicked the map at " + e.latlng);
		var lat = e.latlng.lat;
		var lng = e.latlng.lng;
		//alert(lat+","+lng);
		
		var d = document.getElementById("lat1").value;		
		
		if(d==""){ 
			document.getElementById("lat1").value = lat; 
			document.getElementById("lng1").value = lng; 
			
			document.getElementById("departureMessage").innerHTML = ""; 
			document.getElementById("arrivalMessage").innerHTML = "Click on the map the arrival location"; 
		}else{
			document.getElementById("lat2").value = lat; 
			document.getElementById("lng2").value = lng;
			
			document.getElementById("arrivalMessage").innerHTML = ""; 
		}
	}

	function resetLocations(){
				
		document.getElementById("lat1").value = ""; 
		document.getElementById("lng1").value = ""; 
	
		document.getElementById("lat2").value = ""; 
		document.getElementById("lng2").value = "";
		
		document.getElementById("arrivalMessage").innerHTML = ""; 
		document.getElementById("departureMessage").innerHTML = "Click on the map the departure location"; 

	}
	
	function calculateFlightPlan(){
		
		var lat1 = document.getElementById("lat1").value; 
		var lng1 = document.getElementById("lng1").value; 
	
		var lat2 = document.getElementById("lat2").value; 
		var lng2 = document.getElementById("lng2").value;
		
		window.alert("Missing implementation");
		
		routePolyline.remove(mymap);
	}