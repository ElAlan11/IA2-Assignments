package assignments.MLR;

public interface Solver {
	
	public double[] calculateCoefficients(double[][] dataset);
	public double predict(double[] coef, double[] input);
}
