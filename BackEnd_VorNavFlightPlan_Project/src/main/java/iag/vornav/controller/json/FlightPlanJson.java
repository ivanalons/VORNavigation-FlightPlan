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
public class FlightPlanJson {

	private FlightFromTo flightFromTo;
	
	private List<NavaidDTO> route;
	
	private String name;

	public FlightPlanJson() {
		
	}
	
	/**
	 * @param flightFromto
	 * @param route
	 */
	public FlightPlanJson(FlightFromTo flightFromTo, List<NavaidDTO> route) {
		this.flightFromTo = flightFromTo;
		this.route = route;
	}

	public FlightFromTo getFlightFromTo() {
		return flightFromTo;
	}

	public void setFlightFromTo(FlightFromTo flightFromTo) {
		this.flightFromTo = flightFromTo;
	}

	public List<NavaidDTO> getRoute() {
		return route;
	}

	public void setRoute(List<NavaidDTO> route) {
		this.route = route;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
