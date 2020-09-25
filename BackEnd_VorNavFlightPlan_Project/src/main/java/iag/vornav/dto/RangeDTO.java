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

import iag.vornav.dto.serializable.RangeId;

/**
 * @author Ivan Alonso
 *
 */
@Entity
@Table(name="`range`") // range is a keyword from MySQL dialect ` symbols resolves conflict 
public class RangeDTO {

	@EmbeddedId
	private RangeId rangeId;
	
	@MapsId("navaidSourceId") //References Primary Key attribute @EmbeddedId rangeId.navaidSourceId
	@JoinColumn(name="navaid_source_id")
	@ManyToOne
	private NavaidDTO navaidSource;
	
	@MapsId("navaidTargetId") //References Primary Key attribute @EmbeddedId rangeId.navaidTargetId
	@JoinColumn(name="navaid_target_id")
	@ManyToOne
	private NavaidDTO navaidTarget;
	
	public RangeDTO() {
		
	}

	/**
	 * @param rangeId
	 * @param navaidSource
	 * @param navaidTarget
	 */
	public RangeDTO(NavaidDTO navaidSource, NavaidDTO navaidTarget) {
		this.rangeId = new RangeId();
		this.rangeId.setNavaidSource(navaidSource.getIdentifier());
		this.rangeId.setNavaidTarget(navaidTarget.getIdentifier());
		this.navaidSource = navaidSource;
		this.navaidTarget = navaidTarget;
	}

	public RangeId getRangeId() {
		return rangeId;
	}

	public void setRangeId(RangeId rangeId) {
		this.rangeId = rangeId;
	}

	public NavaidDTO getNavaidSource() {
		return navaidSource;
	}

	public void setNavaidSource(NavaidDTO navaidSource) {
		this.navaidSource = navaidSource;
	}

	public NavaidDTO getNavaidTarget() {
		return navaidTarget;
	}

	public void setNavaidTarget(NavaidDTO navaidTarget) {
		this.navaidTarget = navaidTarget;
	}
	
	public String toString() {
		return "Range [navaid_source_name="+this.getNavaidSource().getName()
				+", navaid_target_name="+this.getNavaidTarget().getName()+"]";

	}
	
}
