package iag.vornav.tools;

/**
 * @author Ivan Alonso
 *
 */
public class MathTools {

	private static final double NM_To_KM = 1.852;
	
	public static double convertNMToKm(double nmNumber) {
		
		return nmNumber*NM_To_KM;
		
	}
	
}
