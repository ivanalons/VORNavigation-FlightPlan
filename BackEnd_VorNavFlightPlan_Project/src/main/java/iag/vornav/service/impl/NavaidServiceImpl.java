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
import iag.vornav.dao.INavaidDAO;
import iag.vornav.dto.NavaidDTO;
import iag.vornav.service.INavaidService;

/**
 * @author Ivan Alonso
 *
 */
@Service
public class NavaidServiceImpl implements INavaidService{

	@Autowired
	INavaidDAO iNavaidDAO;
	
	@Override
	public void saveImportNavaids(OPENAIP openAip) {
		
		List<NAVAID> xmlNavaidList = openAip.getNavaidsList();
		
		List<NavaidDTO> navaidDTOList = new ArrayList<>();
		
		for( NAVAID xmlNavaid : xmlNavaidList ) {
			
			NavaidDTO navaidDTO = new NavaidDTO();
			
			navaidDTO.setIdentifier((long)xmlNavaid.getIDENTIFIER());
			navaidDTO.setType(xmlNavaid.getTYPE());
			navaidDTO.setCountry(xmlNavaid.getCOUNTRY());
			navaidDTO.setName(xmlNavaid.getNAME());
			navaidDTO.setIdName(xmlNavaid.getID());
			
			GEOLOCATION xmlGeo = xmlNavaid.getGeoLocation();
			navaidDTO.setGeolocationLat(xmlGeo.getLAT());
			navaidDTO.setGeolocationLon(xmlGeo.getLON());
			
			ELEV xmlElev = xmlGeo.getElev();
			navaidDTO.setGeolocationElevUnit(xmlElev.getUNIT());
			navaidDTO.setGeolocationElev(xmlElev.getValue());
			
			RADIO xmlRadio = xmlNavaid.getRadio();
			navaidDTO.setRadioFrequency(xmlRadio.getFREQUENCY());
			navaidDTO.setRadioChannel(xmlRadio.getCHANNEL());
			
			PARAMS xmlParams = xmlNavaid.getParams();
			navaidDTO.setParamDeclination(xmlParams.getDECLINATION());
			
			boolean north = Boolean.getBoolean(xmlParams.getALIGNEDTOTRUENORTH());
			navaidDTO.setParamAlignedToTrueNorth(north);

			navaidDTOList.add(navaidDTO);
			
		}
		
		iNavaidDAO.saveAll(navaidDTOList);
		
	}

}
