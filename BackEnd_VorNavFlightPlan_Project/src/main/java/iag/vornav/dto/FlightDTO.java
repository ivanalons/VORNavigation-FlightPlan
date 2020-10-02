/**
 * 
 */
package iag.vornav.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

/**
 * @author Ivan Alonso
 *
 */
@Entity
@Table(name="flights")
public class FlightDTO {
	
	@Id 
	@GeneratedValue( strategy = GenerationType.IDENTITY  )
	private Long id;
	
	private String name;
	
	@NotNull
	private double departure_lat;
	@NotNull
	private double departure_lng;
	@NotNull
	private double arrival_lat;
	@NotNull
	private double arrival_lng;
	
	@JsonIgnore
	@OneToMany(mappedBy="flight")
	private List<FlightPlanDTO> flightPlanList;

	public FlightDTO() {
		
	}

	/**
	 * @param id
	 * @param departure_lat
	 * @param departure_lng
	 * @param arrival_lat
	 * @param arrival_lng
	 */
	public FlightDTO(String name, double departure_lat, double departure_lng, double arrival_lat, double arrival_lng) {
		this.name = name;
		this.departure_lat = departure_lat;
		this.departure_lng = departure_lng;
		this.arrival_lat = arrival_lat;
		this.arrival_lng = arrival_lng;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getDeparture_lat() {
		return departure_lat;
	}

	public void setDeparture_lat(double departure_lat) {
		this.departure_lat = departure_lat;
	}

	public double getDeparture_lng() {
		return departure_lng;
	}

	public void setDeparture_lng(double departure_lng) {
		this.departure_lng = departure_lng;
	}

	public double getArrival_lat() {
		return arrival_lat;
	}

	public void setArrival_lat(double arrival_lat) {
		this.arrival_lat = arrival_lat;
	}

	public double getArrival_lng() {
		return arrival_lng;
	}

	public void setArrival_lng(double arrival_lng) {
		this.arrival_lng = arrival_lng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FlightPlanDTO> getFlightPlanList() {
		return flightPlanList;
	}

	public void setFlightPlanList(List<FlightPlanDTO> flightPlanList) {
		this.flightPlanList = flightPlanList;
	}
	
}
