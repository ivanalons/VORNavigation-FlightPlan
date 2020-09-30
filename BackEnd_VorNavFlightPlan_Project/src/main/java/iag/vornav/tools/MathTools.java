package iag.vornav.tools;

/**
 * @author Ivan Alonso
 *
 */
public class MathTools {

	private static final double NM_To_KM = 1.852;
	
	/**
	 * Convert a number from Nautic Miles units To Kilometers units
	 * 
	 * @param nmNumber : number in nautic miles
	 * @return : number in Kilometers
	 */
	public static double convertNMToKm(double nmNumber) {
		
		return nmNumber*NM_To_KM;
		
	}
	
}
