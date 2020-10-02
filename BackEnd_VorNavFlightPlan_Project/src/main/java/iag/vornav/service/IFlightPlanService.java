/**
 * 
 */
package iag.vornav.service;

import java.util.List;

import iag.vornav.controller.json.FlightPlanJson;
import iag.vornav.dto.FlightDTO;

/**
 * @author Ivan Alonso
 *
 */
public interface IFlightPlanService {
	
	public void saveFlightPlan(FlightPlanJson flightPlan) throws Exception;
	
	public FlightPlanJson getFlightPlanById(Long flightId);
	
	public List<FlightDTO> getAllFlights();

}
