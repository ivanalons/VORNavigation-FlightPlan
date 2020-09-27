	
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
				
	}
	
	function onMapClick(e) {
		alert("You clicked the map at " + e.latlng);
	}

	
	
	