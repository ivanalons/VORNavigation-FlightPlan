/**
 * 
 */
package iag.vornav.tools;

/**
 * @author Ivan Alonso
 *
 */
/**
 * 
 * This class defines a geolocation
 *
 */
public class Coordinate {

	private double latitude;
	private double longitude;
	
	public Coordinate(double latitude, double longitude) {
		this.latitude=latitude;
		this.longitude=longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String toString() {
		return "Coordinate [lat="+latitude+", lng="+longitude+"]";
	}
	
}
