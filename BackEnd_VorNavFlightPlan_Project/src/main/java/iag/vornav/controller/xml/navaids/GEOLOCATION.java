package iag.vornav.controller.xml.navaids;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author Ivan Alonso
 *
 */
public class GEOLOCATION {

    @JsonProperty("LAT")
	private double LAT;
	
    @JsonProperty("LON")
	private double LON;
	
    @JsonProperty("ELEV")
	private ELEV elev;
    
    
}
