package iag.vornav.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iag.vornav.controller.xml.navaids.OPENAIP;
import iag.vornav.service.impl.NavaidServiceImpl;
/**
 * 
 * @author Ivan Alonso
 *
 */
@RestController
@RequestMapping("/api")
public class NavaidsImportController {

	@Autowired
	NavaidServiceImpl navaidServiceImpl;
	
	
	@PostMapping(value="/navaids", consumes = MediaType.APPLICATION_XML_VALUE,
			                      produces = MediaType.APPLICATION_JSON_VALUE )
	public Map<String,Object> saveImportNavaids(@RequestBody OPENAIP openaip) {
		
		navaidServiceImpl.saveImportNavaids(openaip);
		
		Map<String,Object> map = new HashMap<>();
		map.put("hello", "world");
		return map;
	}
	
}
