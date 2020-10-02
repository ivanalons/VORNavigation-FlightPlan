/**
 * 
 */
package iag.vornav.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iag.vornav.controller.json.FlightFromTo;
import iag.vornav.controller.json.FlightPlanJson;
import iag.vornav.dao.IFlightDAO;
import iag.vornav.dao.IFlightPlanDAO;
import iag.vornav.dao.INavaidDAO;
import iag.vornav.dto.FlightDTO;
import iag.vornav.dto.FlightPlanDTO;
import iag.vornav.dto.NavaidDTO;
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
	
	@Autowired
	IFlightPlanDAO iFlightPlanDAO;
	
	@Autowired
	INavaidDAO iNavaidDAO;
	
	@Override
	public void saveFlightPlan(FlightPlanJson flightPlan) throws Exception {

		FlightFromTo fromTo = flightPlan.getFlightFromTo();
		
		Coordinate c1 = fromTo.getDepartureLocation();
		double lat1 = c1.getLatitude();
		double lng1 = c1.getLongitude();
		
		Coordinate c2 = fromTo.getArrivalLocation();
		double lat2 = c2.getLatitude();
		double lng2 = c2.getLongitude();
		
		String flightName = flightPlan.getName();
		
		FlightDTO flightDTO = new FlightDTO(flightName,lat1,lng1,lat2,lng2);
		FlightDTO createdFlightDTO = iFlightDAO.save(flightDTO);
		
		saveRouteInFlightPlan(createdFlightDTO, flightPlan.getRoute() );
		
	}
	
	// The only field not null in list NavaidDTO is "identifier" list
	// TODO save flightPlan by identifier without querying for each navaid in route 
	private void saveRouteInFlightPlan(FlightDTO flight, List<NavaidDTO> navaidsRoute) throws Exception {

		List<FlightPlanDTO> listFlightPlan = new ArrayList<>();
		NavaidDTO navaid = null;
		int step = 1;
		
		for (NavaidDTO navId : navaidsRoute ) {
			try {
				navaid = iNavaidDAO.findById(navId.getIdentifier()).get();
			}catch(Exception e) {
				throw new Exception("Error trying to findById the navaid with identifier "+ 
									 navId.getIdentifier());
			}
			
			FlightPlanDTO flightPlan = new FlightPlanDTO(flight,step,navaid);
			listFlightPlan.add(flightPlan);
			
			step++;
		}
		
		iFlightPlanDAO.saveAll(listFlightPlan);
	}

}
