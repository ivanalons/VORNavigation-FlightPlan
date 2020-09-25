/**
 * 
 */
package iag.vornav.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import iag.vornav.dto.NavaidDTO;

/**
 * @author Ivan Alonso
 *
 */
public interface INavaidDAO extends JpaRepository<NavaidDTO, Long> {

}
