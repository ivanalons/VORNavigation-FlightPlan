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
	
	/**
	 * 
	 * @return the list of all navaids stored in database
	 * 			WARNING : high processing cost querying data from database especially if there are 
	 * 					  navaids in database from different countries
	 * 		   (TODO : return navaids from an specific country)
	 */
	public List<NavaidDTO> getAllNavaids();
	
	/**
	 * Import all XML navaids saved in the Java object representation OPENAIP and store all of them
	 * in database (table navaids)
	 * 
	 * @param openAip - input: Java representation of XML navaids list (in AIP format)
	 */
	public void saveImportNavaids(OPENAIP openAip);
	
	/**
	 * Quadratic cost!
	 * 
	 * Calculate for each navaid which navaids are detectable from its location. And store each navaid
	 * visibility in database (table `range`)
	 * 
	 * Loop over all navaids: each navaid is compared with all other navaids and stores in database 
	 * the relationship "navaid target" is detectable from "navaid source".
	 * 
	 */
	public void calculateNavaidsRange();
}
