/**
 * 
 */
package iag.vornav.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iag.vornav.controller.json.FlightFromTo;
import iag.vornav.controller.json.FlightPlanJson;
import iag.vornav.dto.FlightPlanDTO;
import iag.vornav.dto.NavaidDTO;
import iag.vornav.service.impl.FlightPlanServiceImpl;
import iag.vornav.service.impl.RouteServiceImpl;

/**
 * @author Ivan Alonso
 *
 */
@RestController
@RequestMapping("/api/routes")
public class RouteController {
	
	@Autowired
	FlightPlanServiceImpl flightPlanServiceImpl;
	
	@Autowired
	RouteServiceImpl routeServiceImpl;
	
	/**
	 * PAY ATTENTION: THIRD STEP!: Get the route path (navaids list) from departure location to arrival
	 * 							  location. 
	 * 		Previous steps: (see NavaidsController methods)
	 * 						1) POST /api/navaids (XML content with the navaids list in HTTP Request Body)
	 * 						2) POST /api/navaids/range
	 * 
	 * Request URL Example: [POST method] http://localhost:8181/api/routes/strategy/simple
	 * Request Body: param flightFromTo
	 * 
	 * @param flightFromTo - includes departure_location and arrival_location coordinates
	 * @return the route path: the list of navaids that represent the coordinates where the route will
	 *                         pass. Departure and Arrival locations are not included in the response. 
	 *                         The algorythm, that calculates the route path, is the most simple. 
	 */
	@PostMapping(value="/strategy/simple")
	public Map<String,Object> calculateSimpleRoute(@RequestBody FlightFromTo flightFromTo){

		List<NavaidDTO> navaidList = new ArrayList<>();

		Map<String,Object> map = new HashMap<>();
		
		try {
			
			System.out.println("FROM: "+flightFromTo.getDepartureLocation().toString());
			System.out.println("TO:   "+flightFromTo.getArrivalLocation().toString());
			
			routeServiceImpl.calculateSimpleRoute(flightFromTo,navaidList);
			
			map.put("route",navaidList);
			map.put("success", true);
			map.put("message", "Simple route calculated correctly.");
			
		}catch(Exception e) {
			
			map.put("route",navaidList);
			map.put("success", false);
			map.put("message", e.getMessage());
		}
		
		return map;
	}
	
	@GetMapping(value="/strategy/heuristic")
	public Map<String,Object> calculateHeuristicRoute(){
		
		Map<String,Object> map = new HashMap<>();
		map.put("success", false);
		map.put("message", "Not implemented yet.");
		
		return map;
		
	}
	
	//TODO move this method to FlightPlan Controller ???
	@PostMapping
	public Map<String,Object> saveFlightPlan(@RequestBody FlightPlanJson flightPlanJson){
		
		flightPlanServiceImpl.saveFlightPlan(flightPlanJson);
		
		Map<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "Flight Plan saved.");
		
		return map;
		
	}
	
}
