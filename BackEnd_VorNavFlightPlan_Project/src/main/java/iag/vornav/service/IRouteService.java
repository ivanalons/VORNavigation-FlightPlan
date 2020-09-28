/**

 * 
 */
package iag.vornav.service;

import java.util.List;

import iag.vornav.controller.json.FlightFromTo;
import iag.vornav.dto.NavaidDTO;
import iag.vornav.tools.Coordinate;

/**
 * @author Ivan Alonso
 *
 */
public interface IRouteService {
	
	public void calculateSimpleRoute(FlightFromTo fromTo, List<NavaidDTO> route) throws Exception;
	
	public NavaidDTO detectSimpleFirstNavaid(Coordinate departureLocation);
	
	
}
