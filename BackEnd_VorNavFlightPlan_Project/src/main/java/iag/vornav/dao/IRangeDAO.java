/**
 * 
 */
package iag.vornav.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import iag.vornav.dto.RangeDTO;
import iag.vornav.dto.serializable.RangeId;

/**
 * @author Ivan Alonso
 *
 */
/**
 * 
 * JPA for managing database range operations: which navaids are detectable from every navaid, 
 * based on their range distance
 * 
 */
public interface IRangeDAO extends JpaRepository<RangeDTO, RangeId>{

}
