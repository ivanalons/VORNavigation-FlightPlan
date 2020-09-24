package iag.vornav.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NavaidsImportController {

	@PostMapping("/navaids")
	public Map<String,Object> saveImportNavaids() {
		Map<String,Object> map = new HashMap<>();
		map.put("hello", "world");
		return map;
	}
	
}
