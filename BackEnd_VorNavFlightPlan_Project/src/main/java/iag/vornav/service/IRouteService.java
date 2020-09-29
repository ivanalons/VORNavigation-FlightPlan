/**

 * 
 */
package iag.vornav.service;

import java.util.List;

import iag.vornav.controller.json.FlightFromTo;
import iag.vornav.dto.NavaidDTO;
import iag.vornav.tools.Coordinate;

/**
 * @author Ivan Alonso
 *
 */
public interface IRouteService {
	
	/**
	 * Simple algorythm to build the route path that goes through the navaids coordinates, that begins
	 * from departure location and ends at arrival location. First approach tested in FrontEnd.
	 * 
	 * @param fromTo : geolocation coordinates departure and arrival
	 * @param route : list of navaids where the flight plan goes through, updated for route calculation
	 * @throws Exception : if "first navaid strategy" fails or "next navaid strategy" fails an exception 
	 * 					   is thrown
	 */
	public void calculateSimpleRoute(FlightFromTo fromTo, List<NavaidDTO> route) throws Exception;
	
	/**
	 * 
	 * This method is called at the beginning of the route calculation, so although this method has 
	 * public visibility, it is only called from "calculateSimpleRoute" or maybe whatever that calculates 
	 * the route.
	 * 
	 * @param departureLocation
	 * @return first navaid chosen as "second" coordinate for the route after departureLocation.
	 * 		   (Comment: departure location and arrival location are not included in the route, 
	 * 					 and both of them aren't navaids, so this is why I've called it "first" navaid)
	 */
	public NavaidDTO detectSimpleFirstNavaid(Coordinate departureLocation);
	
	
}
