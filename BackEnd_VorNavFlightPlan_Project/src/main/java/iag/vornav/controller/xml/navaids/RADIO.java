package iag.vornav.controller.xml.navaids;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author Ivan Alonso
 *
 */
public class RADIO {

	@JsonProperty("FREQUENCY")
	private float FREQUENCY;
	
	@JsonProperty("CHANNEL")
	private String CHANNEL;

	public float getFREQUENCY() {
		return FREQUENCY;
	}

	public void setFREQUENCY(float fREQUENCY) {
		FREQUENCY = fREQUENCY;
	}

	public String getCHANNEL() {
		return CHANNEL;
	}

	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}
	
}
