/**
 * 
 */
package iag.vornav.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iag.vornav.controller.json.FlightPlanJson;
import iag.vornav.dto.FlightDTO;
import iag.vornav.service.impl.FlightPlanServiceImpl;

/**
 * @author Ivan Alonso
 *
 */
@RestController
@RequestMapping("/api/flightplans")
public class FlightPlanController {

	@Autowired
	FlightPlanServiceImpl flightPlanServiceImpl;

	@PostMapping
	public Map<String, Object> saveFlightPlan(@RequestBody FlightPlanJson flightPlanJson) {

		Map<String, Object> map = new HashMap<>();

		try {
			flightPlanServiceImpl.saveFlightPlan(flightPlanJson);
			map.put("success", true);
			map.put("message", "Flight Plan saved.");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.getMessage());
		}

		return map;

	}

	@GetMapping("/{id}")
	public Map<String, Object> getFlightPlanById(@PathVariable(name = "id") Long flightId) {

		Map<String, Object> map = new HashMap<>();

		FlightPlanJson json = flightPlanServiceImpl.getFlightPlanByFlightId(flightId);

		map.put("flightPlan", json);
		map.put("success", true);
		map.put("message", "Returned Flight Plan OK.");

		return map;

	}

	@GetMapping("")
	public Map<String, Object> getAllFlights() {

		Map<String, Object> map = new HashMap<>();

		List<FlightDTO> listFlights = flightPlanServiceImpl.getAllFlights();

		map.put("flights", listFlights);
		map.put("success", true);
		map.put("message", "Returned all flights OK.");

		return map;

	}
	
	@DeleteMapping("{id}")
	public Map<String, Object> removeFlightPlan(@PathVariable(name="id") Long flightId) {

		Map<String, Object> map = new HashMap<>();

		flightPlanServiceImpl.removeFlightPlan(flightId);

		map.put("success", true);
		map.put("message", "Removed flight plan by id OK.");

		return map;

	}
}
