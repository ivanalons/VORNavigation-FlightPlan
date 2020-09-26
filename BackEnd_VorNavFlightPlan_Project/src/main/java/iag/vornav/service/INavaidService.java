/**

 * 
 */
package iag.vornav.service;

import java.util.List;

import iag.vornav.controller.xml.navaids.OPENAIP;
import iag.vornav.dto.NavaidDTO;

/**
 * @author Ivan Alonso
 *
 */
public interface INavaidService {
	
	public List<NavaidDTO> getAllNavaids();
	
	public void saveImportNavaids(OPENAIP openAip);
	
	public void calculateNavaidsRange();
}
