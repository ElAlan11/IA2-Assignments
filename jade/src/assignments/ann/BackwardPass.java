package assignments.ann;

public class BackwardPass {
	
	
	public void updateWeights(int[] data, double[] hlOutput, double[] output, double[][][] weights, double[] errors) {
		
		for(int i=0; i<weights[1].length; i++) {
			double dn = logisticFuncDer(output[i]) * errors[i];
			
			for(int j=0; j<weights[1][i].length; j++) {
				weights[1][i][j] = weights[1][i][j] + DeepLearning.learningCoef * dn * hlOutput[j];
			}
			
			for(int j=0; j<weights[0].length; j++) {
				double dj = logisticFuncDer(hlOutput[j+1]) * weights[1][i][j+1] * dn;
				for(int k=0; k<weights[0][j].length; k++) {
					weights[0][j][k] = weights[0][j][k] + DeepLearning.learningCoef * dj * data[k];
				}
			}
		}
		
	}

	private double logisticFuncDer(double y) {
    	return y * (1-y);
	}	
}
        		
