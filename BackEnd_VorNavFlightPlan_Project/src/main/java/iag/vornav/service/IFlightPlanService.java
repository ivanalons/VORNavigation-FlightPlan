/**
 * 
 */
package iag.vornav.service;

import java.util.List;

import iag.vornav.controller.json.FlightPlanJson;
import iag.vornav.dto.FlightPlanDTO;

/**
 * @author Ivan Alonso
 *
 */
public interface IFlightPlanService {
	
	public void saveFlightPlan(FlightPlanJson flightPlan) throws Exception;
	
}
