/**
 * 
 */
package iag.vornav.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import iag.vornav.dto.FlightPlanDTO;
import iag.vornav.dto.serializable.FlightPlanId;

/**
 * @author Ivan Alonso
 *
 */
public interface IFlightPlanDAO extends JpaRepository<FlightPlanDTO, FlightPlanId> {

}
