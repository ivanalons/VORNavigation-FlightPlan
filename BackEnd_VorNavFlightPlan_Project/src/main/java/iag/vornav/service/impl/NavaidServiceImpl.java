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
import iag.vornav.tools.Coordinate;
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
											 // NOT AVG FROM ALL NOT NULL RANGES 
			}
			
			navaidDTOList.add(navaidDTO);
			
		}
		
		iNavaidDAO.saveAll(navaidDTOList);
		
	}
	
	private void setGeolocationFromXML(NAVAID xmlNavaid, NavaidDTO navaidDTO) {
		GEOLOCATION xmlGeo = xmlNavaid.getGeoLocation();
		navaidDTO.setGeolocationLat(xmlGeo.getLAT());
		navaidDTO.setGeolocationLon(xmlGeo.getLON());
		
		ELEV xmlElev = xmlGeo.getElev();
		navaidDTO.setGeolocationElevUnit(xmlElev.getUNIT());
		navaidDTO.setGeolocationElev(xmlElev.getValue());
	}
	
	private void setRadioFromXML(NAVAID xmlNavaid, NavaidDTO navaidDTO) {
		RADIO xmlRadio = xmlNavaid.getRadio();
		navaidDTO.setRadioFrequency(xmlRadio.getFREQUENCY());
		navaidDTO.setRadioChannel(xmlRadio.getCHANNEL());
	}

	@Override
	public void calculateNavaidsRange() {

//		testcalculateNavaidsRange();
		List<NavaidDTO> navaidList = iNavaidDAO.findAll();
		
		for( NavaidDTO sourceNavaid : navaidList ) {
			for( NavaidDTO targetNavaid : navaidList ) {
				
				boolean sameNavaid = sourceNavaid.getIdentifier()==targetNavaid.getIdentifier();
				
				if(sameNavaid==false) {
					
					// to find target detectable from sourceNavaid
					// the targetNavaid range must be considered!!! (not the sourceNavaid range)
					double kmRange_targetNavaid = MathTools.convertNMToKm(targetNavaid.getParamRange());
					
					Coordinate c1 = new Coordinate(sourceNavaid.getGeolocationLat(),
												   sourceNavaid.getGeolocationLon());
					Coordinate c2 = new Coordinate(targetNavaid.getGeolocationLat(),
												   targetNavaid.getGeolocationLon());
					
					double distance = HaversineDistance.calculateDistance(c1, c2);
					
					if(distance < kmRange_targetNavaid) {
						saveNavaidRange(sourceNavaid,targetNavaid);
					}
					
				}
				
			}
		}
		
	}
	
	private RangeDTO saveNavaidRange(NavaidDTO navaidSource, NavaidDTO navaidTarget) {
		
		RangeDTO rangeDTO = new RangeDTO(navaidSource,navaidTarget);
		return iRangeDAO.save(rangeDTO);
		
	}

	@Override
	public List<NavaidDTO> getAllNavaids() {

		return iNavaidDAO.findAll();
		
	}

	/**
	 * DATABASE TEST IMPLEMENTATION
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
