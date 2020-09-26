/**
 * 
 */
package iag.vornav.tools;

/**
 * @author Ivan Alonso
 *
 */
public class HaversineDistance {

	public static double calculateDistance(Coordinate c1, Coordinate c2) {

		final double R = 6371.0088; // Radious of the earth

		Double lat1 = c1.getLatitude();
		Double lon1 = c1.getLongitude();
		Double lat2 = c2.getLatitude();
		Double lon2 = c2.getLongitude();
		Double latDistance = toRad(lat2 - lat1);
		Double lonDistance = toRad(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double distance = R * c;

		return distance;
	}

	private static Double toRad(Double value) {
		return value * Math.PI / 180;
	}

}