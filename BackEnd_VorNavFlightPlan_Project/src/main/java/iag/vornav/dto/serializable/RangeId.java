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
public class RangeId implements Serializable{

	private Long navaidSourceId;
	
	private Long navaidTargetId;

	public RangeId() {
	}

	/**
	 * @param navaidSource
	 * @param navaidTarget
	 */
	public RangeId(Long navaidSource, Long navaidTarget) {
		this.navaidSourceId = navaidSource;
		this.navaidTargetId = navaidTarget;
	}

	public Long getNavaidSource() {
		return navaidSourceId;
	}

	public void setNavaidSource(Long navaidSource) {
		this.navaidSourceId = navaidSource;
	}

	public Long getNavaidTarget() {
		return navaidTargetId;
	}

	public void setNavaidTarget(Long navaidTarget) {
		this.navaidTargetId = navaidTarget;
	}
	
	
	
}
