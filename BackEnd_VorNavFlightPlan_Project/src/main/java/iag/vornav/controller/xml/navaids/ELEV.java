package iag.vornav.controller.xml.navaids;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
/**
 * 
 * @author Ivan Alonso
 *
 */
public class ELEV {

	@JsonProperty("UNIT")
	private String UNIT;
	
	@JacksonXmlText
	private int value;

	public String getUNIT() {
		return UNIT;
	}

	public void setUNIT(String uNIT) {
		UNIT = uNIT;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}
