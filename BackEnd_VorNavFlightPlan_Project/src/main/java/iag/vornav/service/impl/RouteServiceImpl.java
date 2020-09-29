/**
 * 
 */
package iag.vornav.service.impl;

import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iag.vornav.controller.json.FlightFromTo;
import iag.vornav.dao.INavaidDAO;
import iag.vornav.dto.NavaidDTO;
import iag.vornav.dto.RangeDTO;
import iag.vornav.service.IRouteService;
import iag.vornav.tools.Coordinate;
import iag.vornav.tools.HaversineDistance;
import iag.vornav.tools.MathTools;

/**
 * @author Ivan Alonso
 *
 */
@Service
public class RouteServiceImpl implements IRouteService{

	@Autowired
	INavaidDAO iNavaidDAO;
	
	@Override
	public void calculateSimpleRoute(FlightFromTo fromTo, List<NavaidDTO> route) throws Exception{

		NavaidDTO firstNavaid = detectSimpleFirstNavaid(fromTo.getDepartureLocation());
		
		if (firstNavaid==null) throw new Exception("There is not any navaid station with range enough"
												  +"to be detected at departure location");
		// CALCULATE FLIGHT PLAN
		
		boolean lastNavaid = false;
		double remainingDistance = HaversineDistance.calculateDistance(firstNavaid, 
																	   fromTo.getArrivalLocation());
//		double traveledDistance = 0;
		NavaidDTO currentNavaid = firstNavaid;
		
		while(lastNavaid==false) {
			
			List<RangeDTO> detectableNavaids = currentNavaid.getRangeList();
			NavaidDTO nextNavaid = routeSimpleNextNavaid(detectableNavaids, fromTo.getArrivalLocation());
			
			double newDistance = HaversineDistance.calculateDistance(nextNavaid,fromTo.getArrivalLocation() );
			if ( newDistance > remainingDistance ) {
				throw new Exception(" There is not any  VOR station with range enough to be detected"+
									" at some point in the middle of this route");
			}
			route.add(nextNavaid);
			currentNavaid = nextNavaid;

			remainingDistance = newDistance;
			lastNavaid = isLocationDetectableFromNavaid(fromTo.getArrivalLocation(), nextNavaid);
		}
		
	}

	@Override
	public NavaidDTO detectSimpleFirstNavaid(Coordinate departureLocation) {
		
		List<NavaidDTO> navaidList = iNavaidDAO.findAll();
		ListIterator<NavaidDTO> it = navaidList.listIterator();
		
		boolean found = false;
		NavaidDTO currentNavaid = null;
		NavaidDTO detectedNavaid = null;
		
		while(it.hasNext() && found==false) {
			
			currentNavaid = it.next();
			
			if (isLocationDetectableFromNavaid(departureLocation, currentNavaid)) {
				found=true;
				detectedNavaid = currentNavaid;
			}
			
		}
		
		return detectedNavaid;
	}

	/**
	 * Returns the target navaid, from detectableNavaids list, nearest to the arrivalLocation
	 * 
	 * @param detectableNavaids
	 * @param arrivalLocation
	 * @return
	 */
	private NavaidDTO routeSimpleNextNavaid(List<RangeDTO> detectableNavaids, Coordinate arrivalLocation) {
		
		double min = 100000;
		NavaidDTO nextNavaid = null;
		
		for( RangeDTO range : detectableNavaids ) {
			
			NavaidDTO targetNavaid = range.getNavaidTarget();
			
			double distance = HaversineDistance.calculateDistance(targetNavaid, arrivalLocation);
			
			if (distance<min) {
				min = distance;
				nextNavaid = targetNavaid;
			}
		}
		return nextNavaid;
	}
	
	/**
	 * Returns true if the location is detectable from navaid range, else false
	 * 
	 * @param location
	 * @param navaid
	 * @return
	 */
	private boolean isLocationDetectableFromNavaid(Coordinate location, NavaidDTO navaid) {
				
		double distance = HaversineDistance.calculateDistance(location, navaid);
		double navaidRange = MathTools.convertNMToKm(navaid.getParamRange());
	
		if(distance<=navaidRange) return true;
		
		return false;
	}
	
}
