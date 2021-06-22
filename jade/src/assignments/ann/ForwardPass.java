package assignments.ann;

public class ForwardPass {
	
	public double[] calculateNNOutput(int[] input, double[][][] weights) {
		double[] hlOut = hiddenLayerOutput(input, weights[0]);
		return outputLayerOutput(hlOut, weights[1]);
	}
	
	public double[] outputLayerOutput(double[] input, double[][] weights){
		double[] output = new double[weights.length];
		
		for(int i=0; i<output.length; i++) {
			output[i] = calculateNeuronOutput(weights[i], input);
		}
		
		return output;
	}
	
	public double[] hiddenLayerOutput(int[] input, double[][] weights){
		double[] output = new double[weights.length+1];
		output[0] = -1;
		
		for(int i=1; i<output.length; i++) {
			output[i] = calculateNeuronOutput(weights[i-1], input);
//			output[i] = calculateWX(weights[i-1], input);
		}
		
		return output;
	}
	
	
	public double calculateNeuronOutput(double[] w, int[] x) {
		return logisticFunction(calculateWX(w,x));
	}
	
	public double calculateNeuronOutput(double[] w, double[] x) {
		return logisticFunction(calculateWX(w,x));
	}

	public double calculateWX(double[] w, int[] x){
		double v = 0.0;
		
		for(int i=0; i<w.length; i++)
			v += w[i]*x[i];
		
		return v;
	}
	
	public double calculateWX(double[] w, double[] x){
		double v = 0.0;
		
		for(int i=0; i<w.length; i++)
			v += w[i]*x[i];
		
		return v;
	}
	
	public double logisticFunction(double v) {
		return (double)1/(1+Math.exp(-v));
	}
	
	
}
