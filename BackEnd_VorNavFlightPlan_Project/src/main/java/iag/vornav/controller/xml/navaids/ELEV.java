package iag.vornav.controller.xml.navaids;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class ELEV {

	@JsonProperty("UNIT")
	private String UNIT;
	
	@JacksonXmlText
	private int value;
}
