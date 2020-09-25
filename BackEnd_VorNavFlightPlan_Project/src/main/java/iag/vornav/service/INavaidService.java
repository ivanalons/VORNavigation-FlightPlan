/**

 * 
 */
package iag.vornav.service;

import iag.vornav.controller.xml.navaids.OPENAIP;

/**
 * @author Ivan Alonso
 *
 */
public interface INavaidService {
	
	public void saveImportNavaids(OPENAIP openAip);
	
	public void calculateNavaidsRange();
}
