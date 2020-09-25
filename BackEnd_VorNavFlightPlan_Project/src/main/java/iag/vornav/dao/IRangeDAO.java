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
public interface IRangeDAO extends JpaRepository<RangeDTO, RangeId>{

}
