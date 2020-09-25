package iag.vornav.controller.xml.navaids;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author Ivan Alonso
 *
 */
public class RADIO {

	@JsonProperty("FREQUENCY")
	private double FREQUENCY;
	
	@JsonProperty("CHANNEL")
	private String CHANNEL;

	public double getFREQUENCY() {
		return FREQUENCY;
	}

	public void setFREQUENCY(double fREQUENCY) {
		FREQUENCY = fREQUENCY;
	}

	public String getCHANNEL() {
		return CHANNEL;
	}

	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}
	
}
