/**
 * 
 */
package iag.vornav.dto.serializable;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author Ivan Alonso
 *
 */
@Embeddable
public class FlightPlanId implements Serializable{

	private Long flightId;
	
	private Integer stepId;

	public FlightPlanId() {
		
	}

	/**
	 * @param flightId
	 * @param stepId
	 */
	public FlightPlanId(Long flightId, Integer stepId) {
		this.flightId = flightId;
		this.stepId = stepId;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}
	
}
