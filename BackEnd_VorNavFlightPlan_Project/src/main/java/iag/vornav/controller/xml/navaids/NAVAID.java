package iag.vornav.controller.xml.navaids;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author Ivan Alonso
 *
 */
public class NAVAID {

	@JsonProperty("TYPE")
	private String TYPE;
	
    @JsonProperty("IDENTIFIER")
	private int IDENTIFIER;
    
    @JsonProperty("COUNTRY")
	private String COUNTRY;
    
    @JsonProperty("NAME")
	private String NAME;
    
    @JsonProperty("ID")
	private String ID;
	
    @JsonProperty("GEOLOCATION")
    private GEOLOCATION geoLocation;
    
    @JsonProperty("RADIO")
    private RADIO radio;
    
    @JsonProperty("PARAMS")
    private PARAMS params;
    
	public GEOLOCATION getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(GEOLOCATION geoLocation) {
		this.geoLocation = geoLocation;
	}
	
	public int getIDENTIFIER() {
		return IDENTIFIER;
	}
	public void setIDENTIFIER(int iDENTIFIER) {
		IDENTIFIER = iDENTIFIER;
	}
	public String getCOUNTRY() {
		return COUNTRY;
	}
	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public RADIO getRadio() {
		return radio;
	}
	public void setRadio(RADIO radio) {
		this.radio = radio;
	}
	public PARAMS getParams() {
		return params;
	}
	public void setParams(PARAMS params) {
		this.params = params;
	}
	
	
	
	
}
