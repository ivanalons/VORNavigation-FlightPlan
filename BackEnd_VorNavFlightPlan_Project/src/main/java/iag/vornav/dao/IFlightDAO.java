/**
 * 
 */
package iag.vornav.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import iag.vornav.dto.FlightDTO;

/**
 * @author Ivan Alonso
 *
 */
public interface IFlightDAO extends JpaRepository<FlightDTO, Long> {

}
