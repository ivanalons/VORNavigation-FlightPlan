/**
 * 
 */
package iag.vornav.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iag.vornav.controller.json.FlightFromTo;
import iag.vornav.controller.json.FlightPlanJson;
import iag.vornav.dao.IFlightDAO;
import iag.vornav.dto.FlightDTO;
import iag.vornav.dto.FlightPlanDTO;
import iag.vornav.service.IFlightPlanService;
import iag.vornav.tools.Coordinate;

/**
 * @author Ivan Alonso
 *
 */
@Service
public class FlightPlanServiceImpl implements IFlightPlanService{

	@Autowired
	IFlightDAO iFlightDAO;
	
	@Override
	public FlightPlanDTO saveFlightPlan(FlightPlanJson flightPlan) {

		FlightFromTo fromTo = flightPlan.getFlightFromTo();
		
		Coordinate c1 = fromTo.getDepartureLocation();
		double lat1 = c1.getLatitude();
		double lng1 = c1.getLongitude();
		
		Coordinate c2 = fromTo.getArrivalLocation();
		double lat2 = c2.getLatitude();
		double lng2 = c2.getLongitude();
		
		String flightName = flightPlan.getName();
		
		FlightDTO flightDTO = new FlightDTO(flightName,lat1,lng1,lat2,lng2);
		FlightDTO createdFlight = iFlightDAO.save(flightDTO);
		
		return null;
		
	}

}
