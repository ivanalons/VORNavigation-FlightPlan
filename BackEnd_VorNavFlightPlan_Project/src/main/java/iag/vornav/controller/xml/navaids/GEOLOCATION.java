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

	public double getLAT() {
		return LAT;
	}

	public void setLAT(double lAT) {
		LAT = lAT;
	}

	public double getLON() {
		return LON;
	}

	public void setLON(double lON) {
		LON = lON;
	}

	public ELEV getElev() {
		return elev;
	}

	public void setElev(ELEV elev) {
		this.elev = elev;
	}
    
    
    
}
