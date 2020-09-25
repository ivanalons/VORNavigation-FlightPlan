/**
 * 
 */
package iag.vornav.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Ivan Alonso
 *
 */

@Entity
@Table(name="navaid")
public class NavaidDTO {

	@Id
//	@GeneratedValue( strategy = GenerationType.IDENTITY ) //not necessary identifier is provided
	private Long identifier;
	
	private String type;
	
	private String country;
	
	private String name;
	
	@Column(name="id_name")
	private String idName;
	
	@Column(name="geolocation_lat")
	private double geolocationLat;
	
	@Column(name="geolocation_lon")
	private double geolocationLon;
	
	@Column(name="geolocation_elev")
	private double geolocationElev;

	@Column(name="geolocation_elev_unit")
	private String geolocationElevUnit;
	
	@Column(name="radio_frequency")
	private float radioFrequency;
	
	@Column(name="radio_channel")
	private String radioChannel;
	
	@Column(name="param_range")
	private Integer paramRange;
	
	@Column(name="param_range_unit")
	private String paramRangeUnit;
	
	@Column(name="param_declination")
	private double paramDeclination;
	
	@Column(name="param_aligned_to_true_north")
	private boolean paramAlignedToTrueNorth;

	@OneToMany(mappedBy="navaidSource")
	private List<RangeDTO> rangeList;
	
	public NavaidDTO() {
		
	}
	
	/**
	 * @param identifier
	 * @param type
	 * @param country
	 * @param name
	 * @param idName
	 * @param geolocationLat
	 * @param geolocationLon
	 * @param geolocationElev
	 * @param geolocationElevUnit
	 * @param radioFrequency
	 * @param radioChannel
	 * @param paramDeclination
	 * @param paramAlignedToTrueNorth
	 */
	public NavaidDTO(Long identifier, String type, String country, String name, String idName, double geolocationLat,
			double geolocationLon, double geolocationElev, String geolocationElevUnit, float radioFrequency,
			String radioChannel, double paramDeclination, boolean paramAlignedToTrueNorth) {
		super();
		this.identifier = identifier;
		this.type = type;
		this.country = country;
		this.name = name;
		this.idName = idName;
		this.geolocationLat = geolocationLat;
		this.geolocationLon = geolocationLon;
		this.geolocationElev = geolocationElev;
		this.geolocationElevUnit = geolocationElevUnit;
		this.radioFrequency = radioFrequency;
		this.radioChannel = radioChannel;
		this.paramDeclination = paramDeclination;
		this.paramAlignedToTrueNorth = paramAlignedToTrueNorth;
	}

	public Long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public double getGeolocationLat() {
		return geolocationLat;
	}

	public void setGeolocationLat(double geolocationLat) {
		this.geolocationLat = geolocationLat;
	}

	public double getGeolocationLon() {
		return geolocationLon;
	}

	public void setGeolocationLon(double geolocationLon) {
		this.geolocationLon = geolocationLon;
	}

	public double getGeolocationElev() {
		return geolocationElev;
	}

	public void setGeolocationElev(double geolocationElev) {
		this.geolocationElev = geolocationElev;
	}

	public String getGeolocationElevUnit() {
		return geolocationElevUnit;
	}

	public void setGeolocationElevUnit(String geolocationElevUnit) {
		this.geolocationElevUnit = geolocationElevUnit;
	}

	public float getRadioFrequency() {
		return radioFrequency;
	}

	public void setRadioFrequency(float radioFrequency) {
		this.radioFrequency = radioFrequency;
	}

	public String getRadioChannel() {
		return radioChannel;
	}

	public void setRadioChannel(String radioChannel) {
		this.radioChannel = radioChannel;
	}

	public double getParamDeclination() {
		return paramDeclination;
	}

	public void setParamDeclination(double paramDeclination) {
		this.paramDeclination = paramDeclination;
	}

	public boolean isParamAlignedToTrueNorth() {
		return paramAlignedToTrueNorth;
	}

	public void setParamAlignedToTrueNorth(boolean paramAlignedToTrueNorth) {
		this.paramAlignedToTrueNorth = paramAlignedToTrueNorth;
	}

	public int getParamRange() {
		return paramRange;
	}

	public void setParamRange(int paramRange) {
		this.paramRange = paramRange;
	}

	public String getParamRangeUnit() {
		return paramRangeUnit;
	}

	public void setParamRangeUnit(String paramRangeUnit) {
		this.paramRangeUnit = paramRangeUnit;
	}
	
	
}
