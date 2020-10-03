/**
 * 
 */
package iag.vornav.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
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

	@Override
	public FlightPlanJson getFlightPlanByFlightId(Long flightId) {
		
		FlightPlanJson json = new FlightPlanJson();
		
		FlightDTO flight = iFlightDAO.findById(flightId).get();
		
		FlightFromTo fromTo = getFromToLocations(flight);
		json.setFlightFromTo(fromTo);
		
		List<FlightPlanDTO> listFlightPlan = flight.getFlightPlanList();

		List<NavaidDTO> route = getNavaidsListFromFlightPlan(listFlightPlan);
		json.setRoute(route);
		
		return json;
	}
	
	private FlightFromTo getFromToLocations(FlightDTO flight) {
		FlightFromTo fromTo =  new FlightFromTo();
		
		double lat1 = flight.getDeparture_lat();
		double lng1 = flight.getDeparture_lng();
		Coordinate c1 = new Coordinate(lat1,lng1);
		
		double lat2 = flight.getArrival_lat();
		double lng2 = flight.getArrival_lng();
		Coordinate c2 = new Coordinate(lat2,lng2);
		
		fromTo.setDepartureLocation(c1);
		fromTo.setArrivalLocation(c2);

		return fromTo;
	}

	private List<NavaidDTO> getNavaidsListFromFlightPlan(List<FlightPlanDTO> listFlightPlan){
		
		List<NavaidDTO> listNavaidDTO = new ArrayList<>();
		
		//TO CHECK is sorting needed???
		listFlightPlan.sort( Comparator.comparingInt(p -> p.getStep()) );  
		//(FlightPlanDTO f1, FlightPlanDTO f2) -> (f1.getStep() - f2.getStep());
		
		for(FlightPlanDTO fp : listFlightPlan) {
			NavaidDTO navaid = fp.getNavaid();
			listNavaidDTO.add(navaid);
		}
		
		return listNavaidDTO;
		
	}

	
	@Override
	public List<FlightDTO> getAllFlights() {
		return iFlightDAO.findAll();
	}

	@Override
	public void removeFlightPlan(Long flightId) {

		FlightDTO flight = iFlightDAO.findById(flightId).get();
		
		List<FlightPlanDTO> flightPlan = flight.getFlightPlanList();
		
		iFlightPlanDAO.deleteAll(flightPlan);
		iFlightDAO.delete(flight);
		
	}

}
