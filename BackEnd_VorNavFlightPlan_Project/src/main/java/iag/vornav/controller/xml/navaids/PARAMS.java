package iag.vornav.controller.xml.navaids;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author Ivan Alonso
 *
 */
public class PARAMS {

	@JsonProperty("DECLINATION")
	private double DECLINATION;
	
	@JsonProperty("ALIGNEDTOTRUENORTH")
	private String ALIGNEDTOTRUENORTH;

	public double getDECLINATION() {
		return DECLINATION;
	}

	public void setDECLINATION(double dECLINATION) {
		DECLINATION = dECLINATION;
	}

	public String getALIGNEDTOTRUENORTH() {
		return ALIGNEDTOTRUENORTH;
	}

	public void setALIGNEDTOTRUENORTH(String aLIGNEDTOTRUENORTH) {
		ALIGNEDTOTRUENORTH = aLIGNEDTOTRUENORTH;
	}
	
}
