/**
 * 
 */
package iag.vornav.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iag.vornav.controller.xml.navaids.ELEV;
import iag.vornav.controller.xml.navaids.GEOLOCATION;
import iag.vornav.controller.xml.navaids.NAVAID;
import iag.vornav.controller.xml.navaids.OPENAIP;
import iag.vornav.controller.xml.navaids.PARAMS;
import iag.vornav.controller.xml.navaids.RADIO;
import iag.vornav.controller.xml.navaids.RANGE;
import iag.vornav.dao.INavaidDAO;
import iag.vornav.dao.IRangeDAO;
import iag.vornav.dto.NavaidDTO;
import iag.vornav.dto.RangeDTO;
import iag.vornav.service.INavaidService;
import iag.vornav.tools.HaversineDistance;
import iag.vornav.tools.MathTools;

/**
 * @author Ivan Alonso
 *
 */
@Service
public class NavaidServiceImpl implements INavaidService{

	@Autowired
	INavaidDAO iNavaidDAO;
	
	@Autowired
	IRangeDAO iRangeDAO;
	
	public final Integer defaultRange = 100; //DEFAULT RANGE FOR THOSE NAVAIDS WITH NULL RANGE
	
	private double rangeProcessed = 0; // Total percentage of navaids range processed
	
	/**
	 * 
	 * Store in database the Java object representation of a list of navaids in format openAIP for navaids.
	 *
	 */
	@Override
	public void saveImportNavaids(OPENAIP openAip) { //TODO clean code
		
		List<NAVAID> xmlNavaidList = openAip.getNavaidsList();
		
		List<NavaidDTO> navaidDTOList = new ArrayList<>();
		
		for( NAVAID xmlNavaid : xmlNavaidList ) {
			
			NavaidDTO navaidDTO = new NavaidDTO();
			
			navaidDTO.setIdentifier((long)xmlNavaid.getIDENTIFIER());
			navaidDTO.setType(xmlNavaid.getTYPE());
			navaidDTO.setCountry(xmlNavaid.getCOUNTRY());
			navaidDTO.setName(xmlNavaid.getNAME());
			navaidDTO.setIdName(xmlNavaid.getID());
			
			setGeolocationFromXML(xmlNavaid, navaidDTO);
			
			setRadioFromXML(xmlNavaid,navaidDTO);
			
			PARAMS xmlParams = xmlNavaid.getParams();
			
			navaidDTO.setParamDeclination(xmlParams.getDECLINATION());
			
			boolean north = Boolean.getBoolean(xmlParams.getALIGNEDTOTRUENORTH());
			navaidDTO.setParamAlignedToTrueNorth(north);

			RANGE xmlRange = xmlParams.getRange();
			if(xmlRange!=null) {
				navaidDTO.setParamRange(xmlRange.getValue());
				navaidDTO.setParamRangeUnit(xmlRange.getUNIT());
			}else {
				navaidDTO.setParamRange(defaultRange); // DEFAULT RANGE - TODO THINK ABOUT IT!
			}
			
			navaidDTOList.add(navaidDTO);
			
		}
		
		iNavaidDAO.saveAll(navaidDTOList);
		
	}
	
	/**
	 * Extracts information that belongs to xml data and saves it into a dto object NavaidDTO
	 * 
	 * @param xmlNavaid: Java XML data
	 * @param navaidDTO: Java JPA entity
	 */
	private void setGeolocationFromXML(NAVAID xmlNavaid, NavaidDTO navaidDTO) {
		GEOLOCATION xmlGeo = xmlNavaid.getGeoLocation();
		navaidDTO.setGeolocationLat(xmlGeo.getLAT());
		navaidDTO.setGeolocationLon(xmlGeo.getLON());
		
		ELEV xmlElev = xmlGeo.getElev();
		navaidDTO.setGeolocationElevUnit(xmlElev.getUNIT());
		navaidDTO.setGeolocationElev(xmlElev.getValue());
	}
	
	/**
	 * Extracts information that belongs to xml data and saves it into a dto object NavaidDTO
	 * 
	 * @param xmlNavaid: Java XML data
	 * @param navaidDTO: Java JPA entity
	 */
	private void setRadioFromXML(NAVAID xmlNavaid, NavaidDTO navaidDTO) {
		RADIO xmlRadio = xmlNavaid.getRadio();
		navaidDTO.setRadioFrequency(xmlRadio.getFREQUENCY());
		navaidDTO.setRadioChannel(xmlRadio.getCHANNEL());
	}

	/**
	 * 
	 * Stores in database table "`range`" for each navaid in table "navaids" the relationship:
	 * 		FROM "source navaid" IS DETECTED "target navaid" 
	 * 		based on if target range achieves source navaid location  
	 * 
	 */
	@Override
	public void calculateNavaidsRange() {

//		testcalculateNavaidsRange();
		List<NavaidDTO> navaidList = iNavaidDAO.findAll();
		
		int listSize = navaidList.size();
		int i = 0;
		int j = 0;
		
		for( NavaidDTO sourceNavaid : navaidList ) {
			
			j=0;
			
			for( NavaidDTO targetNavaid : navaidList ) {
							
				boolean sameNavaid = sourceNavaid.getIdentifier()==targetNavaid.getIdentifier();
				
				if(sameNavaid==false) {
					
					// to find target detectable from sourceNavaid
					// the targetNavaid range must be considered!!! (not the sourceNavaid range)
					double kmRange_targetNavaid = MathTools.convertNMToKm(targetNavaid.getParamRange());
					
					double distance = HaversineDistance.calculateDistance(sourceNavaid, targetNavaid);
					
					if(distance < kmRange_targetNavaid) {
						saveNavaidRange(sourceNavaid,targetNavaid);
					}
					
				}
				updatePercentageNavaidsProcessed(listSize,i,j);
				j++;
			}

			printPercentageNavaidsProcessed(listSize,i); // print in console - TO COMMENT
			i++;
		}
		
		updatePercentageNavaidsProcessed(listSize,i,0);
		printPercentageNavaidsProcessed(listSize,i); // print in console - TO COMMENT

	}
	
	/**
	 * 
	 * Insert a row in table "range" with navaidSource id and navaidTarget id
	 * 
	 * @param navaidSource
	 * @param navaidTarget
	 * @return a DTO object with the new range instance created in database
	 */
	private RangeDTO saveNavaidRange(NavaidDTO navaidSource, NavaidDTO navaidTarget) {
		
		RangeDTO rangeDTO = new RangeDTO(navaidSource,navaidTarget);
		return iRangeDAO.save(rangeDTO);
		
	}

	/**
	 * Return the list of all navaids stored in database
	 */
	@Override
	public List<NavaidDTO> getAllNavaids() {

		return iNavaidDAO.findAll();
		
	}

	private void updatePercentageNavaidsProcessed(int listSize, int i, int j) {
		
		this.rangeProcessed = 100*((double) ( i*listSize + j )) / ((double)(listSize*listSize));
		
	}
	
	private void printPercentageNavaidsProcessed(int listSize, int i) {
		System.out.println("Range processed: [ Percentage=" + 
		String.format("%.5f", this.rangeProcessed) + " / 100 ] [ Navaid "+
						(i+1) + " of " + listSize+ " ]");
	}
	
	@Override
	public double getNavaidsRangeProcessed() {
		
		return this.rangeProcessed;
	} 
	
	/**
	 * 
	 * Method prepared only for testing purposes
	 * 
	 */
	public void testcalculateNavaidsRange() {
		
//		List<NavaidDTO> navaidList = iNavaidDAO.findAll();
		
		NavaidDTO navaid1 = iNavaidDAO.findById(1744059L).get();
		NavaidDTO navaid2 = iNavaidDAO.findById(1744061L).get();
		NavaidDTO navaid3 = iNavaidDAO.findById(2630060L).get();
		NavaidDTO navaid4 = iNavaidDAO.findById(1744062L).get();

		System.out.println(navaid1.toString());
		System.out.println(navaid2.toString());
		System.out.println(navaid3.toString());
		System.out.println(navaid4.toString());

		RangeDTO range2 = new RangeDTO(navaid1,navaid2);
		RangeDTO range3 = new RangeDTO(navaid1,navaid3);
		RangeDTO range4 = new RangeDTO(navaid1,navaid4);
		
		iRangeDAO.save(range2);
		iRangeDAO.save(range3);
		iRangeDAO.save(range4);
		
		navaid1 = iNavaidDAO.findById(1744059L).get();
		List<RangeDTO> rangeList = navaid1.getRangeList();
		for(RangeDTO r : rangeList) {
			System.out.println(r.toString());
		}
	}
	
}
