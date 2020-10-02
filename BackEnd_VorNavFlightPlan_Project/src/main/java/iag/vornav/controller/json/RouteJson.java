/**
 * 
 */
package iag.vornav.controller.json;

import java.util.List;

import iag.vornav.dto.NavaidDTO;

/**
 * @author Ivan Alonso
 *
 */
public class RouteJson {

	private FlightFromTo flightFromTo;
	
	private List<NavaidDTO> route;

	public RouteJson() {
		
	}
	
	/**
	 * @param flightFromto
	 * @param route
	 */
	public RouteJson(FlightFromTo flightFromTo, List<NavaidDTO> route) {
		this.flightFromTo = flightFromTo;
		this.route = route;
	}

	public FlightFromTo getFlightFromTo() {
		return flightFromTo;
	}

	public void setFlightFromto(FlightFromTo flightFromTo) {
		this.flightFromTo = flightFromTo;
	}

	public List<NavaidDTO> getRoute() {
		return route;
	}

	public void setRoute(List<NavaidDTO> route) {
		this.route = route;
	}
	
}
