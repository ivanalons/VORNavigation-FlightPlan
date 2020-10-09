package iag.vornav.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iag.vornav.controller.xml.navaids.OPENAIP;
import iag.vornav.dto.NavaidDTO;
import iag.vornav.service.impl.NavaidServiceImpl;
/**
 * 
 * @author Ivan Alonso
 *
 */
@RestController
@RequestMapping("/api")
public class NavaidsController {

	@Autowired
	NavaidServiceImpl navaidServiceImpl;
	
	/**
	 * Request URL Example: [GET method] http://localhost:8181/api/navaids
	 * Request Body: none
	 * 
	 * @return list of all navaids stored in database
	 */
	@GetMapping(value="/navaids", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> getAllNavaids(){
		
		List<NavaidDTO> navaidsList = navaidServiceImpl.getAllNavaids();
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("navaids", navaidsList);
		map.put("success", true);
		map.put("message", "Navaids imported correctly.");
		
		return map;
	}
	
	/**
	 * PAY ATTENTION: FIRST STEP! This service should be called at first to store navaids in database.
	 * 		If there isn't any navaid stored in database the remaining code is useless
	 * 		You can call several times this service with XML data from different countries to 
	 * 		test the software. Navaids from different countries have different identifiers so
	 * 		storing navaids from different countries won't produce conflicts.
	 * 
	 * Request URL Example: [POST method] http://localhost:8181/api/navaids
	 * Request Body: XML with .aip format
	 * 
	 * @param openaip - navaids list in XML format from openaip.net openAIP - Worldwide aviation database
	 * 					(XML content from openaip_navaid_[countryName]_[countryCcode].aip)
	 * 					For downloading an .aip file you need to sign up in www.openaip.net 
	 * 
	 * @return 
	 */
	@PostMapping(value="/navaids", consumes = MediaType.APPLICATION_XML_VALUE,
			                      produces = MediaType.APPLICATION_JSON_VALUE )
	public Map<String,Object> saveImportNavaids(@RequestBody OPENAIP openaip) {
		
		navaidServiceImpl.saveImportNavaids(openaip);
		
		Map<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "Navaids imported correctly.");
		return map;
	}
	
	/**
	 * PAY ATTENTION: SECOND STEP! This service should be called after importing navaids to database.
	 * 		This service has no explicit input nor output (except success and message json parameters)
	 * 		The business logic below this method is to associate for every navaid which other navaids
	 * 		are detectable based on their range. So navaids visibility from every navaid can be 
	 * 		retrieved efficiently without any calculation more than a query database.
	 * 		Only create relationships in database between navaids sources and detectable targets.
	 * 
	 * @return
	 */
	@PostMapping(value="/navaids/range")
	public Map<String,Object> calculateNavaidsRange(){
		
		navaidServiceImpl.calculateNavaidsRange();
		
		Map<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "Navaids range calculated correctly.");
		return map;
	}
	
	@GetMapping(value="/navaids/range/percentageProcessed")
	public Map<String,Object> getNavaidsRangeProcessed(){
		
		double rangeProcessed = navaidServiceImpl.getNavaidsRangeProcessed();
		
		Map<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("progress", String.format("%.5f", rangeProcessed));
		map.put("message", "Percentage progress of navaids range calculated.");
		return map;
	}
	
	
}
