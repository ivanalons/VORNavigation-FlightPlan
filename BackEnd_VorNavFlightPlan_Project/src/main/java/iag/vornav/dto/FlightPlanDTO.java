/**
 * 
 */
package iag.vornav.dto;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import iag.vornav.dto.serializable.FlightPlanId;

/**
 * @author Ivan Alonso
 *
 */
@Entity
@Table(name="flight_plans")
public class FlightPlanDTO {

	@EmbeddedId
	private FlightPlanId flightPlanId;
	
	@MapsId("flightId")
	@JoinColumn(name="flight_id")
	@ManyToOne
	private FlightDTO flight;
	
//	@MapsId("stepId") // it has no sense because "step" field is not a DTO
//	@ManyToOne        // it has no sense because "step" field is not a DTO
//	private Integer step; // value stored and accessible from flightPlanId.stepId
	
	@JsonIgnore
	@JoinColumn(name="navaid_id")
	@ManyToOne
	private NavaidDTO navaid;
	
	public FlightPlanDTO() {
		
	}

	/**
	 * @param flightPlanId
	 * @param flight
	 * @param step
	 * @param navaid
	 */
	public FlightPlanDTO(FlightDTO flight, Integer step, NavaidDTO navaid) {
		this.flightPlanId = new FlightPlanId();
		this.flightPlanId.setFlightId(flight.getId());
		this.flightPlanId.setStepId(step);
		this.flight = flight;
//		this.step = step;
		this.navaid = navaid;
	}

	public FlightPlanId getFlightPlanId() {
		return flightPlanId;
	}

	public void setFlightPlanId(FlightPlanId flightPlanId) {
		this.flightPlanId = flightPlanId;
	}

	public FlightDTO getFlight() {
		return flight;
	}

	public void setFlight(FlightDTO flight) {
		this.flight = flight;
	}

	public Integer getStep() { // be careful! step value is an embeddedId
		return flightPlanId.getStepId();
	}

	public void setStep(Integer step) { // be careful! step value is an embeddedId
		this.flightPlanId.setStepId(step);
	}

	public NavaidDTO getNavaid() {
		return navaid;
	}

	public void setNavaid(NavaidDTO navaid) {
		this.navaid = navaid;
	}
	
}
