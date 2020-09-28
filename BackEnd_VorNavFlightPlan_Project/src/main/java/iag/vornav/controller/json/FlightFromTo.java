/**
 * 
 */
package iag.vornav.controller.json;

import iag.vornav.tools.Coordinate;

/**
 * @author Ivan Alonso
 *
 */
public class FlightFromTo {

	Coordinate departureLocation;
	Coordinate arrivalLocation;
	
	public FlightFromTo(){
		
	}
	
	public FlightFromTo(Coordinate departureLocation, Coordinate arrivalLocation) {
		this.departureLocation=departureLocation;
		this.arrivalLocation=arrivalLocation;
	}

	public Coordinate getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(Coordinate departureLocation) {
		this.departureLocation = departureLocation;
	}

	public Coordinate getArrivalLocation() {
		return arrivalLocation;
	}

	public void setArrivalLocation(Coordinate arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}
	
	
}
