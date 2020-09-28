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
import iag.vornav.dto.NavaidDTO;
import iag.vornav.service.impl.RouteServiceImpl;

/**
 * @author Ivan Alonso
 *
 */
@RestController
@RequestMapping("/api/routes/")
public class RouteController {
	
	@Autowired
	RouteServiceImpl routeServiceImpl;
	
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
	
	
}
