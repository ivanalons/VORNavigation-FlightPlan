package iag.vornav.controller.xml.navaids;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement
public class OPENAIP {

    @JsonProperty("VERSION")
	private int VERSION;
	
    @JsonProperty("DATAFORMAT")
	private double DATAFORMAT;
	
    @JacksonXmlElementWrapper(localName="NAVAIDS")
	private List<NAVAID> navaidsList;
	
	public int getVERSION() {
		return VERSION;
	}
	
	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}
	
	public double getDATAFORMAT() {
		return DATAFORMAT;
	}
	
	public void setDATAFORMAT(double dATAFORMAT) {
		DATAFORMAT = dATAFORMAT;
	}

	public List<NAVAID> getNavaidsList() {
		return navaidsList;
	}

	public void setNavaidsList(List<NAVAID> navaidsList) {
		this.navaidsList = navaidsList;
	}
	
}
