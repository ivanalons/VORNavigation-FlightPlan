/**
 * 
 */
package iag.vornav.service;

import org.springframework.stereotype.Service;

import iag.vornav.controller.json.FlightPlanJson;
import iag.vornav.dto.FlightPlanDTO;

/**
 * @author Ivan Alonso
 *
 */
public interface IFlightPlanService {
	
	public FlightPlanDTO saveFlightPlan(FlightPlanJson flightPlan);
	
}
