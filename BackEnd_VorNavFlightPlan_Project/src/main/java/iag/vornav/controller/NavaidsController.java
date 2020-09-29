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
	 * Request URL Example: http://localhost:8181/api/navaids
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
	
	
	@PostMapping(value="/navaids", consumes = MediaType.APPLICATION_XML_VALUE,
			                      produces = MediaType.APPLICATION_JSON_VALUE )
	public Map<String,Object> saveImportNavaids(@RequestBody OPENAIP openaip) {
		
		navaidServiceImpl.saveImportNavaids(openaip);
		
		Map<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "Navaids imported correctly.");
		return map;
	}
	
	@PostMapping(value="/navaids/range")
	public Map<String,Object> calculateNavaidsRange(){
		
		navaidServiceImpl.calculateNavaidsRange();
		
		Map<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "Navaids range calculated correctly.");
		return map;
	}
	
}
